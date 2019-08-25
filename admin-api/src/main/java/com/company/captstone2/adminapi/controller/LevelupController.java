package com.company.captstone2.adminapi.controller;

import com.company.captstone2.adminapi.exception.NotFoundException;
import com.company.captstone2.adminapi.model.Levelup;
import com.company.captstone2.adminapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RefreshScope
@RequestMapping("/levelup")
public class LevelupController {

    @Autowired
    AdminService service;

    public LevelupController(AdminService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Levelup addLevelup(@RequestBody Levelup levelup,Principal principal){
        return service.addLevelup(levelup);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Levelup> getAllLevelups(Principal principal) {
        return service.getAllLevelups();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Levelup getLevelupById(@PathVariable int id,Principal principal) {
        Levelup  levelup= service.getLevelupById(id);
        if (levelup==null){
            throw new NotFoundException("Levelup not found, id: "+id);
        } else {
            return levelup;
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateLevelup(@RequestBody Levelup levelup, @PathVariable int id,Principal principal) {
        if (levelup.getLevelupId()==id){
            service.updateLevelup(levelup,id);
        } else {
            throw new NotFoundException("path id should match levelup id: "+ levelup.getLevelupId());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteLevelup(@PathVariable int id, Principal principal) {
        service.deleteLevelup(id);
    }

//    @GetMapping("/customer/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public List<Levelup> getLevelupByCustomerId(@PathVariable int id) {
//        return service.getLevelUpByCustomerId(id);
//    }

}
