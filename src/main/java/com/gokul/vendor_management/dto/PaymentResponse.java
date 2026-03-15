package com.gokul.vendor_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    private Long paymentId;
    private Double amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String status;

}