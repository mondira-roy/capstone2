package com.company.capstone2.levelupconsumer.util.feign;

import com.company.capstone2.levelupconsumer.model.Levelup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="levelup-service")
public interface LevelupClient {
    @RequestMapping(value = "/levelup", method = RequestMethod.POST)
    public Levelup addLevelup(@RequestBody Levelup levelup);
}
