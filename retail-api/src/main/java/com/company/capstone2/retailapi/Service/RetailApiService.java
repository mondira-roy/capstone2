package com.company.capstone2.retailapi.Service;

import com.company.capstone2.retailapi.exception.NotFoundException;
import com.company.capstone2.retailapi.model.*;
import com.company.capstone2.retailapi.util.feign.*;
import com.company.capstone2.retailapi.viewModel.RetailInvoiceViewModel;
import feign.FeignException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class RetailApiService {

    public static final String EXCHANGE = "level-up-exchange";
    public static final String ROUTING_KEY = "level-up.#";

    @Autowired
    InvoiceClient invoiceClient;
    @Autowired
    LevelupClient levelupClient;
    @Autowired
    ProductClient productClient;
    @Autowired
    CustomerClient customerClient;
    @Autowired
    InventoryClient inventoryClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public RetailApiService(
            InvoiceClient invoiceClient,
            LevelupClient levelupClient,
            ProductClient productClient,
            CustomerClient customerClient,
            InventoryClient inventoryClient,
            RabbitTemplate rabbitTemplate) {
        this.invoiceClient = invoiceClient;
        this.levelupClient = levelupClient;
        this.productClient = productClient;
        this.customerClient = customerClient;
        this.inventoryClient = inventoryClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public RetailInvoiceViewModel submitInvoice(RetailInvoiceViewModel ivm) {
//        retailInvoiceViewModel (order View model)        # ${ } denote provided input
//        private int invoiceId;                        # generated when saved
//       ${private int customerId;}                       # provided but checked first
//        private LocalDate purchaseDate;               # LocalDate.now()
//        private List<InvoiceItem> invoiceItems;
        //   invoiceItem model
        //   private int invoiceItemId;                    # generated when saved
        //   private int invoiceId;                        # returned and set from above
        //   ${private int inventoryId;}                      # provided but check first (if exist in inventory DB),
        //                                                   feign with productClient for findProductById(from
        //                                                   productId in inventory),
        //                                                   save tempUnitPrice from the Product,set it to unitPrice
        //   ${private int quantity;}                         # provided input
        //   private BigDecimal unitPrice;                 # see inventoryId
//        private int point;                            # calculated

        // check if customer
        Customer customer;
        try {
            customer = customerClient.getCustomerById(ivm.getCustomerId());
        } catch (FeignException e) {
            throw new NotFoundException("invalid customer id: " + ivm.getCustomerId());
        }
        // from invoiceItems of view model, get inventory from inventory id and then get product thu inventory,
        // take product price and set to the invoice item.
        List<InvoiceItem> invoiceItems = ivm.getInvoiceItems();
        List<Inventory> inventories = new ArrayList<>();
        invoiceItems.stream().forEach(invoiceItem -> {
            Inventory tempInventory;
            try {
                tempInventory = inventoryClient.getInventoryById(invoiceItem.getInventoryId());
            } catch (FeignException e) {
                throw new NotFoundException("invalid invoiceItem id: " + invoiceItem.getInventoryId());
            }
            if (tempInventory.getQuantity() < invoiceItem.getQuantity()) {
                throw new NotFoundException("Not enough inventory");
            }
            inventories.add(tempInventory);

            int productId = tempInventory.getProductId();
            Product tempProduct;
            try {
                tempProduct = productClient.getProductById(productId);
            } catch (FeignException e) {
                throw new NotFoundException("invalid product id: " + productId);
            }
            invoiceItem.setUnitPrice(tempProduct.getListPrice());

        });
        // add invoice
        LocalDate localDate = LocalDate.now();
        ivm.setPurchaseDate(localDate);
        ivm = invoiceClient.submitInvoice(ivm);

        // for every 50 dollars, add 10 points
        AtomicInteger tempPoints = new AtomicInteger(ivm.getPoint());
        invoiceItems.stream().forEach(invoiceItem -> {
            BigDecimal subtotal =
                    invoiceItem.getUnitPrice().multiply(BigDecimal.valueOf(invoiceItem.getQuantity()));
            tempPoints.addAndGet(subtotal.intValue() / 5);
        });
        ivm.setPoint(tempPoints.intValue());
        // construct msg
        Levelup msg = new Levelup();
        // if customer's point == 0, set member date = now;
        int awardPoint = getLevelUpPointsByCustomerId(ivm.getCustomerId());
        if (awardPoint == 0) {
            msg.setMemberDate(LocalDate.now());
        } else { // else pick the first date
            List<Levelup> tempLevelups = levelupClient.getLevelUpByCustomerId(ivm.getCustomerId());
            msg.setMemberDate(tempLevelups.get(0).getMemberDate());
        }
        msg.setPoint(ivm.getPoint());
        msg.setCustomerId(customer.getCustomerId());
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);

        return ivm;
    }


    public RetailInvoiceViewModel getInvoiceById(int id) {
        RetailInvoiceViewModel rivm = invoiceClient.getInvoiceById(id);
        int point = calculatePoint(rivm);
        rivm.setPoint(point);
        return rivm;
    }

    // helper method
    public int calculatePoint(RetailInvoiceViewModel rivm) {
        int point = rivm.getInvoiceItems().stream()
                .map(invoiceItem -> invoiceItem.getUnitPrice().multiply(BigDecimal.valueOf(invoiceItem.getQuantity())))
                .mapToInt(total -> total.intValue() / 5).sum();

        return point;
    }


    public List<RetailInvoiceViewModel> getAllInvoices() {
        List<RetailInvoiceViewModel> rivms = invoiceClient.getAllInvoices();
        rivms.stream().forEach(rivm -> {
            int point = calculatePoint(rivm);
            rivm.setPoint(point);
        });
        return rivms;
    }


    public List<RetailInvoiceViewModel> getInvoicesByCustomerId(int id) {
        List<RetailInvoiceViewModel> rivms = invoiceClient.getInvoicesByCustomerId(id);
        rivms.stream().forEach(rivm -> {
            int point = calculatePoint(rivm);
            rivm.setPoint(point);
        });
        return rivms;
    }

    public Product getProductById(int id) {
        return productClient.getProductById(id);
    }

    public List<Product> getProductsInInventory() {
//        return productClient.getProductsInInventory();
        List<Inventory> inventories= inventoryClient.getAllInventory();
        List<Product> products = new ArrayList<>();
        inventories.stream().forEach(inventory -> {
            int productId = inventory.getProductId();
            Product tempProduct = new Product();
            try{
                tempProduct = productClient.getProductById(productId);
            } catch (FeignException e ){

            } finally{
                if (tempProduct.getProductId()!=0){
                    products.add(tempProduct);
                }
            }
        });
        return products;
    }

    public List<Product> getProductByInvoiceId(int id) {
//        return productClient.getProductByInvoiceId(id);
                RetailInvoiceViewModel invoice = invoiceClient.getInvoiceById(id);
        int invoiceId = invoice.getInvoiceId();
        List<InvoiceItem> items = invoiceClient.getInvoiceItemByInvoiceId(invoiceId);
        List<Product> products = new ArrayList<>();
        items.stream().forEach(item -> {
            int tempInventoryId = item.getInventoryId();
            int tempProductId = inventoryClient.getAllInventory().stream()
                    .filter(inventory -> inventory.getInventoryId()==tempInventoryId).collect(Collectors.toList())
                    .get(0).getProductId();
            Product tempProduct = getProductById(tempProductId);
            products.add(tempProduct);
        });
        return products;
    }


    public int getLevelUpPointsByCustomerId(int id) {
        List<Levelup> levelups = levelupClient.getLevelUpByCustomerId(id);
        int awardPoints = levelups.stream().mapToInt(Levelup::getPoint).sum();
        return awardPoints;
    }

}
