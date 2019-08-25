package com.company.captstone2.adminapi.controller;

import com.company.captstone2.adminapi.exception.NotFoundException;
import com.company.captstone2.adminapi.service.AdminService;
import com.company.captstone2.adminapi.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    AdminService service;

    public InvoiceController(AdminService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public InvoiceViewModel addInvoice(@RequestBody InvoiceViewModel ivm,Principal principal){
        return service.addInvoice(ivm);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices(Principal principal) {
        return service.getAllInvoices();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel getInvoiceById(@PathVariable int id,Principal principal) {
        InvoiceViewModel  ivm= service.getInvoiceById(id);
        if (ivm==null){
            throw new NotFoundException("Invoice not found, id: "+id);
        } else {
            return ivm;
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateInvoice(@RequestBody InvoiceViewModel ivm, @PathVariable int id,Principal principal) {
        if (ivm.getInvoiceId()==id){
            service.updateInvoice(ivm,id);
        } else {
            throw new NotFoundException("path id should match ivm id: "+ ivm.getInvoiceId());
        }
    }
//
//    @DeleteMapping("/invoiceItem/invoice/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public void deleteInvoiceItemByInvoiceId(@PathVariable int id) {
//        service.deleteInvoiceItemByInvoiceId(id);
//    }
//
//    @DeleteMapping("/invoiceItem/inventory/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public void deleteInvoiceItemByInventoryId(@PathVariable int id) {
//        service.deleteInvoiceItemByInventoryId(id);
//    }


    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteInvoice(@PathVariable int id, Principal principal) {
        service.deleteInvoice(id);
    }


//    @GetMapping("/invoiceitem/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    List<InvoiceItem>  getInvoiceItemByInvoiceId(@PathVariable int id){
//        return service. getInvoiceItemByInvoiceId(id);
//    }


//    @GetMapping("/customer/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    List<InvoiceViewModel> getInvoiceByCustomerId(@PathVariable int id){
//        return service.getInvoiceByCustomerId(id);
//    }
}
