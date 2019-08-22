package com.company.capstone2.invoiceservice.dao;
import com.company.capstone2.invoiceservice.dao.InvoiceDao;
import com.company.capstone2.invoiceservice.dao.InvoiceItemDao;
import com.company.capstone2.invoiceservice.model.Invoice;
import com.company.capstone2.invoiceservice.model.InvoiceItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceItemDaoJdbcTemplateImpleTest {

    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp() throws Exception {
        // clear the data base
        List<InvoiceItem> invoiceItems = invoiceItemDao.getAllInvoiceItems();
        invoiceItems.stream().forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));
        // clear the data base
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.stream().forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void testAddInvoiceGetAllInvoicesGetInvoiceById() {
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2000,1,1));
        invoice1 = invoiceDao.addInvoice(invoice1);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setInventoryId(99);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setUnitPrice(new BigDecimal(100.00).setScale(2));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem2.setInventoryId(98);
        invoiceItem2.setQuantity(10);
        invoiceItem2.setUnitPrice(new BigDecimal(10.00).setScale(2));

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItem1);
        invoiceItems.add(invoiceItem2);

        invoiceItem1 = invoiceItemDao.addInvoiceItem(invoiceItem1);
        invoiceItem2 = invoiceItemDao.addInvoiceItem(invoiceItem2);

        // test getAll
        List<InvoiceItem> invoiceitems1 = invoiceItemDao.getAllInvoiceItems();
        assertEquals(invoiceitems1,invoiceItems);

        // test get by Id
        InvoiceItem invoiceItem3 = invoiceItemDao.getInvoiceItemById(invoiceItem1.getInvoiceItemId());
        assertEquals(invoiceItem1,invoiceItem3);
        InvoiceItem invoiceItem4 = invoiceItemDao.getInvoiceItemById(-1);
        assertNull(invoiceItem4);


    }

    @Test
    public void testGetInvoiceItemByInvoiceId(){
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2000,1,1));
        invoice1 = invoiceDao.addInvoice(invoice1);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setInventoryId(99);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setUnitPrice(new BigDecimal(100.00).setScale(2));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem2.setInventoryId(98);
        invoiceItem2.setQuantity(10);
        invoiceItem2.setUnitPrice(new BigDecimal(10.00).setScale(2));

        invoiceItem1 = invoiceItemDao.addInvoiceItem(invoiceItem1);
        invoiceItem2 = invoiceItemDao.addInvoiceItem(invoiceItem2);

        // test get by invoice id
        List<InvoiceItem> invoiceItems = invoiceItemDao.getInvoiceItemByInvoiceId(invoiceItem1.getInvoiceId());
        assertEquals(invoiceItems.size(),2);
    }

    @Test
    public void testUpdateInvoiceDeleteInvoice() {
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2000,1,1));
        invoice1 = invoiceDao.addInvoice(invoice1);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setInventoryId(99);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setUnitPrice(new BigDecimal(100.00).setScale(2));

        // test add
        invoiceItem1 = invoiceItemDao.addInvoiceItem(invoiceItem1);
        // test update
        invoiceItem1.setQuantity(20);
        invoiceItemDao.updateInvoiceItem(invoiceItem1);
        InvoiceItem invoiceItem3 = invoiceItemDao.getInvoiceItemById(invoiceItem1.getInvoiceItemId());
        assertEquals(invoiceItem3.getQuantity(),20);
    }

    @Test
    public void testDeleteInvoiceByInvoiceId(){
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2000,1,1));
        invoice1 = invoiceDao.addInvoice(invoice1);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setInventoryId(99);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setUnitPrice(new BigDecimal(100.00).setScale(2));
        // test add
        invoiceItem1 = invoiceItemDao.addInvoiceItem(invoiceItem1);
        invoiceItemDao.deleteInvoiceItemByInvoiceId(invoice1.getInvoiceId());
        List<InvoiceItem> invoiceItems = invoiceItemDao.getAllInvoiceItems();
        assertEquals(invoiceItems.size(),0);
    }
}