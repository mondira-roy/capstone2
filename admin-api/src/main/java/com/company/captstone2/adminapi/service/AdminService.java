package com.company.captstone2.adminapi.service;


import com.company.captstone2.adminapi.exception.NotFoundException;
import com.company.captstone2.adminapi.model.*;
import com.company.captstone2.adminapi.util.feign.*;
import com.company.captstone2.adminapi.viewModel.InvoiceViewModel;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminService {

    @Autowired
    CustomerClient customerClient;
    @Autowired
    LevelupClient levelupClient;
    @Autowired
    InvoiceClient invoiceClient;
    @Autowired
    InventoryClient inventoryClient;
    @Autowired
    ProductClient productClient;

    public AdminService(CustomerClient customerClient, LevelupClient levelupClient, InvoiceClient invoiceClient,
                        InventoryClient inventoryClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.levelupClient = levelupClient;
        this.invoiceClient = invoiceClient;
        this.inventoryClient = inventoryClient;
        this.productClient = productClient;
    }

    //Customer CRUD
    @Transactional
    public Customer addCustomer(Customer customer) {
        customer = customerClient.addCustomer(customer);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return customerClient.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        Customer customer;
        try {
            customer = customerClient.getCustomerById(id);
        } catch (FeignException e) {
            throw new NotFoundException("invalid customer id: " + id);
        }
        return customer;
    }

    @Transactional
    public void updateCustomer(Customer customer, int id) {
        customerClient.updateCustomer(customer, id);
    }

    public void deleteCustomer(int id) {
        customerClient.deleteCustomer(id);
    }

    //Levelup CRUD

    @Transactional
    public Levelup addLevelup(Levelup levelup) {
        levelup = levelupClient.addLevelup(levelup);
        return levelup;
    }

    public List<Levelup> getAllLevelups() {
        return levelupClient.getAllLevelups();
    }

    public Levelup getLevelupById(int id) {
        Levelup levelup;
        try {
           levelup = levelupClient.getLevelupById(id);
        } catch (FeignException e) {
            throw new NotFoundException("invalid levelup id: " + id);
        }
        return levelup;
    }

    @Transactional
    public void updateLevelup(Levelup levelup, int id) {
        levelupClient.updateLevelup(levelup, id);
    }

    public void deleteLevelup(int id) {
        levelupClient.deleteLevelup(id);
    }

    //Invoice CRUD
    @Transactional
    public InvoiceViewModel addInvoice(InvoiceViewModel ivm) {
        return invoiceClient.addInvoice(ivm);
    }

    public List<InvoiceViewModel> getAllInvoices() {
        return invoiceClient.getAllInvoices();
    }

    public InvoiceViewModel getInvoiceById(int id) {
        InvoiceViewModel ivm;
        try {
           ivm = invoiceClient.getInvoiceById(id);
        } catch (FeignException e) {
            throw new NotFoundException("invalid invoice id: " + id);
        }
        return ivm;
    }
    @Transactional
    public void updateInvoice(InvoiceViewModel ivm, int id) {
        invoiceClient.updateInvoice(ivm, id);
    }

    public void deleteInvoice(int id) {
        invoiceClient.deleteInvoice(id);
    }

    //Inventory CRUD
    @Transactional
    public Inventory addInventory( Inventory inventory){
        return inventoryClient.addInventory(inventory);
    };

    public List<Inventory> getAllInventory(){
        return inventoryClient.getAllInventory();
    };

    public Inventory getInventoryById(int id){
        Inventory inventory;
        try {
            inventory = inventoryClient.getInventoryById(id);
        } catch (FeignException e) {
            throw new NotFoundException("invalid inventory id: " + id);
        }
        return inventory;
    };
    @Transactional
    public void updateInventory( Inventory inventory,  int id){
        inventoryClient.updateInventory(inventory,id);
    };

    public void deleteInventory( int id){
        inventoryClient.deleteInventory(id);
    };

    //Product CRUD
    @Transactional
    public Product addProduct( Product product){
        return productClient.addProduct(product);
    };

    public List<Product> getAllProducts(){
        return productClient.getAllProducts();
    };

    public Product getProductById(int id){
        Product product;
        try {
            product = productClient.getProductById(id);
        } catch (FeignException e) {
            throw new NotFoundException("invalid product id: " + id);
        }
        return product;
    };
    @Transactional
    public void updateProduct(Product product, int id){
        productClient.updateProduct(product,id);
    };

    public void deleteProduct(int id){
        productClient.deleteProduct(id);
    };


}
