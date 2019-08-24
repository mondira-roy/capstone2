package com.company.capstone2.productservice.util.feign;

import com.company.capstone2.productservice.model.Inventory;
import com.company.capstone2.productservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("inventory-service")
public interface InventoryServiceClient {

    @RequestMapping(method = RequestMethod.DELETE,value = "/inventory/product/{id}")
    public void deleteInventoryByProductId(@PathVariable int id);
}
