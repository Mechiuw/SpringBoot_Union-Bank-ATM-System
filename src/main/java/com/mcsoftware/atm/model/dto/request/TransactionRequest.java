package com.mcsoftware.atm.model.dto.request;

import com.mcsoftware.atm.constant.TransactionType;
import com.mcsoftware.atm.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionRequest {

    private ATM atm;
    private LocalDate localDate;
    private BigDecimal amount;
    private TransactionType type;
    private Bank bank;
    private Card card;
    private Account account;
}
