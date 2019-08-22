package com.company.capstone2.retailapi.Service;

import com.company.capstone2.retailapi.model.Invoice;
import com.company.capstone2.retailapi.model.InvoiceItem;
import com.company.capstone2.retailapi.model.Levelup;
import com.company.capstone2.retailapi.model.Product;
import com.company.capstone2.retailapi.util.feign.InvoiceClient;
import com.company.capstone2.retailapi.util.feign.LevelupClient;
import com.company.capstone2.retailapi.util.feign.ProductClient;
import com.company.capstone2.retailapi.viewModel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RetailApiServiceTest {

    @Mock
    InvoiceClient invoiceClient;
    @Mock
    LevelupClient levelupClient;
    @Mock
    ProductClient productClient;
    @InjectMocks
    RetailApiService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //    public InvoiceViewModel submitInvoice(InvoiceViewModel ivm)
    @Test
    public void testSubmitInvoice() {

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(10);
        invoice1.setPurchaseDate(LocalDate.of(2000, 1, 1));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(100);
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(1000);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setUnitPrice(new BigDecimal(100.00).setScale(2));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceItemId(101);
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(1001);
        invoiceItem2.setQuantity(10);
        invoiceItem2.setUnitPrice(new BigDecimal(10.00).setScale(2));

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItem1);
        invoiceItems.add(invoiceItem2);

        // construct expected ivm
        InvoiceViewModel ivm1 = new InvoiceViewModel();
        ivm1.setInvoiceId(invoice1.getInvoiceId());
        ivm1.setCustomerId(invoice1.getCustomerId());
        ivm1.setPurchaseDate(invoice1.getPurchaseDate());
        ivm1.setPoint(40);
        ivm1.setInvoiceItems(invoiceItems);

        // construct input ivm
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice1.getInvoiceId());
        ivm.setCustomerId(invoice1.getCustomerId());
        ivm.setPurchaseDate(invoice1.getPurchaseDate());
        ivm.setInvoiceItems(invoiceItems);

        // mock performance arrangement
        when(invoiceClient.submitInvoice(ivm)).thenReturn(ivm);
        // test
        // after passing it to service, ivm should have:
        // invoiceId;                      # generated when going though invoice dao
        // customerId;                     # comes with input
        // purchaseDate;                   # comes with input
        // List<InvoiceItem> invoiceItems; # comes with input but without invoiceItemId, generated after dao
        // point;                          # calculated using the total of price in invoiceItem
        ivm = service.submitInvoice(ivm);
        assertEquals(ivm, ivm1);
    }

    //    public InvoiceViewModel getInvoiceById(int id)
    @Test
    public void testGetInvoiceById() {
        // act
        InvoiceViewModel ivm = service.getInvoiceById(5);
        // assert
        verify(invoiceClient, times(1)).getInvoiceById(5);
    }

    //    public List<InvoiceViewModel> getAllInvoices()
    @Test
    public void testGetAllInvoices() {
        // act
        List<InvoiceViewModel> ivms = service.getAllInvoices();
        // assert
        verify(invoiceClient, times(1)).getAllInvoices();
    }

    //    public List<InvoiceViewModel> getInvoicesByCustomerId(int id)
    @Test
    public void testGetInvoiceByCustomerId() {
        // act
        List<InvoiceViewModel> ivms = service.getInvoicesByCustomerId(5);
        // assert
        verify(invoiceClient, times(1)).getInvoicesByCustomerId(5);
    }

    //    public List<Product> getProductsInInventory()
    @Test
    public void testGetProductsInInventory() {
        // act
        List<Product> products = service.getProductsInInventory();
        // assert
        verify(productClient, times(1)).getProductsInInventory();
    }

    //    public Product getProductById(int id)
    @Test
    public void testGetProductsById() {
        Product product = service.getProductById(5);
        verify(productClient, times(1)).getProductById(5);
    }

    //    public List<Product> getProductByInvoiceId(int id)
    @Test
    public void testGetProductByInvoiceId() {
        List<Product> products = service.getProductByInvoiceId(5);
        verify(productClient, times(1)).getProductByInvoiceId(5);
    }
    //    public int getLevelUpPointsByCustomerId(int id)
    @Test
    public void testGetLevelUpPointsByCustomerId() {
        int points = service.getLevelUpPointsByCustomerId(5);
        verify(levelupClient, times(1)).getLevelUpPointsByCustomerId(5);
    }
}