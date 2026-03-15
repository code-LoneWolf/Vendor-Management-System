package com.gokul.vendor_management.service;

import com.gokul.vendor_management.entity.Customer;
import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
}