package com.gokul.vendor_management.repository;

import java.util.List;
import com.gokul.vendor_management.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {

    List<TransferHistory> findBySubscriptionSubscriptionIdOrderByTransferDateDesc(Long subscriptionId);
}