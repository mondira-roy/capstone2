package com.company.capstone2.productservice.dao;

import com.company.capstone2.productservice.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoJdbcTemplateImplTest {
    @Autowired
    ProductDao productDao;

    @Before
    public void setUp() throws Exception {
        List<Product> products = productDao.getAllProducts();
        for (Product pro : products) {
            productDao.deleteProduct(pro.getProductId());
        }
    }
    @Test
        public void addGetDeleteProduct() {
            Product product = new Product();
            product.setProductName("Model1");
            product.setProductDescription("Manufacturer1");
            product.setListPrice(BigDecimal.valueOf(125.99));
            product.setUnitCost(BigDecimal.valueOf(25.99));


            product = productDao.addProduct(product);

            Product product1 = productDao.getProductById(product.getProductId());
            assertEquals(product1, product);

            productDao.deleteProduct(product.getProductId());
            product1 = productDao.getProductById(product.getProductId());
            //assertNull(console1);
        }

    @Test
    public void getAllProducts() {
        Product product = new Product();
        product.setProductName("Model1");
        product.setProductDescription("Manufacturer1");
        product.setListPrice(BigDecimal.valueOf(125.99));
        product.setUnitCost(BigDecimal.valueOf(25.99));
        product = productDao.addProduct(product);

        product = new Product();
        product.setProductName("Model2");
        product.setProductDescription("Manufacturer2");
        product.setListPrice(BigDecimal.valueOf(100.99));
        product.setUnitCost(BigDecimal.valueOf(50.99));
        product = productDao.addProduct(product);

        List<Product> pList = productDao.getAllProducts();
        assertEquals(2, pList.size());
    }


    @Test
    public void updateProduct() {
        Product product = new Product();
        product.setProductName("Model1");
        product.setProductDescription("Manufacturer1");
        product.setListPrice(BigDecimal.valueOf(125.99));
        product.setUnitCost(BigDecimal.valueOf(25.99));

        product = productDao.addProduct(product);
        product.setProductName("Model1");
        product.setProductDescription("Manufacturer2");
        product.setListPrice(BigDecimal.valueOf(250.99));
        product.setUnitCost(BigDecimal.valueOf(20.99));

        productDao.updateProduct(product);

        Product product1  = productDao.getProductById(product.getProductId());
        assertEquals(product1,product);

    }


}