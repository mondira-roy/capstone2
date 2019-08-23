package com.company.capstone2.productservice.util.feign;

import com.company.capstone2.productservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("inventory-service")
public interface InventoryServiceClient {
    @RequestMapping(method= RequestMethod.GET, value = "/inventory/")
    public List<Product> getProductsInInventory();

    @RequestMapping(method= RequestMethod.GET, value = "/inventory/{id}")
    public int getProductIdByInventoryId(int id);
}
