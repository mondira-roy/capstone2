package com.company.captstone2.adminapi.controller;

import com.company.captstone2.adminapi.exception.NotFoundException;
import com.company.captstone2.adminapi.model.Product;
import com.company.captstone2.adminapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RefreshScope
public class ProductController {
    @Autowired
    AdminService service;

    public ProductController(AdminService service) {
        this.service = service;
    }

    //@CachePut(key = "#result.getProductId()")
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product, Principal principal) {
        return service.addProduct(product);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(Principal principal) {
        return service.getAllProducts();
    }

    //@Cacheable
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable int id,Principal principal) throws NotFoundException {
        Product  product= service.getProductById(id);
        //return service.getProductById(id);
        if (product==null){
            throw new NotFoundException("Product not found, id: "+id);
        } else {
            return product;
        }
    }

    // @CacheEvict(key = "#product.getProductId()")
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody Product product,@PathVariable int id,Principal principal)  {
        System.out.println("UPDATING Product = " + product.getProductId());
        if (id!=product.getProductId()){
            throw new NotFoundException("path id should match with id: "+id);
        } else {
            service.updateProduct(product,id);
        }
    }

    //@CacheEvict
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable int id,Principal principal) {
        service.deleteProduct(id);
    }
}
