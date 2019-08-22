package com.company.capstone2.retailapi.util.feign;

import com.company.capstone2.retailapi.model.Invoice;
import com.company.capstone2.retailapi.model.Product;
import com.company.capstone2.retailapi.viewModel.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="invoice-service")
public interface InvoiceClient {

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public InvoiceViewModel submitInvoice(@RequestBody InvoiceViewModel invoice);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id);

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices();

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id);


}
