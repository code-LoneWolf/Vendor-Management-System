package com.gokul.vendor_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendorDashboardResponse {

    private Long vendorId;

    private long totalCustomers;

    private long totalAgents;

    private long activeSubscriptions;

    private long expiredSubscriptions;

    private long expiringSoon;
}