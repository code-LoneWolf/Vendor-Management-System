package com.gokul.vendor_management.dto;

import java.time.LocalDate;

public class SubscriptionVerificationResponse {

    private String customer;
    private String vendor;
    private String status;
    private LocalDate expiry;

    public SubscriptionVerificationResponse(String customer, String vendor, String status, LocalDate expiry) {
        this.customer = customer;
        this.vendor = vendor;
        this.status = status;
        this.expiry = expiry;
    }

    public String getCustomer() {
        return customer;
    }

    public String getVendor() {
        return vendor;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getExpiry() {
        return expiry;
    }
}