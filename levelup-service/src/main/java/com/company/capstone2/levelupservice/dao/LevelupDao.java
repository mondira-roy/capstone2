package com.company.capstone2.levelupservice.dao;

import com.company.capstone2.levelupservice.model.Levelup;

import java.util.List;

public interface LevelupDao {

    Levelup addLevelup(Levelup customer);

    List<Levelup> getAllLevelups();

    Levelup getLevelupById(int id);

    void updateLevelup(Levelup customer);

    void deleteLevelup(int id);

    List<Levelup> getLevelupByCustomerId(int id);

}
