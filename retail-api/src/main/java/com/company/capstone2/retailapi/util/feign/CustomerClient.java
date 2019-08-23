package com.company.capstone2.retailapi.util.feign;

import com.company.capstone2.retailapi.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="customer-service")
public interface CustomerClient {

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable int id);

}
