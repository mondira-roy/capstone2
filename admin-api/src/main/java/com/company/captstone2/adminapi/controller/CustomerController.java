package com.company.captstone2.adminapi.controller;

import com.company.captstone2.adminapi.exception.NotFoundException;
import com.company.captstone2.adminapi.model.Customer;
import com.company.captstone2.adminapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RefreshScope
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    AdminService service;

    public CustomerController(AdminService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Customer addCustomer(@RequestBody Customer customer,Principal principal){
        return service.addCustomer(customer);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Customer> getAllCustomers(Principal principal) {
        return service.getAllCustomers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Customer getCustomerById(@PathVariable int id,Principal principal)throws NotFoundException {
        Customer  customer= service.getCustomerById(id);
        if (customer==null){
            throw new NotFoundException("Customer not found, id: "+id);
        } else {
            return customer;
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCustomer(@RequestBody Customer customer, @PathVariable int id,Principal principal) {
        if (customer.getCustomerId()==id){
            service.updateCustomer(customer,id);
        } else {
            throw new NotFoundException("path id should match customer id: "+ customer.getCustomerId());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCustomer(@PathVariable int id, Principal principal) {
        service.deleteCustomer(id);
    }

}
