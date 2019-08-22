package com.company.capstone2.invoiceservice.dao;

import com.company.capstone2.invoiceservice.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImple implements InvoiceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public InvoiceDaoJdbcTemplateImple(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    private int invoiceId;
//    private int customerId;
//    private LocalDate purchaseDate;

    @Override
    public Invoice addInvoice(Invoice invoice) {
        String sql = "insert into invoice (" +
                "customer_id," +
                "purchase_date" +
                ") values (?,?)";
        jdbcTemplate.update(
                sql,
                invoice.getCustomerId(),
                invoice.getPurchaseDate()
        );
        int id = jdbcTemplate.queryForObject(
                "select last_insert_id()",
                Integer.class
        );
        invoice.setInvoiceId(id);
        return invoice;
    }

    // helper method
    private Invoice mapTo(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setCustomerId(rs.getInt("customer_id"));
        invoice.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());
        return invoice;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        String sql = "select * from invoice";
        return jdbcTemplate.query(
                sql,
                this::mapTo
        );
    }

    @Override
    public Invoice getInvoiceById(int id) {
        String sql = "select * from invoice where invoice_id=?";
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
    public void updateInvoice(Invoice invoice) {
        String sql = "update invoice set " +
                "customer_id=?," +
                "purchase_date=?" +
                "where invoice_id=?";
        jdbcTemplate.update(
                sql,
                invoice.getCustomerId(),
                invoice.getPurchaseDate(),
                invoice.getInvoiceId()
        );
    }

    @Override
    public void deleteInvoice(int id) {
        String sql = "delete from invoice where invoice_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public List<Invoice> getInvoiceByCustomerId(int id) {
        String sql = "select * from invoice where customer_id=?";
            return jdbcTemplate.query(
                    sql,
                    this::mapTo,
                    id
            );
    }
}
