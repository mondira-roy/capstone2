package com.company.capstone2.retailapi.util.feign;

import com.company.capstone2.retailapi.viewModel.RetailInvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


}
