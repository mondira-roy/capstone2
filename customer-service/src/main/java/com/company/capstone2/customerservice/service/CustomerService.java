package com.company.capstone2.customerservice.service;


import com.company.capstone2.customerservice.dao.CustomerDao;
import com.company.capstone2.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CustomerService {
    @Autowired
    CustomerDao dao;

    public CustomerService(CustomerDao dao) {
        this.dao = dao;
    }

    @Transactional
    public Customer addCustomer(Customer customer) {
        customer = dao.addCustomer(customer);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return dao.getCustomerById(id);
    }

    @Transactional
    public void updateCustomer(Customer customer) {
        dao.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {
        dao.deleteCustomer(id);
    }

}
