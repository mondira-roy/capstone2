package com.company.captstone2.adminapi.util.feign;

import com.company.captstone2.adminapi.model.Invoice;
import com.company.captstone2.adminapi.viewModel.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="invoice-service")
public interface InvoiceClient {

    @RequestMapping(value = "/invoices",method = RequestMethod.POST)
    public InvoiceViewModel addInvoice(@RequestBody InvoiceViewModel invoice);

    @RequestMapping(value = "/invoices",method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices();

    @RequestMapping(value = "/invoices/{id}",method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id);

    @RequestMapping(value = "/invoices/{id}",method = RequestMethod.PUT)
    public void updateInvoice(@RequestBody InvoiceViewModel invoice, @PathVariable int id);

    @RequestMapping(value = "/invoices/{id}",method = RequestMethod.DELETE)
    public void deleteInvoice(@PathVariable int id);

}