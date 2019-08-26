package com.company.capstone2.retailapi.Service;

import com.company.capstone2.retailapi.model.*;
import com.company.capstone2.retailapi.util.feign.*;
import com.company.capstone2.retailapi.viewModel.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RetailApiServiceTest {

    @Mock
    InvoiceClient invoiceClient;
    @Mock
    LevelupClient levelupClient;
    @Mock
    ProductClient productClient;
    @Mock
    CustomerClient customerClient;
    @Mock
    InventoryClient inventoryClient;
    @Mock
    RabbitTemplate rabbit;
    @InjectMocks
    RetailApiService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    public static final String EXCHANGE = "level-up-exchange";
    public static final String ROUTING_KEY = "level-up.#";

    //    public InvoiceViewModel submitInvoice(InvoiceViewModel ivm)
    @Test
    public void testSubmitInvoice() {
        // arrange input
        // retailInvoiceViewModel{
        // customerId:10,
        // invoiceItem:[{inventoryId:1000, quantity:1 },{inventoryId:1001, quantity:10}],
        // awardPoints:40
        // }

        Order rivm = new Order();
        rivm.setCustomerId(1);
        rivm.setPurchaseDate(LocalDate.now());
        InvoiceItem invoiceItem1a = new InvoiceItem();
        invoiceItem1a.setInventoryId(1000);
        invoiceItem1a.setQuantity(1);
        invoiceItem1a.setUnitPrice(new BigDecimal(100.00).setScale(2));
        InvoiceItem invoiceItem1b = new InvoiceItem();
        invoiceItem1b.setInventoryId(1001);
        invoiceItem1b.setQuantity(10);
        invoiceItem1b.setUnitPrice(new BigDecimal(10.00).setScale(2));
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItem1a);
        invoiceItems.add(invoiceItem1b);
        rivm.setInvoiceItems(invoiceItems);

        Order rivm2 = new Order();
        rivm2.setInvoiceId(1);
        rivm2.setCustomerId(1);
        rivm2.setPurchaseDate(LocalDate.now());
        InvoiceItem invoiceItem2a = new InvoiceItem();
        invoiceItem2a.setInvoiceItemId(1);
        invoiceItem2a.setInventoryId(1000);
        invoiceItem2a.setQuantity(1);
        invoiceItem2a.setUnitPrice(new BigDecimal(100.00).setScale(2));
        InvoiceItem invoiceItem2b = new InvoiceItem();
        invoiceItem2a.setInvoiceItemId(2);
        invoiceItem2b.setInventoryId(1001);
        invoiceItem2b.setQuantity(10);
        invoiceItem2b.setUnitPrice(new BigDecimal(10.00).setScale(2));
        List<InvoiceItem> invoiceItems2 = new ArrayList<>();
        invoiceItems2.add(invoiceItem2a);
        invoiceItems2.add(invoiceItem2b);
        rivm2.setInvoiceItems(invoiceItems2);

        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        when(customerClient.getCustomerById(1)).thenReturn(customer1);
        Inventory inventory1a = new Inventory();
        inventory1a.setProductId(50);
        inventory1a.setQuantity(1000);
        Inventory inventory1b = new Inventory();
        inventory1b.setProductId(51);
        inventory1b.setQuantity(1000);
        when(inventoryClient.getInventoryById(1000)).thenReturn(inventory1a);
        when(inventoryClient.getInventoryById(1001)).thenReturn(inventory1b);
        Product product1a = new Product();
        product1a.setListPrice(new BigDecimal(100.0).setScale(2));
        Product product1b = new Product();
        product1b.setListPrice(new BigDecimal(10.0).setScale(2));
        when(productClient.getProductById(50)).thenReturn(product1a);
        when(productClient.getProductById(51)).thenReturn(product1b);
        when(invoiceClient.submitInvoice(rivm)).thenReturn(rivm2);
        rivm = service.submitInvoice(rivm);

        verify(customerClient, times(1)).getCustomerById(1);
        verify(inventoryClient, times(2)).getInventoryById(any(Integer.class));
        verify(productClient, times(2)).getProductById(any(Integer.class));
        verify(levelupClient, times(1)).getLevelUpByCustomerId(1);
        verify(rabbit, times(1)).convertAndSend(eq(EXCHANGE),
                eq(ROUTING_KEY), any(Object.class));
        assertEquals(rivm.getPoint(), 40);
    }

    //    public InvoiceViewModel getInvoiceById(int id)
    @Test
    public void testGetInvoiceById() {
        //arrange
        Order rivm2 = new Order();
        rivm2.setInvoiceId(1);
        rivm2.setCustomerId(1);
        rivm2.setPurchaseDate(LocalDate.now());
        InvoiceItem invoiceItem2a = new InvoiceItem();
        invoiceItem2a.setInvoiceItemId(1);
        invoiceItem2a.setInventoryId(1000);
        invoiceItem2a.setQuantity(1);
        invoiceItem2a.setUnitPrice(new BigDecimal(100.00).setScale(2));
        InvoiceItem invoiceItem2b = new InvoiceItem();
        invoiceItem2a.setInvoiceItemId(2);
        invoiceItem2b.setInventoryId(1001);
        invoiceItem2b.setQuantity(10);
        invoiceItem2b.setUnitPrice(new BigDecimal(10.00).setScale(2));
        List<InvoiceItem> invoiceItems2 = new ArrayList<>();
        invoiceItems2.add(invoiceItem2a);
        invoiceItems2.add(invoiceItem2b);
        rivm2.setInvoiceItems(invoiceItems2);
        when(invoiceClient.getInvoiceById(5)).thenReturn(rivm2);
        // act
        rivm2 = service.getInvoiceById(5);
        // assert
        verify(invoiceClient, times(1)).getInvoiceById(5);
    }

    //    public List<InvoiceViewModel> getAllInvoices()
    @Test
    public void testGetAllInvoices() {
        // act
        List<Order> ivms = service.getAllInvoices();
        // assert
        verify(invoiceClient, times(1)).getAllInvoices();
    }

    //    public List<InvoiceViewModel> getInvoicesByCustomerId(int id)
    @Test
    public void testGetInvoiceByCustomerId() {
        // act
        List<Order> ivms = service.getInvoicesByCustomerId(5);
        // assert
        verify(invoiceClient, times(1)).getInvoicesByCustomerId(5);
    }

    //    public Product getProductById(int id)
    @Test
    public void testGetProductsById() {
        Product product = service.getProductById(5);
        verify(productClient, times(1)).getProductById(5);
    }

    //  public List<Product> getProductsInInventory() {
    @Test
    public void testGetProductsInInventory() {
        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(1);
        inventory1.setProductId(10);
        inventory1.setQuantity(900);
        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(2);
        inventory2.setProductId(11);
        inventory2.setQuantity(90);
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inventory1);
        inventories.add(inventory2);

        when(inventoryClient.getAllInventory()).thenReturn(inventories);
        // Product tempProduct = productDao.getProductById(productId);
        Product product1 = new Product();
        product1.setProductId(10);
        product1.setProductDescription("garbage");
        Product product2 = new Product();
        product2.setProductId(11);
        product2.setProductDescription("trash");
        when(productClient.getProductById(10)).thenReturn(product1);
        when(productClient.getProductById(11)).thenReturn(product2);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        List<Product> assertList = service.getProductsInInventory();
        assertEquals(assertList, products);
    }


    @Test
    public void testGetProductByInvoiceId() {
        Order invoice = new Order();
        invoice.setInvoiceId(1);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(10);
        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(11);
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItem1);
        invoiceItems.add(invoiceItem2);

        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(10);
        inventory1.setProductId(100);
        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(11);
        inventory2.setProductId(101);

        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inventory1);
        inventories.add(inventory2);

        Product product1 = new Product();
        product1.setProductId(100);
        product1.setProductDescription("ball");
        Product product2 = new Product();
        product2.setProductId(101);
        product2.setProductDescription("phone");

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        when(invoiceClient.getInvoiceById(1)).thenReturn(invoice);
        when(invoiceClient.getInvoiceItemByInvoiceId(1)).thenReturn(invoiceItems);
        when(inventoryClient.getAllInventory()).thenReturn(inventories);

        when(service.getProductById(100)).thenReturn(product1);
        when(service.getProductById(101)).thenReturn(product2);

        List<Product> assertList = service.getProductByInvoiceId(1);
        assertEquals(assertList, products);
    }

    //    public int getLevelUpPointsByCustomerId(int id)
    @Test
    public void testGetLevelUpPointsByCustomerId() {
        int points = service.getLevelUpPointsByCustomerId(5);
        verify(levelupClient, times(1)).getLevelUpByCustomerId(5);
    }
}