package com.company.capstone2.productservice.util.feign;

import com.company.capstone2.productservice.model.Invoice;
import com.company.capstone2.productservice.model.InvoiceItem;
import com.company.capstone2.productservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("invoice-service")
public interface InvoiceServiceClient {

    @RequestMapping(method= RequestMethod.GET, value = "/invoice/{id}")
    public  List<Product> getProductByInvoiceId();

    @RequestMapping(method= RequestMethod.GET, value = "/{id}")
    public Invoice getInvoiceById(int id);

    @RequestMapping(method= RequestMethod.GET, value = "/invoiceitem/{id}")
    public List<InvoiceItem> getInvoiceItemByInvoiceId(int id);
}





//@FeignClient("stores")
//public interface StoreClient {
//    @RequestMapping(method = RequestMethod.GET, value = "/stores")
//    List<Store> getStores();
//
//    @RequestMapping(method = RequestMethod.POST, value = "/stores/{storeId}", consumes = "application/json")
//    Store update(@PathVariable("storeId") Long storeId, Store store);
//}