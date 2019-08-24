package com.company.capstone2.invoiceservice.service;

import com.company.capstone2.invoiceservice.dao.InvoiceDao;
import com.company.capstone2.invoiceservice.dao.InvoiceItemDao;
import com.company.capstone2.invoiceservice.exception.NotFoundException;
import com.company.capstone2.invoiceservice.model.Invoice;
import com.company.capstone2.invoiceservice.model.InvoiceItem;
import com.company.capstone2.invoiceservice.viewModel.InvoiceViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceServiceTest {

    @Mock
    InvoiceItemDao invoiceItemDao;
    @Mock
    InvoiceDao invoiceDao;
    @InjectMocks
    InvoiceService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //    public InvoiceViewModel addInvoice(InvoiceViewModel ivm)
    //    public List<InvoiceViewModel> getAllInvoices()
    //    public InvoiceViewModel getInvoiceById(int id)
    @Test
    public void testAddInvoiceGetAllInvoicesGetInvoiceById() {
        //input to the service-layer
        InvoiceViewModel ivm1a = new InvoiceViewModel();
        ivm1a.setCustomerId(22);
        ivm1a.setPurchaseDate(LocalDate.of(2010, 1, 1));

        //input to dao
        InvoiceItem invoiceItem1a = new InvoiceItem();
        invoiceItem1a.setInvoiceId(11);
        invoiceItem1a.setInventoryId(101);
        invoiceItem1a.setQuantity(10);
        invoiceItem1a.setUnitPrice(new BigDecimal(10.00).setScale(2));

        InvoiceItem invoiceItem2a = new InvoiceItem();
        invoiceItem2a.setInvoiceId(11);
        invoiceItem2a.setInventoryId(102);
        invoiceItem2a.setQuantity(1);
        invoiceItem2a.setUnitPrice(new BigDecimal(100.00).setScale(2));

        Invoice invoice1a = new Invoice();
        invoice1a.setCustomerId(ivm1a.getCustomerId());
        invoice1a.setPurchaseDate(ivm1a.getPurchaseDate());

        List<InvoiceItem> invoiceItems1a = new ArrayList<>();
        invoiceItems1a.add(invoiceItem1a);
        invoiceItems1a.add(invoiceItem2a);

        // input ivm has the list of pre-processed list
        ivm1a.setInvoiceItems(invoiceItems1a);

        // outputs from Dao
        InvoiceItem invoiceItem1b = new InvoiceItem();
        invoiceItem1b.setInvoiceItemId(1);
        invoiceItem1b.setInvoiceId(11);
        invoiceItem1b.setInventoryId(101);
        invoiceItem1b.setQuantity(10);
        invoiceItem1b.setUnitPrice(new BigDecimal(10.00).setScale(2));

        InvoiceItem invoiceItem2b = new InvoiceItem();
        invoiceItem2b.setInvoiceItemId(2);
        invoiceItem2b.setInvoiceId(11);
        invoiceItem2b.setInventoryId(102);
        invoiceItem2b.setQuantity(1);
        invoiceItem2b.setUnitPrice(new BigDecimal(100.00).setScale(2));

        List<InvoiceItem> invoiceItems1b = new ArrayList<>();
        invoiceItems1b.add(invoiceItem1b);
        invoiceItems1b.add(invoiceItem2b);

        // expected return from service layer
        InvoiceViewModel ivm1b = new InvoiceViewModel();
        ivm1b.setInvoiceId(11);
        ivm1b.setCustomerId(22);
        ivm1b.setPurchaseDate(LocalDate.of(2010, 1, 1));
        ivm1b.setInvoiceItems(invoiceItems1b);

        Invoice invoice1b = new Invoice();
        invoice1b.setInvoiceId(ivm1b.getInvoiceId());
        invoice1b.setCustomerId(ivm1b.getCustomerId());
        invoice1b.setPurchaseDate(ivm1b.getPurchaseDate());

        when(invoiceItemDao.addInvoiceItem(invoiceItem1a)).thenReturn(invoiceItem1b);
        when(invoiceItemDao.addInvoiceItem(invoiceItem2a)).thenReturn(invoiceItem2b);
        when(invoiceDao.addInvoice(invoice1a)).thenReturn(invoice1b);
        when(invoiceDao.getInvoiceById(11)).thenReturn(invoice1b);
//        invoiceItemDao.addInvoiceItem(invoiceItem)
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        ivmList.add(ivm1b);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1b);

        when(invoiceDao.getAllInvoices()).thenReturn(invoices);
        when(invoiceItemDao.getInvoiceItemByInvoiceId(11)).thenReturn(invoiceItems1b);

        // Act
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setCustomerId(22);
        ivm.setPurchaseDate(LocalDate.of(2010, 1, 1));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInventoryId(101);
        invoiceItem1.setQuantity(10);
        invoiceItem1.setUnitPrice(new BigDecimal(10.00).setScale(2));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInventoryId(102);
        invoiceItem2.setQuantity(1);
        invoiceItem2.setUnitPrice(new BigDecimal(100.00).setScale(2));

        List<InvoiceItem> invoiceItems1 = new ArrayList<>();
        invoiceItems1.add(invoiceItem1);
        invoiceItems1.add(invoiceItem2);
        ivm.setInvoiceItems(invoiceItems1);

        // test add
        ivm = service.addInvoice(ivm);
        assertEquals(ivm, ivm1b);

        // test get all
        List<InvoiceViewModel> ivmList1 = service.getAllInvoices();
        assertEquals(ivmList, ivmList1);

        // test get by id
        ivm = service.getInvoiceById(11);
        assertEquals(ivm, ivm1b);

    }


    //    public void updateInvoice(InvoiceViewModel ivm)
    //    public void deleteInvoice(int id)
    @Test
    public void testUpdateDeleteInvoiceById() {
    // outputs from Dao
        InvoiceItem invoiceItem1b = new InvoiceItem();
        invoiceItem1b.setInvoiceItemId(1);
        invoiceItem1b.setInvoiceId(11);
        invoiceItem1b.setInventoryId(101);
        invoiceItem1b.setQuantity(10);
        invoiceItem1b.setUnitPrice(new BigDecimal(10.00).setScale(2));

        InvoiceItem invoiceItem2b = new InvoiceItem();
        invoiceItem2b.setInvoiceItemId(2);
        invoiceItem2b.setInvoiceId(11);
        invoiceItem2b.setInventoryId(102);
        invoiceItem2b.setQuantity(1);
        invoiceItem2b.setUnitPrice(new BigDecimal(100.00).setScale(2));

        List<InvoiceItem> invoiceItems1b = new ArrayList<>();
        invoiceItems1b.add(invoiceItem1b);
        invoiceItems1b.add(invoiceItem2b);

        // expected return from service layer
        InvoiceViewModel ivm1b = new InvoiceViewModel();
        ivm1b.setInvoiceId(11);
        ivm1b.setCustomerId(22);
        ivm1b.setPurchaseDate(LocalDate.of(2010, 1, 1));
        ivm1b.setInvoiceItems(invoiceItems1b);

        Invoice invoice1b = new Invoice();
        invoice1b.setInvoiceId(ivm1b.getInvoiceId());
        invoice1b.setCustomerId(ivm1b.getCustomerId());
        invoice1b.setPurchaseDate(ivm1b.getPurchaseDate());

        service.updateInvoice(ivm1b);
        verify(invoiceDao, times(1)).updateInvoice(invoice1b);
        verify(invoiceItemDao, times(2)).updateInvoiceItem(any());

        service.deleteInvoice(11);
        verify(invoiceDao, times(1)).deleteInvoice(11);
        verify(invoiceItemDao, times(1)).deleteInvoiceItemByInvoiceId(11);
    }

    //    public List<InvoiceViewModel> getInvoiceByCustomerId(int id)
    @Test
    public  void testGetInvoiceByCustomerId(){
        // outputs from Dao
        InvoiceItem invoiceItem1b = new InvoiceItem();
        invoiceItem1b.setInvoiceItemId(1);
        invoiceItem1b.setInvoiceId(11);
        invoiceItem1b.setInventoryId(101);
        invoiceItem1b.setQuantity(10);
        invoiceItem1b.setUnitPrice(new BigDecimal(10.00).setScale(2));

        InvoiceItem invoiceItem2b = new InvoiceItem();
        invoiceItem2b.setInvoiceItemId(2);
        invoiceItem2b.setInvoiceId(11);
        invoiceItem2b.setInventoryId(102);
        invoiceItem2b.setQuantity(1);
        invoiceItem2b.setUnitPrice(new BigDecimal(100.00).setScale(2));

        List<InvoiceItem> invoiceItems1b = new ArrayList<>();
        invoiceItems1b.add(invoiceItem1b);
        invoiceItems1b.add(invoiceItem2b);

        // expected return from service layer
        InvoiceViewModel ivm1b = new InvoiceViewModel();
        ivm1b.setInvoiceId(11);
        ivm1b.setCustomerId(22);
        ivm1b.setPurchaseDate(LocalDate.of(2010, 1, 1));
        ivm1b.setInvoiceItems(invoiceItems1b);

        Invoice invoice1b = new Invoice();
        invoice1b.setInvoiceId(ivm1b.getInvoiceId());
        invoice1b.setCustomerId(ivm1b.getCustomerId());
        invoice1b.setPurchaseDate(ivm1b.getPurchaseDate());
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1b);

        when(invoiceDao.getInvoiceByCustomerId(22)).thenReturn(invoices);
        when(invoiceItemDao.getInvoiceItemByInvoiceId(11)).thenReturn(invoiceItems1b);
        List<InvoiceViewModel> ivmList  = service.getInvoiceByCustomerId(22);
        assertEquals(ivmList.get(0),ivm1b);
        assertEquals(ivmList.size(),1);
    }

}