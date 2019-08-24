package com.company.capstone2.retailapi.util.feign;

import com.company.capstone2.retailapi.model.InvoiceItem;
import com.company.capstone2.retailapi.viewModel.RetailInvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="invoice-service")
public interface InvoiceClient {

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public RetailInvoiceViewModel submitInvoice(@RequestBody RetailInvoiceViewModel invoice);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public RetailInvoiceViewModel getInvoiceById(@PathVariable int id);

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<RetailInvoiceViewModel> getAllInvoices();

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<RetailInvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id);

    @RequestMapping(value = "/invoices/invoiceitem/{id}", method = RequestMethod.GET)
    List<InvoiceItem>  getInvoiceItemByInvoiceId(@PathVariable int id);

}
