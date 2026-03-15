package com.gokul.vendor_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfer_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"subscription", "oldVendor", "newVendor"})
public class TransferHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long transferId;

    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "old_vendor_id", nullable = false)
    private Vendor oldVendor;

    @ManyToOne
    @JoinColumn(name = "new_vendor_id", nullable = false)
    private Vendor newVendor;

    @Column(name = "transfer_date")
    private LocalDateTime transferDate;
}