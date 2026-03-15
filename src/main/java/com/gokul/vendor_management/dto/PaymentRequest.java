package com.gokul.vendor_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    private Long subscriptionId;
    private Double amount;
    private String paymentMethod;

}