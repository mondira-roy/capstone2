package com.company.capstone2.inventoryservice.util.feign;

import com.company.capstone2.inventoryservice.model.Inventory;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("invoice-service")
public interface InvoiceServiceClient {

    @RequestMapping(method= RequestMethod.DELETE, value = "/invoices/invoiceItem/inventory/{id}")
    public void deleteInvoiceItemByInventoryId(@PathVariable int id);
}
