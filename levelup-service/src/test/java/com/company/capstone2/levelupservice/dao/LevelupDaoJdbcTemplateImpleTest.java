package com.company.capstone2.levelupservice.dao;

import com.company.capstone2.levelupservice.model.Levelup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LevelupDaoJdbcTemplateImpleTest {

    @Autowired
    LevelupDao dao;

    @Before
    public void setUp() throws Exception {
        // clear the data base
        List<Levelup> levelups = dao.getAllLevelups();
        levelups.stream().forEach(levelup -> dao.deleteLevelup(levelup.getLevelupId()));
    }

    @Test
    public void testAddLevelupGetAllLevelupsGetLevelupById() {
        Levelup levelup1 = new Levelup();
        levelup1.setCustomerId(1);
        levelup1.setPoint(10);
        levelup1.setMemberDate(LocalDate.of(2000,1,1));

        // test add
        levelup1 = dao.addLevelup(levelup1);
        // test getAll
        List<Levelup> levelups = dao.getAllLevelups();
        assertEquals(levelups.size(),1);
        // test get by Id
        Levelup levelup2 = dao.getLevelupById(levelup1.getLevelupId());
        assertEquals(levelup1,levelup2);
        levelup2 = dao.getLevelupById(-1);
    }

    @Test
    public void testUpdateLevelupDeleteLevelup() {
        Levelup levelup1 = new Levelup();
        levelup1.setCustomerId(1);
        levelup1.setPoint(10);
        levelup1.setMemberDate(LocalDate.of(2000,1,1));

        levelup1 = dao.addLevelup(levelup1);
        levelup1.setPoint(9000);
        dao.updateLevelup(levelup1);
        assertEquals(dao.getLevelupById(levelup1.getLevelupId()).getPoint(),9000);
    }

    @Test
    public void testGetLevelupByCustomerId(){
        Levelup levelup1 = new Levelup();
        levelup1.setCustomerId(1);
        levelup1.setPoint(10);
        levelup1.setMemberDate(LocalDate.of(2000,1,1));
List<Levelup> levelups = new ArrayList<>();
levelups.add(levelup1);

       levelup1= dao.addLevelup(levelup1);
        List<Levelup> levelup2= dao.getLevelupByCustomerId(1);
        assertEquals(levelup2,levelups);
        levelup2 = dao.getLevelupByCustomerId(2);
    }
}