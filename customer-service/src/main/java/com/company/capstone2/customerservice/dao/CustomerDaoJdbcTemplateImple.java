package com.company.capstone2.customerservice.dao;

import com.company.capstone2.customerservice.model.Customer;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDaoJdbcTemplateImple implements CustomerDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public CustomerDaoJdbcTemplateImple(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        String sql = "insert into customer (" +
                "first_name," +
                "last_name," +
                "street," +
                "city," +
                "zip," +
                "email," +
                "phone" +
                ") values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                customer.getFistName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone()
        );
        int id = jdbcTemplate.queryForObject(
                "select last_insert_id()",
                Integer.class
        );
        customer.setCustomerId(id);
        return customer;
    }

    // helper method
    private Customer mapTo(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setFistName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setStreet(rs.getString("street"));
        customer.setCity(rs.getString("city"));
        customer.setZip(rs.getString("zip"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "select * from customer";
        return jdbcTemplate.query(
                sql,
                this::mapTo
        );
    }

    @Override
    public Customer getCustomerById(int id) {
        String sql = "select * from customer where customer_id=?";
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
    public void updateCustomer(Customer customer) {
        String sql = "update customer set " +
                "first_name=?," +
                "last_name=?," +
                "street=?," +
                "city=?," +
                "zip=?," +
                "email=?," +
                "phone=?" +
                "where customer_id=?";
        jdbcTemplate.update(
                sql,
                customer.getFistName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCustomerId()
        );
    }

    @Override
    public void deleteCustomer(int id) {
        String sql = "delete from customer where customer_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }
}
