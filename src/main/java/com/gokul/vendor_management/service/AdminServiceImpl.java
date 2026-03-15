package com.gokul.vendor_management.service;

import com.gokul.vendor_management.dto.AdminDashboardResponse;
import com.gokul.vendor_management.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminServiceImpl implements AdminService {

    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final SubscriptionRepository subscriptionRepository;

    public AdminServiceImpl(
            VendorRepository vendorRepository,
            CustomerRepository customerRepository,
            AgentRepository agentRepository,
            SubscriptionRepository subscriptionRepository) {

        this.vendorRepository = vendorRepository;
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public AdminDashboardResponse getAdminDashboard() {

        long vendors = vendorRepository.count();
        long customers = customerRepository.count();
        long agents = agentRepository.count();

        long activeSubscriptions =
                subscriptionRepository.countByStatus("ACTIVE");

        long expiredSubscriptions =
                subscriptionRepository.countByStatus("EXPIRED");

        LocalDate today = LocalDate.now();

        long expiringSoon =
                subscriptionRepository.countByEndDateBetween(
                        today,
                        today.plusDays(7)
                );

        return new AdminDashboardResponse(
                vendors,
                customers,
                agents,
                activeSubscriptions,
                expiredSubscriptions,
                expiringSoon
        );
    }
}