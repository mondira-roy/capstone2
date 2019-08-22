package com.company.capstone2.customerservice.dao;

import com.company.capstone2.customerservice.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CustomerDao {

    Customer addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(int id);

    void updateCustomer(Customer customer);

    void deleteCustomer(int id);
}
