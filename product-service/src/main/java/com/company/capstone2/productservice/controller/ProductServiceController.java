package com.company.capstone2.productservice.controller;

import com.company.capstone2.productservice.dao.ProductDao;
import com.company.capstone2.productservice.model.Product;
import com.company.capstone2.productservice.serviceLayer.ProductService;
import com.company.capstone2.productservice.util.feign.InvoiceServiceClient;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RefreshScope

//@CacheConfig(cacheNames = "products")
public class ProductServiceController {
    @Autowired
    ProductService service;
    @Autowired
    InvoiceServiceClient client;


    //@CachePut(key = "#result.getProductId()")
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        System.out.println("CREATING Product");
        return service.addProduct(product);
    }

    //@Cacheable
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
    //@Cacheable
    @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductByInvoiceId(@PathVariable int id) throws NotFoundException {
      return service.getProductByInvoiceId(id);
    }


    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        System.out.println("GETTING ALL Product");
        return service.getAllProducts();
    }
    //@Cacheable
    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsInInventory() {
        System.out.println("GETTING All Products in Inventory =" );
        return service.getProductsInInventory();
    }

    // @CacheEvict(key = "#product.getProductId()")
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody Product product) {
        System.out.println("UPDATING Product = " + product.getProductId());
        service.updateProduct(product);
    }

    //@CacheEvict
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable int id) {
        System.out.println("DELETING Product ID = " + id);
        service.deleteProduct(id);
        //client.deleteCommentByPostId(id);   //This takes care of referential integrity
    }
}