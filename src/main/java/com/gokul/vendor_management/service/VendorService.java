package com.gokul.vendor_management.service;

import com.gokul.vendor_management.dto.VendorDashboardResponse;
import com.gokul.vendor_management.dto.VendorRequest;
import com.gokul.vendor_management.entity.Vendor;

import java.util.List;

public interface VendorService {

    Vendor createVendor(VendorRequest request);

    List<Vendor> getAllVendors();

    Vendor getVendorById(Long id);

    Vendor updateVendor(Long id, Vendor vendor);

    void deleteVendor(Long id);

    VendorDashboardResponse getVendorDashboard(Long vendorId);
}