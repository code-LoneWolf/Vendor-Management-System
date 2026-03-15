package com.gokul.vendor_management.controller;

import com.gokul.vendor_management.dto.PaymentRequest;
import com.gokul.vendor_management.dto.PaymentResponse;
import com.gokul.vendor_management.entity.Payment;
import com.gokul.vendor_management.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentResponse createPayment(@RequestBody PaymentRequest request) {
        return paymentService.createPayment(request);
    }

    @GetMapping("/subscription/{id}")
    public List<Payment> getPayments(@PathVariable Long id) {
        return paymentService.getPaymentsBySubscription(id);
    }
}