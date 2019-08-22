package com.company.capstone2.invoiceservice.dao;

import com.company.capstone2.invoiceservice.model.Invoice;
import com.company.capstone2.invoiceservice.model.InvoiceItem;
import net.bytebuddy.asm.Advice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceDaoJdbcTemplateImpleTest {

    @Autowired
    InvoiceDao invoiceDao;

    @Autowired
    InvoiceItemDao invoiceItemDao;

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

        // test add
        invoice1 = invoiceDao.addInvoice(invoice1);
        // test getAll
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        assertEquals(invoices.size(),1);
        // test get by Id
        Invoice invoice2 = invoiceDao.getInvoiceById(invoice1.getInvoiceId());
        assertEquals(invoice1,invoice2);
        invoice2 = invoiceDao.getInvoiceById(-1);
    }

    @Test
    public void testUpdateInvoiceDeleteInvoice() {
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2000,1,1));

        invoice1 = invoiceDao.addInvoice(invoice1);
        invoice1.setPurchaseDate(LocalDate.of(1999,1,1));
        invoiceDao.updateInvoice(invoice1);
        assertEquals(invoiceDao.getInvoiceById(invoice1.getInvoiceId()).getPurchaseDate(),LocalDate.of(1999,1,1));
    }

    @Test
    public void testGetInvoiceByCustomerId(){
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2000,1,1));

        invoice1 = invoiceDao.addInvoice(invoice1);
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice1);

        List<Invoice> invoiceList2 = invoiceDao.getInvoiceByCustomerId(1);
        assertEquals(invoiceList2,invoiceList);
    }
}