package com.company.capstone2.retailapi.util.feign;

import com.company.capstone2.retailapi.model.Levelup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="levelup-service")
public interface LevelupClient {
    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public List<Levelup> getLevelUpByCustomerId(@PathVariable int id);
}
