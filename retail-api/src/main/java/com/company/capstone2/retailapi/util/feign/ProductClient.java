package com.company.capstone2.retailapi.util.feign;

import com.company.capstone2.retailapi.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="product-service")
public interface ProductClient {
    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
    public List<Product> getProductsInInventory();

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id);

    @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)
    public List<Product> getProductByInvoiceId(@PathVariable int id);

}
