package com.company.captstone2.adminapi.service;

import com.company.captstone2.adminapi.model.*;
import com.company.captstone2.adminapi.util.feign.*;
import com.company.captstone2.adminapi.viewModel.InvoiceViewModel;
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

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceTest {
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

    @InjectMocks
    AdminService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCustomerCRUD(){
        Customer customer = new Customer();
        customer.setCustomerId(1);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        // add
        when(customerClient.addCustomer(customer)).thenReturn(customer);
        customer = service.addCustomer(customer);
        verify(customerClient,times(1)).addCustomer(any(Customer.class));
        // get all
        when(customerClient.getAllCustomers()).thenReturn(customers);
        customers = service.getAllCustomers();
        verify(customerClient,times(1)).getAllCustomers();
        // get by id
        when(customerClient.getCustomerById(1)).thenReturn(customer);
        customer = service.getCustomerById(1);
        verify(customerClient,times(1)).getCustomerById(1);
        // update
        service.updateCustomer(customer,1);
        verify(customerClient,times(1)).updateCustomer(customer,1);
        // delete
        service.deleteCustomer(1);
        verify(customerClient,times(1)).deleteCustomer(1);
    }

    @Test
    public void testLevelupCRUD(){
        Levelup levelup = new Levelup();
        levelup.setLevelupId(1);
        List<Levelup> levelups = new ArrayList<>();
        levelups.add(levelup);
        // add
        when(levelupClient.addLevelup(levelup)).thenReturn(levelup);
        levelup = service.addLevelup(levelup);
        verify(levelupClient,times(1)).addLevelup(any(Levelup.class));
        // get all
        when(levelupClient.getAllLevelups()).thenReturn(levelups);
        levelups = service.getAllLevelups();
        verify(levelupClient,times(1)).getAllLevelups();
        // get by id
        when(levelupClient.getLevelupById(1)).thenReturn(levelup);
        levelup = service.getLevelupById(1);
        verify(levelupClient,times(1)).getLevelupById(1);
        // update
        service.updateLevelup(levelup,1);
        verify(levelupClient,times(1)).updateLevelup(levelup,1);
        // delete
        service.deleteLevelup(1);
        verify(levelupClient,times(1)).deleteLevelup(1);
    }

    @Test
    public void testInventoryCRUD(){
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        List<Inventory> inventorys = new ArrayList<>();
        inventorys.add(inventory);
        // add
        when(inventoryClient.addInventory(inventory)).thenReturn(inventory);
        inventory = service.addInventory(inventory);
        verify(inventoryClient,times(1)).addInventory(any(Inventory.class));
        // get all
        when(inventoryClient.getAllInventory()).thenReturn(inventorys);
        inventorys = service.getAllInventory();
        verify(inventoryClient,times(1)).getAllInventory();
        // get by id
        when(inventoryClient.getInventoryById(1)).thenReturn(inventory);
        inventory = service.getInventoryById(1);
        verify(inventoryClient,times(1)).getInventoryById(1);
        // update
        service.updateInventory(inventory,1);
        verify(inventoryClient,times(1)).updateInventory(inventory,1);
        // delete
        service.deleteInventory(1);
        verify(inventoryClient,times(1)).deleteInventory(1);
    }

    @Test
    public void testProductCRUD(){
        Product product = new Product();
        product.setProductId(1);
        List<Product> products = new ArrayList<>();
        products.add(product);
        // add
        when(productClient.addProduct(product)).thenReturn(product);
        product = service.addProduct(product);
        verify(productClient,times(1)).addProduct(any(Product.class));
        // get all
        when(productClient.getAllProducts()).thenReturn(products);
        products = service.getAllProducts();
        verify(productClient,times(1)).getAllProducts();
        // get by id
        when(productClient.getProductById(1)).thenReturn(product);
        product = service.getProductById(1);
        verify(productClient,times(1)).getProductById(1);
        // update
        service.updateProduct(product,1);
        verify(productClient,times(1)).updateProduct(product,1);
        // delete
        service.deleteProduct(1);
        verify(productClient,times(1)).deleteProduct(1);
    }

    @Test
    public void testInvoiceCRUD(){
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setInvoiceId(1);
        List<InvoiceViewModel> invoices = new ArrayList<>();
        invoices.add(invoice);
        // add
        when(invoiceClient.addInvoice(invoice)).thenReturn(invoice);
        invoice = service.addInvoice(invoice);
        verify(invoiceClient,times(1)).addInvoice(any(InvoiceViewModel.class));
        // get all
        when(invoiceClient.getAllInvoices()).thenReturn(invoices);
        invoices = service.getAllInvoices();
        verify(invoiceClient,times(1)).getAllInvoices();
        // get by id
        when(invoiceClient.getInvoiceById(1)).thenReturn(invoice);
        invoice = service.getInvoiceById(1);
        verify(invoiceClient,times(1)).getInvoiceById(1);
        // update
        service.updateInvoice(invoice,1);
        verify(invoiceClient,times(1)).updateInvoice(invoice,1);
        // delete
        service.deleteInvoice(1);
        verify(invoiceClient,times(1)).deleteInvoice(1);
    }
}