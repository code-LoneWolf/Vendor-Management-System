package com.gokul.vendor_management.controller;

import com.gokul.vendor_management.dto.*;
import com.gokul.vendor_management.entity.Subscription;
import com.gokul.vendor_management.entity.TransferHistory;
import com.gokul.vendor_management.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public Subscription createSubscription(@Valid @RequestBody SubscriptionRequest request) {
        return subscriptionService.createSubscription(request);
    }

    @PutMapping("/{id}/renew")
    public ResponseEntity<Subscription> renewSubscription(
            @PathVariable Long id,
            @RequestBody RenewSubscriptionRequest request) {

        Subscription renewedSubscription =
                subscriptionService.renewSubscription(id, request);

        return ResponseEntity.ok(renewedSubscription);
    }

    @GetMapping
    public PaginatedResponse<Subscription> getAllSubscriptions(Pageable pageable) {
        return subscriptionService.getAllSubscriptions(pageable);
    }

    @GetMapping("/{id}")
    public Subscription getSubscriptionById(@PathVariable Long id) {
        return subscriptionService.getSubscriptionById(id);
    }

    @GetMapping("/expired")
    public List<Subscription> getExpiredSubscriptions() {
        return subscriptionService.getExpiredSubscriptions();
    }

    @GetMapping("/expiring")
    public List<Subscription> getExpiringSubscriptions(@RequestParam int days) {
        return subscriptionService.getExpiringSubscriptions(days);
    }

    @GetMapping("/{id}/history")
    public List<TransferHistoryResponse> getTransferHistory(@PathVariable Long id) {
        return subscriptionService.getTransferHistory(id);
    }

    @GetMapping("/verify/{id}")
    public SubscriptionVerificationResponse verifySubscription(
            @PathVariable Long id,
            @RequestParam String token) {

        return subscriptionService.verifySubscription(id, token);
    }

    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> getSubscriptionQR(@PathVariable Long id) {

        byte[] qrImage = subscriptionService.generateSubscriptionQR(id);

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(qrImage);
    }

    @PutMapping("/{id}")
    public Subscription updateSubscription(@PathVariable Long id,
                                           @Valid @RequestBody SubscriptionRequest request) {
        return subscriptionService.updateSubscription(id, request);
    }

    @PutMapping("/{id}/transfer")
    public Subscription transferVendor(
            @PathVariable Long id,
            @RequestParam Long newVendorId) {

        return subscriptionService.transferVendor(id, newVendorId);
    }

    @DeleteMapping("/{id}")
    public String deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return "Subscription deleted successfully with id: " + id;
    }
}