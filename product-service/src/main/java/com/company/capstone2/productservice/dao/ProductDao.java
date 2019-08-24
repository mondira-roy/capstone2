package com.company.capstone2.productservice.dao;

import com.company.capstone2.productservice.model.Product;

import java.util.List;

public interface ProductDao {

    //Method to get a product by the id
    Product getProductById(int id);

    // method to get all the Products
    List<Product> getAllProducts();

    //method to add  a new Product to inventory
    Product addProduct(Product product);

    //Update Product
    void updateProduct(Product product);

    void deleteProduct(int id);

}
