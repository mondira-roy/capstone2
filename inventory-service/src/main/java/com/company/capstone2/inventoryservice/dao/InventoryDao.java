package com.company.capstone2.inventoryservice.dao;

import com.company.capstone2.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryDao {
    Inventory addInventory(Inventory inventory);

    List<Inventory> getAllInventory();

    Inventory getInventoryById(int id);

    void updateInventory(Inventory inventory);

    void deleteInventory(int id);

    void deleteInventoryByProductId(int in);
}
