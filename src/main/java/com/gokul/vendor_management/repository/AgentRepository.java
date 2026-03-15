package com.gokul.vendor_management.repository;

import com.gokul.vendor_management.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    long countByVendor_VendorId(Long vendorId);
}