package com.company.capstone2.inventoryservice.service;

import com.company.capstone2.inventoryservice.dao.InventoryDao;
import com.company.capstone2.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryService {
    @Autowired
    InventoryDao dao;

    public InventoryService(InventoryDao dao) {
        this.dao = dao;
    }

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

    public void updateInventory(Inventory inventory) {

        dao.updateInventory(inventory);
    }

    public void deleteInventory(int id) {
        dao.deleteInventory(id);
    }

}