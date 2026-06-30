package com.example.socialnetworkproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "vouchers")
@Data
public class VoucherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  unique = true)
    private String code;

    @Column(name = "discount_amount", nullable = false)
    private double discountAmount;

    @Column(name = "min_order_value", nullable = false)
    private double minOrderValue;

    @Column(name = "max_usage", nullable = false)
    private int maxUsage;

    @Column(name = "used_count", nullable = false)
    private int usedCount;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
}
