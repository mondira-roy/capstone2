package com.company.capstone2.productservice.controller;

import com.company.capstone2.productservice.dao.ProductDao;
import com.company.capstone2.productservice.model.Product;
import com.company.capstone2.productservice.serviceLayer.ProductService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RefreshScope
@CacheConfig(cacheNames = {"products"})
public class ProductServiceController {
    @Autowired
    ProductService service;


    @CachePut(key = "#result.getProductId()")
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody @Valid Product product) {
        return service.addProduct(product);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @Cacheable
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable int id) throws NotFoundException {
        Product  product= service.getProductById(id);
        //return service.getProductById(id);
        if (product==null){
            throw new NotFoundException("Product not found, id: "+id);
        } else {
            return product;
        }
    }

    @CacheEvict(key = "#product.getProductId()")
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody @Valid Product product,@PathVariable int id)  {
        System.out.println("UPDATING Product = " + product.getProductId());
        if (id!=product.getProductId()){
            throw new com.company.capstone2.productservice.exception.NotFoundException("path id should match with id: "+id);
        } else {
            service.updateProduct(product);
        }
    }

    @CacheEvict
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
    }

}