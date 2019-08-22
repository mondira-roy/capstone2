package com.company.capstone2.customerservice.service;

import com.company.capstone2.customerservice.dao.CustomerDao;
import com.company.capstone2.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {

    @Mock
    CustomerDao dao;

    @InjectMocks
    CustomerService service;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addCustomer() {
        Customer customer1 = new Customer();
        when(dao.addCustomer(customer1)).thenReturn(customer1);
        customer1 = service.addCustomer(customer1);
        verify(dao, times(1)).addCustomer(customer1);
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        when(dao.getAllCustomers()).thenReturn(customers);
        List<Customer> customers1 = service.getAllCustomers();
        assertEquals(customers1.size(),0);
    }

    @Test
    public void getCustomerById() {
        Customer customer1 = new Customer();
        when(dao.getCustomerById(1)).thenReturn(customer1);
        customer1 = service.getCustomerById(1);
        verify(dao, times(1)).getCustomerById(1);
    }

    @Test
    public void updateCustomer() {
        Customer customer1 = new Customer();
        service.updateCustomer(customer1);
        verify(dao,times(1)).updateCustomer(customer1);
    }

    @Test
    public void deleteCustomer() {
        service.deleteCustomer(1);
        verify(dao,times(1)).deleteCustomer(1);
    }
}