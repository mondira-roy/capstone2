package com.company.capstone2.retailapi.Service;

import com.company.capstone2.retailapi.exception.NotFoundException;
import com.company.capstone2.retailapi.model.*;
import com.company.capstone2.retailapi.util.feign.*;
import com.company.capstone2.retailapi.viewModel.Order;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.FeignException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.net.SocketTimeoutException;
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

  // @HystrixCommand(fallbackMethod = "reliable3")
   @Transactional
    public Order submitInvoice(Order ivm) {
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
                throw new NotFoundException("invalid inventory id: " + invoiceItem.getInventoryId());
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
            List<Levelup> tempLevelups = getLevelUpByCustomerId(ivm.getCustomerId());
            msg.setMemberDate(tempLevelups.get(0).getMemberDate());
        }
        msg.setPoint(ivm.getPoint());
        msg.setCustomerId(customer.getCustomerId());
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);

        return ivm;
    }

    @Transactional
    public Order reliable3(Order ivm) {
        Customer customer = customerClient.getCustomerById(ivm.getCustomerId());
        List<InvoiceItem> invoiceItems = ivm.getInvoiceItems();
        List<Inventory> inventories = new ArrayList<>();
        invoiceItems.stream().forEach(invoiceItem -> {
            Inventory tempInventory = inventoryClient.getInventoryById(invoiceItem.getInventoryId());
            inventories.add(tempInventory);
            int productId = tempInventory.getProductId();
            Product tempProduct = productClient.getProductById(productId);
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
        msg.setMemberDate(LocalDate.now());
        msg.setPoint(ivm.getPoint());
        msg.setCustomerId(customer.getCustomerId());
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);
        return ivm;
    }

    @Transactional
    @HystrixCommand(fallbackMethod = "reliable2")
    public int getLevelUpPointsByCustomerId(int id) {
        List<Levelup> levelups = getLevelUpByCustomerId(id);
        int awardPoints = levelups.stream().mapToInt(Levelup::getPoint).sum();
        return awardPoints;
    }

    @Transactional
    @HystrixCommand(fallbackMethod = "reliable")
    public List<Levelup> getLevelUpByCustomerId(int id) {
        List<Levelup> levelups= new ArrayList<>();;
        try {
           levelups= levelupClient.getLevelUpByCustomerId(id);
        } catch (Exception e){
            if (e.getCause().getClass().equals(SocketTimeoutException.class)) {
                throw new NotFoundException("Server connection timeout!");
            }
        } finally {
            return levelups;
        }
    }

    public int reliable2(int id) {
        return -1;
    }

    @Transactional
    public List<Levelup> reliable(int id) {
        Levelup levelup = new Levelup();
        levelup.setCustomerId(id);
        levelup.setPoint(0);
        List<Levelup> levelups = new ArrayList<>();
        levelups.add(levelup);
        return levelups;
    }


    public Order getInvoiceById(int id) {
        Order rivm = invoiceClient.getInvoiceById(id);
        int point = calculatePoint(rivm);
        rivm.setPoint(point);
        return rivm;
    }

    // helper method
    public int calculatePoint(Order rivm) {
        int point = rivm.getInvoiceItems().stream()
                .map(invoiceItem -> invoiceItem.getUnitPrice().multiply(BigDecimal.valueOf(invoiceItem.getQuantity())))
                .mapToInt(total -> total.intValue() / 5).sum();

        return point;
    }


    public List<Order> getAllInvoices() {
        List<Order> rivms = invoiceClient.getAllInvoices();
        rivms.stream().forEach(rivm -> {
            int point = calculatePoint(rivm);
            rivm.setPoint(point);
        });
        return rivms;
    }


    public List<Order> getInvoicesByCustomerId(int id) {
        List<Order> rivms = invoiceClient.getInvoicesByCustomerId(id);
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
        List<Inventory> inventories = inventoryClient.getAllInventory();
        List<Product> products = new ArrayList<>();
        inventories.stream().forEach(inventory -> {
            int productId = inventory.getProductId();
            Product tempProduct = new Product();
            try {
                tempProduct = productClient.getProductById(productId);
            } catch (FeignException e) {
                throw new NotFoundException("invalid product id: " + productId);
            } finally {
                if (tempProduct.getProductId() != 0) {
                    products.add(tempProduct);
                }
            }
        });
        return products;
    }


    public List<Product> getProductByInvoiceId(int id) {
//        return productClient.getProductByInvoiceId(id);
        Order invoice = new Order();
        try {
            invoice = invoiceClient.getInvoiceById(id);
        } catch (FeignException e) {
            throw new NotFoundException("invalid invoice id: " + id);
        }
        int invoiceId = invoice.getInvoiceId();
        List<InvoiceItem> items = invoiceClient.getInvoiceItemByInvoiceId(invoiceId);
        List<Product> products = new ArrayList<>();
        items.stream().forEach(item -> {
            int tempInventoryId = item.getInventoryId();
            int tempProductId = inventoryClient.getAllInventory().stream()
                    .filter(inventory -> inventory.getInventoryId() == tempInventoryId).collect(Collectors.toList())
                    .get(0).getProductId();
            Product tempProduct = getProductById(tempProductId);
            products.add(tempProduct);
        });
        return products;
    }


}
