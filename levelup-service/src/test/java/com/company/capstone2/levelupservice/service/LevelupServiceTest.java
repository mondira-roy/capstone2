package com.company.capstone2.levelupservice.service;

import com.company.capstone2.levelupservice.dao.LevelupDao;
import com.company.capstone2.levelupservice.model.Levelup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LevelupServiceTest {

    @InjectMocks
    LevelupService service;

    @Mock
    LevelupDao dao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addLevelup() {

        Levelup levelup1 = new Levelup();
        when(dao.addLevelup(levelup1)).thenReturn(levelup1);
        levelup1 = service.addLevelup(levelup1);
        verify(dao,times(1)).addLevelup(levelup1);
    }

    @Test
    public void getAllLevelups() {
        List<Levelup> levelups = service.getAllLevelups();
        verify(dao,times(1)).getAllLevelups();
    }

    @Test
    public void getLevelupById() {
        Levelup levelup1 = new Levelup();
        levelup1.setLevelupId(1);
        levelup1 = service.getLevelupById(1);
        verify(dao,times(1)).getLevelupById(1);
    }

    @Test
    public void updateLevelup() {
        Levelup levelup1 = new Levelup();
        service.updateLevelup(levelup1);
        verify(dao,times(1)).updateLevelup(levelup1);
    }

    @Test
    public void deleteLevelup() {
        service.deleteLevelup(1);
        verify(dao,times(1)).deleteLevelup(1);
    }

    @Test
    public void getLevelUpByCustomerId() {
       List<Levelup> levelups = service.getLevelUpByCustomerId(1);
        verify(dao,times(1)).getLevelupByCustomerId(1);
    }
}