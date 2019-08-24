package com.company.capstone2.levelupservice.dao;

import com.company.capstone2.levelupservice.model.Levelup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LevelupDaoJdbcTemplateImple implements LevelupDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public LevelupDaoJdbcTemplateImple(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Levelup addLevelup(Levelup levelup) {
        String sql = "insert into level_up (" +
                "customer_id," +
                "points," +
                "member_date" +
                ") values (?,?,?)";
        jdbcTemplate.update(
                sql,
                levelup.getCustomerId(),
                levelup.getPoint(),
                levelup.getMemberDate()
        );
        int id = jdbcTemplate.queryForObject(
                "select last_insert_id()",
                Integer.class
        );
        levelup.setLevelupId(id);
        return levelup;
    }

    // helper method
    private Levelup mapTo(ResultSet rs, int rowNum) throws SQLException {
        Levelup levelup = new Levelup();
        levelup.setLevelupId(rs.getInt("level_up_id"));
        levelup.setCustomerId(rs.getInt("customer_id"));
        levelup.setPoint(rs.getInt("points"));
        levelup.setMemberDate(rs.getDate("member_date").toLocalDate());
        return levelup;
    }

    @Override
    public List<Levelup> getAllLevelups() {
        String sql = "select * from level_up";
        return jdbcTemplate.query(
                sql,
                this::mapTo
        );
    }

    @Override
    public Levelup getLevelupById(int id) {
        String sql = "select * from level_up where level_up_id=?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    this::mapTo,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateLevelup(Levelup levelup) {
        String sql = "update level_up set " +
                "customer_id=?," +
                "points=?," +
                "member_date=?" +
                "where level_up_id=?";
        jdbcTemplate.update(
                sql,
                levelup.getCustomerId(),
                levelup.getPoint(),
                levelup.getMemberDate(),
                levelup.getLevelupId()
        );
    }

    @Override
    public void deleteLevelup(int id) {
        String sql = "delete from level_up where level_up_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public List<Levelup> getLevelupByCustomerId(int id) {
        String sql = "select * from level_up where customer_id=?";

            return jdbcTemplate.query(
                    sql,
                    this::mapTo,
                    id);

    }
}
