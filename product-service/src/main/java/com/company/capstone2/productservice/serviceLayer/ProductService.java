package com.company.capstone2.productservice.serviceLayer;

import com.company.capstone2.productservice.dao.ProductDao;
import com.company.capstone2.productservice.model.Inventory;
import com.company.capstone2.productservice.model.Invoice;
import com.company.capstone2.productservice.model.InvoiceItem;
import com.company.capstone2.productservice.model.Product;
import com.company.capstone2.productservice.util.feign.InventoryServiceClient;
import com.company.capstone2.productservice.util.feign.InvoiceServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductService {
    ProductDao productDao;


    @Autowired
    InventoryServiceClient inventoryServiceClient;
    InvoiceServiceClient invoiceServiceClient;

    public ProductService(ProductDao productDao,
               InventoryServiceClient inventoryServiceClient, InvoiceServiceClient invoiceServiceClient  ) {//, InventoryDao inventoryDao, InvoiceDao invoiceDao ) {
        this.productDao = productDao;
        this.invoiceServiceClient= invoiceServiceClient;
        this.inventoryServiceClient= inventoryServiceClient;

    }

    @Transactional    // must commit entire code block or nothing
    public Product addProduct(Product product) {
return productDao.addProduct(product);

    }

    public Product getProductById(int id) {
        System.out.println("GETTING Product By ID = " + id);
        return productDao.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Transactional
    public void updateProduct(Product product) {
        // update the product DB
        productDao.updateProduct(product);

    }

        public void deleteProduct ( int id){
            productDao.deleteProduct(id);
            //client.deleteProductByInvoiceId(id);   //This takes care of referential integrity
        }

        public  List<Product> getProductsInInventory() {
        return inventoryServiceClient.getProductsInInventory();
        }

    public List<Product> getProductByInvoiceId(int id) {
        Invoice invoice = invoiceServiceClient.getInvoiceById(id);
        int invoiceId = invoice.getInvoiceId();
        List<InvoiceItem> items = invoiceServiceClient.getInvoiceItemByInvoiceId(invoiceId);
        List<Product> products = new ArrayList<>();
        items.stream().forEach(item->{
            int tempInventoryId = item.getInventoryId();
            int tempProductId = inventoryServiceClient.getProductIdByInventoryId(tempInventoryId);
            Product tempProduct = getProductById(tempProductId);
            products.add(tempProduct);
        });
        return products; }

    }
