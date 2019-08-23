package com.company.capstone2.retailapi.Service;

import com.company.capstone2.retailapi.exception.NotFoundException;
import com.company.capstone2.retailapi.model.*;
import com.company.capstone2.retailapi.util.feign.*;
import com.company.capstone2.retailapi.viewModel.RetailInvoiceViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
//        retailInvoiceViewModel (order View model)
//        private int invoiceId;                        # generated when saved
//        private int customerId;                       # provided but checked first
//        private LocalDate purchaseDate;               # LocalDate.now()
//        private List<InvoiceItem> invoiceItems;
            //   invoiceItem model
            //   private int invoiceItemId;                    # generated when saved
            //   private int invoiceId;                        # returned and set from above
            //   private int inventoryId;                      # provided but check first (if exist in inventory DB),
            //                                                   feign with productClient for findProductById(from productId in inventory),
            //                                                   save tempUnitPrice from the Product,set it to unitPrice
            //   private int quantity;                         # provided input
            //   private BigDecimal unitPrice;                 # see inventoryId
//        private int point;                            # calculated

        // check if customer
        Customer customer = customerClient.getCustomerById(ivm.getCustomerId());
        if (customer == null) {
            throw new NotFoundException("customer not found: id "+ivm.getCustomerId());
        }
        // from invoiceItems of view model, get inventory from inventory id and then get product thu inventory,
        // take product price and set to the invoice item.
        List<InvoiceItem> invoiceItems = ivm.getInvoiceItems();
        List<Inventory> inventories = new ArrayList<>();
        invoiceItems.stream().forEach(invoiceItem -> {
            Inventory tempInventory = inventoryClient.getInventoryById(invoiceItem.getInventoryId());
            inventories.add(tempInventory);
            if (tempInventory!=null){
//                int productId = inventoryClient.getInventoryById(invoiceItem.getInventoryId()).getProductId();
                int productId = tempInventory.getProductId();
                Product tempProduct = productClient.getProductById(productId);
                invoiceItem.setUnitPrice(tempProduct.getListPrice());
            }
        });
        if (inventories.contains(null) ) {
            throw new NotFoundException("inventory not found");
        }
        // add invoice
        LocalDate localDate = LocalDate.now();
        ivm.setPurchaseDate(localDate);
        ivm = invoiceClient.submitInvoice(ivm);

        // for every 50 dollars, add 10 points
        AtomicInteger tempPoints = new AtomicInteger(ivm.getPoint());
        invoiceItems.stream().forEach(invoiceItem -> {
            BigDecimal subtotal = invoiceItem.getUnitPrice().multiply(BigDecimal.valueOf(invoiceItem.getQuantity()));
            tempPoints.addAndGet(subtotal.intValue() / 5);
        });
        ivm.setPoint(tempPoints.intValue());
        // construct msg
        Levelup msg = new Levelup();
        // if customer's point == 0, set member date = now;
        int awardPoint = getLevelUpPointsByCustomerId(ivm.getCustomerId());
        if (awardPoint==0){
            msg.setMemberdate(LocalDate.now());
        } else { // else pick the first date
            List<Levelup> tempLevelups = levelupClient.getLevelUpPointsByCustomerId(ivm.getCustomerId());
            msg.setMemberdate(tempLevelups.get(0).getMemberdate());
        }
        msg.setPoint(ivm.getPoint());
        msg.setCustomerId(customer.getCustomerId());
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);
        return ivm;
    }


    public RetailInvoiceViewModel getInvoiceById(int id) {
        return invoiceClient.getInvoiceById(id);
    }


    public List<RetailInvoiceViewModel> getAllInvoices() {
        return invoiceClient.getAllInvoices();
    }


    public List<RetailInvoiceViewModel> getInvoicesByCustomerId(int id) {
        return invoiceClient.getInvoicesByCustomerId(id);
    }


    public List<Product> getProductsInInventory() {
        return productClient.getProductsInInventory();
    }


    public Product getProductById(int id) {
        return productClient.getProductById(id);
    }


    public List<Product> getProductByInvoiceId(int id) {
        return productClient.getProductByInvoiceId(id);
    }


    public int getLevelUpPointsByCustomerId(int id) {
        List<Levelup> levelups = levelupClient.getLevelUpPointsByCustomerId(id);
        int awardPoints = levelups.stream().mapToInt(Levelup::getPoint).sum();
        return awardPoints;
    }

}
