package com.company.capstone2.productservice.serviceLayer;

import com.company.capstone2.productservice.dao.ProductDao;
import com.company.capstone2.productservice.model.Product;
import com.company.capstone2.productservice.util.feign.InventoryServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductService {
    ProductDao productDao;


    @Autowired
    InventoryServiceClient inventoryServiceClient;

    public ProductService(ProductDao productDao,
                          InventoryServiceClient inventoryServiceClient) {
        this.productDao = productDao;
        this.inventoryServiceClient = inventoryServiceClient;

    }

    @Transactional    // must commit entire code block or nothing
    public Product addProduct(Product product) {
        return productDao.addProduct(product);

    }

    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Transactional
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Transactional
    public void deleteProduct(int id) {
        inventoryServiceClient.deleteInventoryByProductId(id);
        productDao.deleteProduct(id);
    }


}
