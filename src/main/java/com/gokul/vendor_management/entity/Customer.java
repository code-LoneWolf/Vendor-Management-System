package com.gokul.vendor_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(nullable = false)
    private String name;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

}