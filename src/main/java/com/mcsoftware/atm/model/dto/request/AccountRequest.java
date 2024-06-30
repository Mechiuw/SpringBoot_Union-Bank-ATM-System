package com.mcsoftware.atm.model.dto.request;

import com.mcsoftware.atm.model.entity.Bank;
import com.mcsoftware.atm.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccountRequest {
    private String accountNumber;
    private BigDecimal balance;
    private User user;
    private Bank bank;
}
