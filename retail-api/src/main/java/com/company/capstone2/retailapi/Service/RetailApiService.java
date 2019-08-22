package com.company.capstone2.retailapi.Service;

import com.company.capstone2.retailapi.model.InvoiceItem;
import com.company.capstone2.retailapi.model.Product;
import com.company.capstone2.retailapi.util.feign.InvoiceClient;
import com.company.capstone2.retailapi.util.feign.LevelupClient;
import com.company.capstone2.retailapi.util.feign.ProductClient;
import com.company.capstone2.retailapi.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RetailApiService {

    @Autowired
    InvoiceClient invoiceClient;
    @Autowired
    LevelupClient levelupClient;
    @Autowired
    ProductClient productClient;

    public RetailApiService(InvoiceClient invoiceClient, LevelupClient levelupClient, ProductClient productClient) {
        this.invoiceClient = invoiceClient;
        this.levelupClient = levelupClient;
        this.productClient = productClient;
    }

    public InvoiceViewModel submitInvoice(InvoiceViewModel ivm) {
        // add invoice
        ivm = invoiceClient.submitInvoice(ivm);
        // for every 50 dollars, add 10 points
        List<InvoiceItem> invoiceItems = ivm.getInvoiceItems();
      AtomicInteger tempPoints = new AtomicInteger(ivm.getPoint());
        invoiceItems.stream().forEach(invoiceItem -> {
            BigDecimal subtotal = invoiceItem.getUnitPrice().multiply(BigDecimal.valueOf(invoiceItem.getQuantity()));
            tempPoints.addAndGet(subtotal.intValue()/5);
        });
        ivm.setPoint(tempPoints.intValue());
        return ivm;
    }


    public InvoiceViewModel getInvoiceById(int id) {
        return invoiceClient.getInvoiceById(id);
    }


    public List<InvoiceViewModel> getAllInvoices() {
        return invoiceClient.getAllInvoices();
    }


    public List<InvoiceViewModel> getInvoicesByCustomerId(int id) {
        return invoiceClient.getInvoicesByCustomerId(id);
    }


    public List<Product> getProductsInInventory() {
        return productClient.getProductsInInventory();
    }


    public Product getProductById(int id) {
        return productClient.getProductById(id);
    }


    public List<Product> getProductByInvoiceId(int id) {
        return productClient.getProductByInvoiceId(id);
    }


    public int getLevelUpPointsByCustomerId(int id) {
        return levelupClient.getLevelUpPointsByCustomerId(id);
    }

}
