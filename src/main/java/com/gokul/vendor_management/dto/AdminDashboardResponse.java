package com.gokul.vendor_management.dto;

public class AdminDashboardResponse {

    private long totalVendors;
    private long totalCustomers;
    private long totalAgents;
    private long activeSubscriptions;
    private long expiredSubscriptions;
    private long expiringSoon;

    public AdminDashboardResponse(long totalVendors,
                                  long totalCustomers,
                                  long totalAgents,
                                  long activeSubscriptions,
                                  long expiredSubscriptions,
                                  long expiringSoon) {

        this.totalVendors = totalVendors;
        this.totalCustomers = totalCustomers;
        this.totalAgents = totalAgents;
        this.activeSubscriptions = activeSubscriptions;
        this.expiredSubscriptions = expiredSubscriptions;
        this.expiringSoon = expiringSoon;
    }

    public long getTotalVendors() { return totalVendors; }
    public long getTotalCustomers() { return totalCustomers; }
    public long getTotalAgents() { return totalAgents; }
    public long getActiveSubscriptions() { return activeSubscriptions; }
    public long getExpiredSubscriptions() { return expiredSubscriptions; }
    public long getExpiringSoon() { return expiringSoon; }
}