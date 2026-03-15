package com.gokul.vendor_management.dto;

import java.time.LocalDateTime;

public class TransferHistoryResponse {

    private Long transferId;
    private Long subscriptionId;
    private String oldVendorName;
    private String newVendorName;
    private LocalDateTime transferDate;

    public TransferHistoryResponse(
            Long transferId,
            Long subscriptionId,
            String oldVendorName,
            String newVendorName,
            LocalDateTime transferDate) {

        this.transferId = transferId;
        this.subscriptionId = subscriptionId;
        this.oldVendorName = oldVendorName;
        this.newVendorName = newVendorName;
        this.transferDate = transferDate;
    }

    public Long getTransferId() { return transferId; }
    public Long getSubscriptionId() { return subscriptionId; }
    public String getOldVendorName() { return oldVendorName; }
    public String getNewVendorName() { return newVendorName; }
    public LocalDateTime getTransferDate() { return transferDate; }
}