package com.company.capstone2.inventoryservice.service;

import com.company.capstone2.inventoryservice.dao.InventoryDao;
import com.company.capstone2.inventoryservice.model.Inventory;
import com.company.capstone2.inventoryservice.util.feign.InvoiceServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class InventoryService {
    @Autowired
    InventoryDao dao;
    @Autowired
    InvoiceServiceClient invoiceClient;

    public InventoryService(InventoryDao dao,InvoiceServiceClient invoiceClient) {
        this.dao = dao;
        this.invoiceClient = invoiceClient;
    }

    @Transactional
    public Inventory addInventory(Inventory inventory) {
        inventory = dao.addInventory(inventory);
        return inventory;
    }

    public List<Inventory> getAllInventory() {
        return dao.getAllInventory();
    }

    public Inventory getInventoryById(int id) {
        return dao.getInventoryById(id);
    }

    @Transactional
    public void updateInventory(Inventory inventory) {
        dao.updateInventory(inventory);
    }

    @Transactional
    public void deleteInventory(int id) {
        invoiceClient.deleteInvoiceItemByInventoryId(id);
        dao.deleteInventory(id);
    }

    public void deleteInventoryByProductId(int id) {
        dao.deleteInventoryByProductId(id);
    }

}