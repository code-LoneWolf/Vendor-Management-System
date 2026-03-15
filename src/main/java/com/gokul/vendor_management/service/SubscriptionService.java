package com.gokul.vendor_management.service;

import com.gokul.vendor_management.dto.*;
import com.gokul.vendor_management.entity.Subscription;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionService {

    Subscription createSubscription(SubscriptionRequest request);

    SubscriptionVerificationResponse verifySubscription(Long id, String token);

    PaginatedResponse<Subscription> getAllSubscriptions(Pageable pageable);

    Subscription getSubscriptionById(Long id);

    Subscription updateSubscription(Long id, SubscriptionRequest request);

    void deleteSubscription(Long id);

    List<Subscription> getExpiredSubscriptions();

    List<Subscription> getExpiringSubscriptions(int days);

    Subscription transferVendor(Long subscriptionId, Long newVendorId);

    List<TransferHistoryResponse> getTransferHistory(Long subscriptionId);

    Subscription renewSubscription(Long id, RenewSubscriptionRequest request);

    byte[] generateSubscriptionQR(Long id);
}