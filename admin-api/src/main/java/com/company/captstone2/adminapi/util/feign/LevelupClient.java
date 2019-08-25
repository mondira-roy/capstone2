package com.company.captstone2.adminapi.util.feign;

import com.company.captstone2.adminapi.model.Levelup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="levelup-service")
public interface LevelupClient {

    @RequestMapping(value = "/levelup",method = RequestMethod.POST)
    public Levelup addLevelup(@RequestBody Levelup levelup);

    @RequestMapping(value = "/levelup",method = RequestMethod.GET)
    public List<Levelup> getAllLevelups();

    @RequestMapping(value = "/levelup/{id}",method = RequestMethod.GET)
    public Levelup getLevelupById(@PathVariable int id);

    @RequestMapping(value = "/levelup/{id}",method = RequestMethod.PUT)
    public void updateLevelup(@RequestBody Levelup levelup, @PathVariable int id);

    @RequestMapping(value = "/levelup/{id}",method = RequestMethod.DELETE)
    public void deleteLevelup(@PathVariable int id);

}
