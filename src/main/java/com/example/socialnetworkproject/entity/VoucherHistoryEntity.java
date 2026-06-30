package com.example.socialnetworkproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "voucher_histories")
@Data
public class VoucherHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "voucher_id", nullable = false)
    private VoucherEntity voucher;

    private LocalDateTime usedAt;
}
