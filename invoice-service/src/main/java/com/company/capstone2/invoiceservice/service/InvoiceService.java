package com.company.capstone2.invoiceservice.service;

import com.company.capstone2.invoiceservice.dao.InvoiceDao;
import com.company.capstone2.invoiceservice.dao.InvoiceItemDao;
import com.company.capstone2.invoiceservice.exception.NotFoundException;
import com.company.capstone2.invoiceservice.model.Invoice;
import com.company.capstone2.invoiceservice.model.InvoiceItem;
import com.company.capstone2.invoiceservice.viewModel.InvoiceViewModel;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceService {
    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Autowired
    InvoiceDao invoiceDao;

    public InvoiceService(InvoiceItemDao invoiceItemDao, InvoiceDao invoiceDao) {
        this.invoiceItemDao = invoiceItemDao;
        this.invoiceDao = invoiceDao;
    }

    // helper method
    private Invoice buildInvoiceFromInvoiceViewModel(InvoiceViewModel ivm) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(ivm.getInvoiceId());
        invoice.setCustomerId(ivm.getCustomerId());
        invoice.setPurchaseDate(ivm.getPurchaseDate());
        return invoice;
    }

    private InvoiceViewModel buildIvmFromInvoice(Invoice invoice) {
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        List<InvoiceItem> tempItems = invoiceItemDao.getInvoiceItemByInvoiceId(invoice.getInvoiceId());
        ivm.setInvoiceItems(tempItems);
        return ivm;
    }

    @Transactional
    public InvoiceViewModel addInvoice(InvoiceViewModel ivm) {
        // build invoice from ivm
        Invoice invoice = buildInvoiceFromInvoiceViewModel(ivm);
        // save the invoice to db though dao
        invoice = invoiceDao.addInvoice(invoice);
        // save invoice item in invoice
        List<InvoiceItem> updateInvoiceItems = new ArrayList<>();
        List<InvoiceItem> invoiceItems = ivm.getInvoiceItems();
        Invoice finalInvoice = invoice;
        invoiceItems.stream().forEach(invoiceItem -> {
            invoiceItem.setInvoiceId(finalInvoice.getInvoiceId());
            InvoiceItem tempItem = invoiceItemDao.addInvoiceItem(invoiceItem);
            updateInvoiceItems.add(tempItem);
        });
        // update ivm for ids
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setInvoiceItems(updateInvoiceItems);
        return ivm;
    }

    @Transactional
    public List<InvoiceViewModel> getAllInvoices() {
        List<InvoiceViewModel> ivms = new ArrayList<>();
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.stream().forEach(invoice -> {
            InvoiceViewModel tempIvm = buildIvmFromInvoice(invoice);
            ivms.add(tempIvm);
        });
        return ivms;
    }

    public InvoiceViewModel getInvoiceById(int id){
        return buildIvmFromInvoice(invoiceDao.getInvoiceById(id));
    }

    @Transactional
    public void updateInvoice(InvoiceViewModel ivm){
        Invoice invoice = buildInvoiceFromInvoiceViewModel(ivm);
        // update for invoice itself
        invoiceDao.updateInvoice(invoice);
        // update for invoice items in the invoice
        ivm.getInvoiceItems().stream().forEach(invoiceItem -> {
            invoiceItem.setInvoiceId(ivm.getInvoiceId());
            try {
                invoiceItemDao.updateInvoiceItem(invoiceItem);
            } catch (EmptyResultDataAccessException e) {
                throw new NotFoundException("InvoiceItem not found, id:"+invoiceItem.getInvoiceItemId());
            }
        });
    }

    @Transactional
    public void deleteInvoice(int id){
        deleteInvoiceItemByInvoiceId(id);
        invoiceDao.deleteInvoice(id);
    }

    @Transactional
    public List<InvoiceViewModel> getInvoiceByCustomerId(int id){
        List<Invoice> invoices = invoiceDao.getInvoiceByCustomerId(id);
        List<InvoiceViewModel> ivms = new ArrayList<>();
        invoices.stream().forEach(invoice -> {
            ivms.add(buildIvmFromInvoice(invoice));
        });
        return  ivms;
    }
    public List<InvoiceItem> getInvoiceItemByInvoiceId(int id){
        return invoiceItemDao.getInvoiceItemByInvoiceId(id);
    }
    public void deleteInvoiceItemByInvoiceId(int id){
        invoiceItemDao.deleteInvoiceItemByInvoiceId(id);
    }
    public void deleteInvoiceItemByInventoryId(int id){
        invoiceItemDao.deleteInvoiceItemByInventoryId(id);
    }
}
