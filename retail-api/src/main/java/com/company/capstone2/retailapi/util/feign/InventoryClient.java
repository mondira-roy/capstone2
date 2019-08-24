package com.company.capstone2.retailapi.util.feign;

import com.company.capstone2.retailapi.model.Customer;
import com.company.capstone2.retailapi.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="inventory-service")
public interface InventoryClient {

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.GET)
    public Inventory getInventoryById(@PathVariable int id);

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public List<Inventory>  getAllInventory();


}
