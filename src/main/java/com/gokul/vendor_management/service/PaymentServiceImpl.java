package com.gokul.vendor_management.service;

import com.gokul.vendor_management.dto.PaymentRequest;
import com.gokul.vendor_management.dto.PaymentResponse;
import com.gokul.vendor_management.entity.Payment;
import com.gokul.vendor_management.entity.Subscription;
import com.gokul.vendor_management.exception.ResourceNotFoundException;
import com.gokul.vendor_management.repository.PaymentRepository;
import com.gokul.vendor_management.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              SubscriptionRepository subscriptionRepository) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        Subscription subscription =
                subscriptionRepository.findById(request.getSubscriptionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Subscription not found"));

        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("PAID");
        payment.setSubscription(subscription);

        Payment saved = paymentRepository.save(payment);

        return new PaymentResponse(
                saved.getPaymentId(),
                saved.getAmount(),
                saved.getPaymentDate(),
                saved.getPaymentMethod(),
                saved.getStatus()
        );
    }

    @Override
    public List<Payment> getPaymentsBySubscription(Long subscriptionId) {

        return paymentRepository
                .findBySubscriptionSubscriptionId(subscriptionId);
    }
}