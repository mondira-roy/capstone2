package com.company.capstone2.inventoryservice.dao;

import com.company.capstone2.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InventoryDaoJdbcTemplateImplTest {
    @Autowired
    InventoryDao dao;

    @Before
    public void setUp() throws Exception {
        // clear the data base
        List<Inventory> inventory = dao.getAllInventory();
        inventory.stream().forEach(inventory1 -> dao.deleteInventory(inventory1.getInventoryId()));
    }

    @Test
    public void testAddInventoryGetAllInventoryGetInventoryById() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(5);

        // test add
        inventory = dao.addInventory(inventory);
        // test getAll
        List<Inventory> Inventories = dao.getAllInventory();
        assertEquals(Inventories.size(),1);
        // test get by Id
        Inventory inventory1 = dao.getInventoryById(inventory.getInventoryId());
        assertEquals(inventory,inventory1);
    }

    @Test
    public void testUpdateInventoryDeleteInventory() {
        Inventory inventory = new Inventory();
        //inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(5);

        // test update
        inventory = dao.addInventory(inventory);
        inventory.setQuantity(10);
        dao.updateInventory(inventory);
        assertEquals(dao.getInventoryById(inventory.getInventoryId()).getQuantity(),10);
    }

}