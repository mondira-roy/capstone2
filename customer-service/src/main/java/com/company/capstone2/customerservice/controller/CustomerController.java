package com.company.capstone2.customerservice.controller;

import com.company.capstone2.customerservice.exception.NotFoundException;
import com.company.capstone2.customerservice.model.Customer;
import com.company.capstone2.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@CacheConfig(cacheNames = {"customers"})
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @CachePut(key = "#result.getCustomerId()")
    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Customer addCustomer(@RequestBody @Valid Customer customer){
        return service.addCustomer(customer);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @Cacheable
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Customer getCustomerById(@PathVariable int id)throws NotFoundException {
        Customer  customer= service.getCustomerById(id);
        if (customer==null){
            throw new NotFoundException("Customer not found, id: "+id);
        } else {
            return customer;
        }
    }

    @CacheEvict(key = "#customer.getCustomerId()")
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCustomer(@RequestBody Customer customer, @PathVariable int id) {
        if (customer.getCustomerId()==id){
            service.updateCustomer(customer);
        } else {
            throw new NotFoundException("path id should match customer id: "+ customer.getCustomerId());
        }
    }

    @CacheEvict
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCustomer(@PathVariable int id) {
        service.deleteCustomer(id);
    }


}
