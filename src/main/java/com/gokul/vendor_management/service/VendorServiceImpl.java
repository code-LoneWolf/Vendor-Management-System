package com.gokul.vendor_management.service;

import com.gokul.vendor_management.dto.VendorDashboardResponse;
import com.gokul.vendor_management.dto.VendorRequest;
import com.gokul.vendor_management.entity.Vendor;
import com.gokul.vendor_management.exception.ResourceNotFoundException;
import com.gokul.vendor_management.repository.AgentRepository;
import com.gokul.vendor_management.repository.CustomerRepository;
import com.gokul.vendor_management.repository.SubscriptionRepository;
import com.gokul.vendor_management.repository.VendorRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final SubscriptionRepository subscriptionRepository;

    public VendorServiceImpl(
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
    public Vendor createVendor(VendorRequest request) {

        Vendor vendor = new Vendor();
        vendor.setName(request.getName());
        vendor.setMobileNumber(request.getMobileNumber());
        vendor.setArea(request.getArea());

        return vendorRepository.save(vendor);
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor getVendorById(Long id) {

        return vendorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vendor not found with id: " + id));
    }

    @Override
    public Vendor updateVendor(Long id, Vendor vendor) {

        Vendor existingVendor = getVendorById(id);

        existingVendor.setName(vendor.getName());
        existingVendor.setMobileNumber(vendor.getMobileNumber());
        existingVendor.setArea(vendor.getArea());

        return vendorRepository.save(existingVendor);
    }

    @Override
    public void deleteVendor(Long id) {

        Vendor vendor = getVendorById(id);
        vendorRepository.delete(vendor);
    }


    @Override
    public VendorDashboardResponse getVendorDashboard(Long vendorId) {

        vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vendor not found"));

        long totalCustomers =
                customerRepository.countByVendor_VendorId(vendorId);

        long totalAgents =
                agentRepository.countByVendor_VendorId(vendorId);

        long activeSubscriptions =
                subscriptionRepository.countByVendor_VendorIdAndStatus(
                        vendorId, "ACTIVE");

        long expiredSubscriptions =
                subscriptionRepository.countByVendor_VendorIdAndStatus(
                        vendorId, "EXPIRED");

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        long expiringSoon =
                subscriptionRepository.countByVendor_VendorIdAndEndDateBetween(
                        vendorId, today, nextWeek);

        return new VendorDashboardResponse(
                vendorId,
                totalCustomers,
                totalAgents,
                activeSubscriptions,
                expiredSubscriptions,
                expiringSoon
        );
    }
}