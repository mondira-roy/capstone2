package com.company.capstone2.invoiceservice.dao;

import com.company.capstone2.invoiceservice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceItemDaoJdbcTemplateImple implements InvoiceItemDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public InvoiceItemDaoJdbcTemplateImple(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public InvoiceItem addInvoiceItem(InvoiceItem invoiceItem) {
        String sql = "insert into invoice_item (" +
                "invoice_id," +
                "inventory_id," +
                "quantity," +
                "unit_price"+
                ") values (?,?,?,?)";
        jdbcTemplate.update(
                sql,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice()
        );
        int id = jdbcTemplate.queryForObject(
                "select last_insert_id()",
                Integer.class
        );
        invoiceItem.setInvoiceItemId(id);
        return invoiceItem;
    }

    // helper method
    private InvoiceItem mapTo(ResultSet rs, int rowNum) throws SQLException {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(rs.getInt("invoice_item_id"));
        invoiceItem.setInvoiceId(rs.getInt("invoice_id"));
        invoiceItem.setInventoryId(rs.getInt("inventory_id"));
        invoiceItem.setQuantity(rs.getInt("quantity"));
        invoiceItem.setUnitPrice(rs.getBigDecimal("unit_price"));
        return invoiceItem;
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItems() {
        String sql = "select * from invoice_item";
        return jdbcTemplate.query(
                sql,
                this::mapTo
        );
    }

    @Override
    public InvoiceItem getInvoiceItemById(int id) {
        String sql = "select * from invoice_item where invoice_item_id=?";
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
    public void updateInvoiceItem(InvoiceItem invoiceItem) {
        String sql = "update invoice_item set " +
                "invoice_id=?," +
                "inventory_id=?," +
                "quantity=?," +
                "unit_price=? "+
                "where invoice_item_id=?";
        jdbcTemplate.update(
                sql,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice(),
                invoiceItem.getInvoiceItemId()
        );
    }

    @Override
    public void deleteInvoiceItem(int id) {
        String sql = "delete from invoice_item where invoice_item_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }


    @Override
    public List<InvoiceItem> getInvoiceItemByInvoiceId(int id) {
        String sql = " select * from invoice_item where invoice_id=?";
        return jdbcTemplate.query(
                sql,
                this::mapTo,
                id
        );
    }

    @Override
    public void deleteInvoiceItemByInvoiceId(int id) {
        String sql = "delete from invoice_item where invoice_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }
    @Override
    public void deleteInvoiceItemByInventoryId(int id) {
        String sql = "delete from invoice_item where inventory_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }
}
