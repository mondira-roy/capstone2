package com.company.captstone2.adminapi.controller;

import com.company.captstone2.adminapi.exception.NotFoundException;
import com.company.captstone2.adminapi.model.Inventory;
import com.company.captstone2.adminapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RefreshScope
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    AdminService service;

    public InventoryController(AdminService service) {
        this.service = service;
    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Inventory addInventory(@RequestBody Inventory inventory, Principal principal) {
        return service.addInventory(inventory);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Inventory> getAllInventory(Principal principal) {
        return service.getAllInventory();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Inventory getInventoryById(@PathVariable int id,Principal principal)  {
        Inventory inventory = service.getInventoryById(id);
        if (inventory == null) {
            throw new NotFoundException("Inventory does not exist, id: " + id);
        } else {
            return inventory;
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateInventory(@RequestBody Inventory inventory, @PathVariable int id,Principal principal) throws NotFoundException {
        if (inventory.getInventoryId() == id) {
            service.updateInventory(inventory,id);
        } else {
            throw new NotFoundException("path id should match customer id: " + inventory.getInventoryId());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteInventory(@PathVariable int id,Principal principal) {
        service.deleteInventory(id);
    }

//    @DeleteMapping("/product/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public void deleteInventoryByProductId(@PathVariable int id) {
//        service.deleteInventoryByProductId(id);
//    }
}
