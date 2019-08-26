package com.company.capstone2.levelupservice.service;

import com.company.capstone2.levelupservice.dao.LevelupDao;
import com.company.capstone2.levelupservice.model.Levelup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class LevelupService {

    @Autowired
    LevelupDao dao;

    public LevelupService(LevelupDao dao) {
        this.dao = dao;
    }

    @Transactional
    public Levelup addLevelup(Levelup levelup) {
        levelup = dao.addLevelup(levelup);
        return levelup;
    }

    public List<Levelup> getAllLevelups() {
        return dao.getAllLevelups();
    }

    public Levelup getLevelupById(int id) {
        return dao.getLevelupById(id);
    }

    @Transactional
    public void updateLevelup(Levelup levelup) {
        dao.updateLevelup(levelup);
    }

    public void deleteLevelup(int id) {
        dao.deleteLevelup(id);
    }

    public List<Levelup>  getLevelUpByCustomerId(int id){
       return  dao.getLevelupByCustomerId(id);
    }
}
