package com.mcsoftware.atm.model.dto.request;

import com.mcsoftware.atm.constant.TransactionType;
import com.mcsoftware.atm.model.entity.Account;
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
public class TransactionRequest {
    private LocalDateTime localDateTime;
    private BigDecimal amount;
    private TransactionType type;
    private Account account;
}
