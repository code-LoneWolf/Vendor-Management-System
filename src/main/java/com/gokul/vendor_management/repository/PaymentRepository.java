package com.gokul.vendor_management.repository;

import com.gokul.vendor_management.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findBySubscriptionSubscriptionId(Long subscriptionId);

}