package com.gokul.vendor_management.dto;

import com.gokul.vendor_management.model.SubscriptionPlan;

public class RenewSubscriptionRequest {

    private SubscriptionPlan plan;

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }
}