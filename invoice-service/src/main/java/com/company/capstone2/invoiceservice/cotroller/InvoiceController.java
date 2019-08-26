package com.company.capstone2.invoiceservice.cotroller;

import com.company.capstone2.invoiceservice.exception.NotFoundException;
import com.company.capstone2.invoiceservice.model.InvoiceItem;
import com.company.capstone2.invoiceservice.service.InvoiceService;
import com.company.capstone2.invoiceservice.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }
    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public InvoiceViewModel addInvoice(@RequestBody @Valid InvoiceViewModel ivm){
        return service.addInvoice(ivm);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices() {
        return service.getAllInvoices();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {
        InvoiceViewModel  ivm= service.getInvoiceById(id);
        if (ivm==null){
            throw new NotFoundException("Invoice not found, id: "+id);
        } else {
            return ivm;
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateInvoice(@RequestBody @Valid InvoiceViewModel ivm, @PathVariable int id) {
        if (ivm.getInvoiceId()==id){
            service.updateInvoice(ivm);
        } else {
            throw new NotFoundException("path id should match ivm id: "+ ivm.getInvoiceId());
        }
    }

    @DeleteMapping("/invoiceItem/invoice/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteInvoiceItemByInvoiceId(@PathVariable int id) {
        service.deleteInvoiceItemByInvoiceId(id);
    }

    @DeleteMapping("/invoiceItem/inventory/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteInvoiceItemByInventoryId(@PathVariable int id) {
        service.deleteInvoiceItemByInventoryId(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteInvoice(@PathVariable int id) {
        service.deleteInvoice(id);
    }


    @GetMapping("/invoiceitem/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    List<InvoiceItem>  getInvoiceItemByInvoiceId(@PathVariable int id){
        return service. getInvoiceItemByInvoiceId(id);
    }


    @GetMapping("/customer/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    List<InvoiceViewModel> getInvoiceByCustomerId(@PathVariable int id){
        return service.getInvoiceByCustomerId(id);
    }
}
