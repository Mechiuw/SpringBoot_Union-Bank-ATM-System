package com.mcsoftware.atm.model.entity;

import com.mcsoftware.atm.constant.EFeeCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "trx_fee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrxFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "fee_category",nullable = false)
    @Enumerated(EnumType.STRING)
    private EFeeCategory feeCategory;

    @Column(name = "regulations",nullable = false)
    private String regulations;
}
