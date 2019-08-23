package com.company.capstone2.levelupservice.controller;


import com.company.capstone2.levelupservice.exception.NotFoundException;
import com.company.capstone2.levelupservice.model.Levelup;
import com.company.capstone2.levelupservice.service.LevelupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/levelup")
public class LevelupController {

    @Autowired
    LevelupService service;

    public LevelupController(LevelupService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Levelup addLevelup(@RequestBody Levelup customer){
        return service.addLevelup(customer);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Levelup> getAllLevelups() {
        return service.getAllLevelups();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Levelup getLevelupById(@PathVariable int id) {
        Levelup  customer= service.getLevelupById(id);
        if (customer==null){
            throw new NotFoundException("Levelup not found, id: "+id);
        } else {
            return customer;
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateLevelup(@RequestBody Levelup customer, @PathVariable int id) {
        if (customer.getLevelupId()==id){
            service.updateLevelup(customer);
        } else {
            throw new NotFoundException("path id should match customer id: "+ customer.getLevelupId());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteLevelup(@PathVariable int id) {
        service.deleteLevelup(id);
    }

    @GetMapping("/customer/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Levelup getLevelupByCustomerId(@PathVariable int id) {
        return service.getLevelUpByCustomerId(id);
    }

}
