package com.gokul.vendor_management.service;

import com.gokul.vendor_management.dto.PaymentRequest;
import com.gokul.vendor_management.dto.PaymentResponse;
import com.gokul.vendor_management.entity.Payment;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    List<Payment> getPaymentsBySubscription(Long subscriptionId);

}