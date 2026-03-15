package com.gokul.vendor_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Agent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_id")
    private Long agentId;

    @Column(nullable = false)
    private String name;

    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    private String area;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

}