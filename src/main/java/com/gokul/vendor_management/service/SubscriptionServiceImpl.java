package com.gokul.vendor_management.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gokul.vendor_management.dto.*;
import com.gokul.vendor_management.entity.*;
import com.gokul.vendor_management.exception.ResourceNotFoundException;
import com.gokul.vendor_management.repository.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final TransferHistoryRepository transferHistoryRepository;
    private final QRCodeService qrCodeService;

    public SubscriptionServiceImpl(
            SubscriptionRepository subscriptionRepository,
            VendorRepository vendorRepository,
            CustomerRepository customerRepository,
            AgentRepository agentRepository,
            TransferHistoryRepository transferHistoryRepository,
            QRCodeService qrCodeService) {

        this.subscriptionRepository = subscriptionRepository;
        this.vendorRepository = vendorRepository;
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.transferHistoryRepository = transferHistoryRepository;
        this.qrCodeService = qrCodeService;
    }


    @Override
    public Subscription createSubscription(SubscriptionRequest request) {

        Vendor vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Agent agent = agentRepository.findById(request.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));

        Subscription subscription = new Subscription();
        subscription.setStartDate(request.getStartDate());
        subscription.setEndDate(request.getEndDate());
        subscription.setVendor(vendor);
        subscription.setCustomer(customer);
        subscription.setAgent(agent);
        subscription.setQrToken(UUID.randomUUID().toString());

        if (request.getEndDate().isBefore(LocalDate.now())) {
            subscription.setStatus("EXPIRED");
        } else {
            subscription.setStatus("ACTIVE");
        }

        return subscriptionRepository.save(subscription);
    }


    @Override
    public PaginatedResponse<Subscription> getAllSubscriptions(Pageable pageable) {

        Page<Subscription> page = subscriptionRepository.findAll(pageable);

        return new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }


    @Override
    public Subscription getSubscriptionById(Long id) {

        return subscriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subscription not found with id: " + id));
    }


    @Override
    public Subscription updateSubscription(Long id, SubscriptionRequest request) {

        Subscription subscription = getSubscriptionById(id);

        Vendor vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Agent agent = agentRepository.findById(request.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));

        subscription.setStartDate(request.getStartDate());
        subscription.setEndDate(request.getEndDate());
        subscription.setVendor(vendor);
        subscription.setCustomer(customer);
        subscription.setAgent(agent);

        if (request.getEndDate().isBefore(LocalDate.now())) {
            subscription.setStatus("EXPIRED");
        } else {
            subscription.setStatus("ACTIVE");
        }

        return subscriptionRepository.save(subscription);
    }


    @Override
    public void deleteSubscription(Long id) {

        Subscription subscription = getSubscriptionById(id);
        subscriptionRepository.delete(subscription);
    }


    @Override
    public List<Subscription> getExpiredSubscriptions() {

        return subscriptionRepository.findByEndDateBefore(LocalDate.now());
    }


    @Override
    public List<Subscription> getExpiringSubscriptions(int days) {

        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(days);

        return subscriptionRepository.findByEndDateBetween(today, futureDate);
    }


    @Override
    public List<TransferHistoryResponse> getTransferHistory(Long subscriptionId) {

        subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subscription not found"));

        return transferHistoryRepository
                .findBySubscriptionSubscriptionIdOrderByTransferDateDesc(subscriptionId)
                .stream()
                .map(history -> new TransferHistoryResponse(
                        history.getTransferId(),
                        history.getSubscription().getSubscriptionId(),
                        history.getOldVendor().getName(),
                        history.getNewVendor().getName(),
                        history.getTransferDate()
                ))
                .toList();
    }


    @Override
    public Subscription transferVendor(Long subscriptionId, Long newVendorId) {

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subscription not found"));

        Vendor newVendor = vendorRepository.findById(newVendorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vendor not found"));

        Vendor oldVendor = subscription.getVendor();

        subscription.setVendor(newVendor);
        subscriptionRepository.save(subscription);

        TransferHistory history = new TransferHistory();
        history.setSubscription(subscription);
        history.setOldVendor(oldVendor);
        history.setNewVendor(newVendor);
        history.setTransferDate(LocalDateTime.now());

        transferHistoryRepository.save(history);

        return subscription;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void autoExpireSubscriptions() {

        LocalDate today = LocalDate.now();

        List<Subscription> subscriptions =
                subscriptionRepository.findByEndDateBeforeAndStatus(today, "ACTIVE");

        for (Subscription subscription : subscriptions) {
            subscription.setStatus("EXPIRED");
        }

        subscriptionRepository.saveAll(subscriptions);

        if (!subscriptions.isEmpty()) {
            System.out.println("Auto-expired " + subscriptions.size() + " subscriptions.");
        }
    }


    @Override
    public SubscriptionVerificationResponse verifySubscription(Long id, String token) {

        Subscription subscription = getSubscriptionById(id);

        if (!subscription.getQrToken().equals(token)) {
            throw new RuntimeException("Invalid QR token");
        }

        return new SubscriptionVerificationResponse(
                subscription.getCustomer().getName(),
                subscription.getVendor().getName(),
                subscription.getStatus(),
                subscription.getEndDate()
        );
    }


    @Override
    public Subscription renewSubscription(Long id, RenewSubscriptionRequest request) {

        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subscription not found with id: " + id));

        LocalDate baseDate = subscription.getEndDate().isBefore(LocalDate.now())
                ? LocalDate.now()
                : subscription.getEndDate();

        LocalDate newEndDate;

        switch (request.getPlan()) {

            case THREE_MONTHS:
                newEndDate = baseDate.plusMonths(3);
                break;

            case SIX_MONTHS:
                newEndDate = baseDate.plusMonths(6);
                break;

            case ONE_YEAR:
                newEndDate = baseDate.plusYears(1);
                break;

            default:
                throw new IllegalArgumentException("Invalid renewal plan");
        }

        subscription.setEndDate(newEndDate);
        subscription.setStatus("ACTIVE");

        return subscriptionRepository.save(subscription);
    }

    @Override
    public byte[] generateSubscriptionQR(Long id) {

        Subscription subscription = getSubscriptionById(id);

        String qrText =
                "http://localhost:8080/subscriptions/verify?id="
                        + subscription.getSubscriptionId()
                        + "&token="
                        + subscription.getQrToken();

        return qrCodeService.generateQRCode(qrText);
    }

    @Scheduled(fixedRate = 60000) // runs every 1 minute (for testing)
    public void notifyExpiringSubscriptions() {

        LocalDate today = LocalDate.now();

        List<Subscription> expiringSoon =
                subscriptionRepository.findByEndDateBetween(today, today.plusDays(7));

        for (Subscription subscription : expiringSoon) {

            long daysLeft =
                    java.time.temporal.ChronoUnit.DAYS.between(today, subscription.getEndDate());

            System.out.println(
                    "Subscription Alert -> Customer: "
                            + subscription.getCustomer().getName()
                            + " | Vendor: "
                            + subscription.getVendor().getName()
                            + " | Expires in "
                            + daysLeft
                            + " days"
            );
        }
    }
}