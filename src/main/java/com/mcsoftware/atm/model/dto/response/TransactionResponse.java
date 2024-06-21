package com.mcsoftware.atm.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionResponse {
    private String id;
    private String atm;
    private String localDateTime;
    private BigDecimal amount;
    private String type;
    private String account;
}
