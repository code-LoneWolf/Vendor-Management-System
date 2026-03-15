package com.gokul.vendor_management.repository;

import com.gokul.vendor_management.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByEndDateBefore(LocalDate date);

    List<Subscription> findByEndDateBetween(LocalDate start, LocalDate end);


    List<Subscription> findByEndDateBeforeAndStatus(LocalDate date, String status);

    long countByVendorVendorId(Long vendorId);

    long countByVendor_VendorIdAndStatus(Long vendorId, String status);

    long countByVendor_VendorIdAndEndDateBetween(
            Long vendorId,
            LocalDate start,
            LocalDate end
    );

    long countByStatus(String status);

    long countByEndDateBetween(LocalDate start, LocalDate end);
}