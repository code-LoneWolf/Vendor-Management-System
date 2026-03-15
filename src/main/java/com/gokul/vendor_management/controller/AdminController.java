package com.gokul.vendor_management.controller;

import com.gokul.vendor_management.dto.AdminDashboardResponse;
import com.gokul.vendor_management.service.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public AdminDashboardResponse getDashboard() {
        return adminService.getAdminDashboard();
    }
}