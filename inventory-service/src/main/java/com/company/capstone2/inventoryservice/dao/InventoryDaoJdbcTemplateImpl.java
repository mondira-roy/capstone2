package com.company.capstone2.inventoryservice.dao;

import com.company.capstone2.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class InventoryDaoJdbcTemplateImpl implements InventoryDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public InventoryDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Inventory addInventory(Inventory inventory) {
        String sql = "insert into inventory (" +
                "product_id," +
                "quantity" +
                ") values (?,?)";
        jdbcTemplate.update(
                sql,
                inventory.getProductId(),
                inventory.getQuantity());
        int id = jdbcTemplate.queryForObject(
                "select last_insert_id()",
                Integer.class
        );
        inventory.setInventoryId(id);
        return inventory;
    }

    // helper method
    private Inventory mapTo(ResultSet rs, int rowNum) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(rs.getInt("inventory_id"));
        inventory.setProductId(rs.getInt("product_id"));
        inventory.setQuantity(rs.getInt("quantity"));
        return inventory;
    }

    @Override
    public List<Inventory> getAllInventory() {
        String sql = "select * from inventory";
        return jdbcTemplate.query(
                sql,
                this::mapTo
        );
    }

    @Override
    public Inventory getInventoryById(int id) {
        String sql = "select * from inventory where inventory_id=?";
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
    public void updateInventory(Inventory inventory) {
        String sql = "update inventory set " +
                "product_id=?," +
                "quantity=?" +
                " where inventory_id=?";
        jdbcTemplate.update(
                sql,
                inventory.getProductId(),
                inventory.getQuantity(),
                inventory.getInventoryId());
    }

    @Override
    public void deleteInventory(int id) {
        String sql = "delete from inventory where inventory_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public void deleteInventoryByProductId(int id) {
        String sql = "delete from inventory where product_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

}
