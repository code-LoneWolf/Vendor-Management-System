package com.gokul.vendor_management.repository;

import com.gokul.vendor_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    long countByVendor_VendorId(Long vendorId);
}