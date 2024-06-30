package com.mcsoftware.atm.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "accountNumber", nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @JoinColumn(referencedColumnName = "id",name = "user_id")
    @ManyToOne
    @JsonBackReference
    private User user;

    @JoinColumn(referencedColumnName = "id",name = "bank_id")
    @ManyToOne
    @JsonBackReference
    private Bank bank;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Transaction> transactionList;
}
