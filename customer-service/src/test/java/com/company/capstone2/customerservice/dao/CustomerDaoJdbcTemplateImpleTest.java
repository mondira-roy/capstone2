package com.company.capstone2.customerservice.dao;

import com.company.capstone2.customerservice.model.Customer;
//import com.netflix.discovery.converters.Auto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerDaoJdbcTemplateImpleTest {

    @Autowired
    CustomerDao dao;

    @Before
    public void setUp() throws Exception {
        // clear the data base
        List<Customer> customers = dao.getAllCustomers();
        customers.stream().forEach(customer -> dao.deleteCustomer(customer.getCustomerId()));
    }

    @Test
    public void testAddCustomerGetAllCustomersGetCustomerById() {
        Customer customer1 = new Customer();
        customer1.setFistName("Simon");
        customer1.setLastName("Kwok");
        customer1.setStreet("Sesame Street");
        customer1.setCity("Forbidden City");
        customer1.setZip("00000");
        customer1.setEmail("test@test.com");
        customer1.setPhone("9009009009");
        // test add
        customer1 = dao.addCustomer(customer1);
        // test getAll
        List<Customer> customers = dao.getAllCustomers();
        assertEquals(customers.size(),1);
        // test get by Id
        Customer customer2 = dao.getCustomerById(customer1.getCustomerId());
        assertEquals(customer1,customer2);
    }

    @Test
    public void testUpdateCustomerDeleteCustomer() {
        Customer customer1 = new Customer();
        customer1.setFistName("Simon");
        customer1.setLastName("Kwok");
        customer1.setStreet("Sesame Street");
        customer1.setCity("Forbidden City");
        customer1.setZip("00000");
        customer1.setEmail("test@test.com");
        customer1.setPhone("9009009009");
        // test add
        customer1 = dao.addCustomer(customer1);
        customer1.setCity("Open City");
        dao.updateCustomer(customer1);
        assertEquals(dao.getCustomerById(customer1.getCustomerId()).getCity(),"Open City");
    }

}