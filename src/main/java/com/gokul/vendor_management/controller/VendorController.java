package com.gokul.vendor_management.controller;

import com.gokul.vendor_management.dto.VendorDashboardResponse;
import com.gokul.vendor_management.dto.VendorRequest;
import jakarta.validation.Valid;
import com.gokul.vendor_management.entity.Vendor;
import com.gokul.vendor_management.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    public ResponseEntity<Vendor> createVendor(
            @Valid @RequestBody VendorRequest request) {

        Vendor vendor = vendorService.createVendor(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(vendor);
    }

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<VendorDashboardResponse> getVendorDashboard(
            @PathVariable Long id) {

        VendorDashboardResponse dashboard =
                vendorService.getVendorDashboard(id);

        return ResponseEntity.ok(dashboard);
    }

    @PutMapping("/{id}")
    public Vendor updateVendor(@PathVariable Long id, @Valid @RequestBody Vendor vendor) {
        return vendorService.updateVendor(id, vendor);
    }

    @DeleteMapping("/{id}")
    public String deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return "Vendor deleted successfully with id: " + id;
    }
}