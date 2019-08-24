package com.company.capstone2.inventoryservice.controller;

import com.company.capstone2.inventoryservice.exception.NotFoundException;
import com.company.capstone2.inventoryservice.model.Inventory;
import com.company.capstone2.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/inventory")
public class InventoryServiceController {

    @Autowired
    InventoryService service;

    public InventoryServiceController(InventoryService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Inventory addInventory(@RequestBody Inventory inventory) {
        return service.addInventory(inventory);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Inventory> getAllInventory() {
        return service.getAllInventory();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Inventory getInventoryById(@PathVariable int id)  {
        Inventory inventory = service.getInventoryById(id);
        if (inventory == null) {
            throw new NotFoundException("Inventory does not exist, id: " + id);
        } else {
            return inventory;
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateInventory(@RequestBody Inventory inventory, @PathVariable int id) throws NotFoundException {
        if (inventory.getInventoryId() == id) {
            service.updateInventory(inventory);
        } else {
            throw new NotFoundException("path id should match customer id: " + inventory.getInventoryId());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteInventory(@PathVariable int id) {
        service.deleteInventory(id);
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteInventoryByProductId(@PathVariable int id) {
        service.deleteInventoryByProductId(id);
    }
}

