package com.company.capstone2.invoiceservice.dao;

import com.company.capstone2.invoiceservice.model.Invoice;

import java.util.List;

public interface InvoiceDao {

    Invoice addInvoice(Invoice invoice);

    List<Invoice> getAllInvoices();

    Invoice getInvoiceById(int id);

    void updateInvoice(Invoice invoice);

    void deleteInvoice(int id);

    List<Invoice> getInvoiceByCustomerId(int id);
}
