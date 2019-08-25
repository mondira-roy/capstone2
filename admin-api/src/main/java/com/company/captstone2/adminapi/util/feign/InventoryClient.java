package com.company.captstone2.adminapi.util.feign;

import com.company.captstone2.adminapi.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="inventory-service")
public interface InventoryClient {

    @RequestMapping(value = "/inventory",method = RequestMethod.POST)
    public Inventory addInventory(@RequestBody Inventory inventory);

    @RequestMapping(value = "/inventory",method = RequestMethod.GET)
    public List<Inventory> getAllInventory();

    @RequestMapping(value = "/inventory/{id}",method = RequestMethod.GET)
    public Inventory getInventoryById(@PathVariable int id);

    @RequestMapping(value = "/inventory/{id}",method = RequestMethod.PUT)
    public void updateInventory(@RequestBody Inventory inventory, @PathVariable int id);

    @RequestMapping(value = "/inventory/{id}",method = RequestMethod.DELETE)
    public void deleteInventory(@PathVariable int id);

}
