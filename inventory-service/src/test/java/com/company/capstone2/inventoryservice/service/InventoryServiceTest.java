package com.company.capstone2.inventoryservice.service;

import com.company.capstone2.inventoryservice.dao.InventoryDao;
import com.company.capstone2.inventoryservice.model.Inventory;
import com.company.capstone2.inventoryservice.util.feign.InvoiceServiceClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InventoryServiceTest {

    @Mock
    InventoryDao dao;
    @Mock
    InvoiceServiceClient client;

    @InjectMocks
    InventoryService service;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addInventory() {
        Inventory inventory = new Inventory();
        when(dao.addInventory(inventory)).thenReturn(inventory);
        inventory = service.addInventory(inventory);
        verify(dao, times(1)).addInventory(inventory);
    }

    @Test
    public void getAllInventory() {
        List<Inventory> inventories = new ArrayList<>();
        when(dao.getAllInventory()).thenReturn(inventories);
        List<Inventory> inventoryList = service.getAllInventory();
        assertEquals(inventoryList.size(),0);
    }

    @Test
    public void getInventoryById() {
        Inventory inventory = new Inventory();
        when(dao.getInventoryById(1)).thenReturn(inventory);
        inventory = service.getInventoryById(1);
        verify(dao, times(1)).getInventoryById(1);
    }

    @Test
    public void updateInventory() {
        Inventory inventory = new Inventory();
        service.updateInventory(inventory);
        verify(dao,times(1)).updateInventory(inventory);
    }

    @Test
    public void deleteInventory() {
        service.deleteInventory(1);
        verify(dao,times(1)).deleteInventory(1);
    }}