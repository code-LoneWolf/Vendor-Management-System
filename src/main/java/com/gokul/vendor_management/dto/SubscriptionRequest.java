package com.gokul.vendor_management.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private Long vendorId;
    private Long customerId;
    private Long agentId;
}