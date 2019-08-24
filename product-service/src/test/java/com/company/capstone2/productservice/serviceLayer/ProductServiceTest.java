package com.company.capstone2.productservice.serviceLayer;

import com.company.capstone2.productservice.dao.ProductDao;
import com.company.capstone2.productservice.model.Inventory;
import com.company.capstone2.productservice.model.Invoice;
import com.company.capstone2.productservice.model.InvoiceItem;
import com.company.capstone2.productservice.model.Product;
import com.company.capstone2.productservice.util.feign.InventoryServiceClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {
    @InjectMocks
    ProductService service;
    @Mock
    ProductDao dao;

    @Mock
    InventoryServiceClient inventoryClient;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddProduct() {
        Product product1 = new Product();
        product1.setProductName("Books");
        product1.setProductDescription("Hard Cover");
        product1.setListPrice(BigDecimal.valueOf(120.50));
        product1.setUnitCost(BigDecimal.valueOf(20.50));
        when(dao.addProduct(product1)).thenReturn(product1);
        product1 = service.addProduct(product1);
        verify(dao, times(1)).addProduct(product1);

    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        when(dao.getAllProducts()).thenReturn(products);
        List<Product> products1 = service.getAllProducts();
        verify(dao, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductById() {
        Product product1 = new Product();
        when(dao.getProductById(1)).thenReturn(product1);
        product1 = service.getProductById(1);
        verify(dao, times(1)).getProductById(1);
    }

    @Test
    public void updateProduct() {
        Product product1 = new Product();
        service.updateProduct(product1);
        verify(dao, times(1)).updateProduct(product1);
    }

    @Test
    public void deleteProduct() {
        service.deleteProduct(1);
        verify(dao, times(1)).deleteProduct(1);
    }

}