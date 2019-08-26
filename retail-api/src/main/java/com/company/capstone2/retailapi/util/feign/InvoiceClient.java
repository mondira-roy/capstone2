package com.company.capstone2.retailapi.util.feign;

import com.company.capstone2.retailapi.model.InvoiceItem;
import com.company.capstone2.retailapi.viewModel.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="invoice-service")
public interface InvoiceClient {

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public Order submitInvoice(@RequestBody Order invoice);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public Order getInvoiceById(@PathVariable int id);

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<Order> getAllInvoices();

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<Order> getInvoicesByCustomerId(@PathVariable int id);

    @RequestMapping(value = "/invoices/invoiceitem/{id}", method = RequestMethod.GET)
    List<InvoiceItem>  getInvoiceItemByInvoiceId(@PathVariable int id);

}
