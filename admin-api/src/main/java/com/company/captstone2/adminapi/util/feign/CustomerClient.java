package com.company.captstone2.adminapi.util.feign;


import com.company.captstone2.adminapi.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="customer-service")
public interface CustomerClient {


    @RequestMapping(value = "/customers",method = RequestMethod.POST)
    public Customer addCustomer(@RequestBody Customer customer);

    @RequestMapping(value = "/customers",method = RequestMethod.GET)
    public List<Customer> getAllCustomers();

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable int id);

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.PUT)
    public void updateCustomer(@RequestBody Customer customer, @PathVariable int id);

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable int id);

}
