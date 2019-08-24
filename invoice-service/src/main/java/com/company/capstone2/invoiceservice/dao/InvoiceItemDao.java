package com.company.capstone2.invoiceservice.dao;

import com.company.capstone2.invoiceservice.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    InvoiceItem addInvoiceItem(InvoiceItem invoice);

    List<InvoiceItem> getAllInvoiceItems();

    InvoiceItem getInvoiceItemById(int id);

    void updateInvoiceItem(InvoiceItem invoice);

    void deleteInvoiceItem(int id);

    List<InvoiceItem> getInvoiceItemByInvoiceId(int id);

    void deleteInvoiceItemByInvoiceId(int id);

    void deleteInvoiceItemByInventoryId(int id);
}
