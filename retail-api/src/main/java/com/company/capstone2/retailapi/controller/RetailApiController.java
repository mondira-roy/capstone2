package com.company.capstone2.retailapi.controller;

import com.company.capstone2.retailapi.Service.RetailApiService;
import com.company.capstone2.retailapi.model.Product;
import com.company.capstone2.retailapi.viewModel.RetailInvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class RetailApiController {

    @Autowired
    RetailApiService service;

    public RetailApiController(RetailApiService service) {
        this.service = service;
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public RetailInvoiceViewModel submitInvoice(@RequestBody RetailInvoiceViewModel ivm) {
        return service.submitInvoice(ivm);
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public RetailInvoiceViewModel getInvoiceById(@PathVariable int id) {
        return service.getInvoiceById(id);
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<RetailInvoiceViewModel> getAllInvoices() {
        return service.getAllInvoices();
    }

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<RetailInvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        return service.getInvoicesByCustomerId(id);
    }

    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
    public List<Product> getProductsInInventory() {
        return service.getProductsInInventory();
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        return service.getProductById(id);
    }

    @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)
    public List<Product> getProductByInvoiceId(@PathVariable int id) {
        return service.getProductByInvoiceId(id);
    }

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(int id) {
        return service.getLevelUpPointsByCustomerId(id);
    }

}
