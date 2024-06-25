package com.mcsoftware.atm.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcsoftware.atm.constant.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JoinColumn(name = "atm_id",referencedColumnName = "id")
    @ManyToOne
    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ATM atm;

    @Column(name = "transaction_date",nullable = false)
    private LocalDate transactionDate;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type; // e.g., "withdrawal", "deposit", "transfer"

    @ManyToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Account account;

    @OneToOne
    @JoinColumn(name = "bank_id",referencedColumnName = "id")
    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Bank bank;

    @OneToOne
    @JoinColumn(name = "card_id",referencedColumnName = "id")
    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Card card;


}
