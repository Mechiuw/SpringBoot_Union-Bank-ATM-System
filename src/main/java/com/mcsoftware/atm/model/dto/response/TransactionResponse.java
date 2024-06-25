package com.mcsoftware.atm.model.dto.response;

import com.mcsoftware.atm.constant.TransactionType;
import com.mcsoftware.atm.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionResponse {
    private String id;
    private ATM atm;
    private LocalDateTime localDateTime;
    private BigDecimal amount;
    private TransactionType type;
    private Bank bank;
    private Card card;
    private Account account;
    private TrxFee trxFee;
}
