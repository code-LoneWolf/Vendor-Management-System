package com.gokul.vendor_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Double amount;

    private LocalDate paymentDate;

    private String paymentMethod;

    private String status;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
}