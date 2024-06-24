package com.mcsoftware.atm.model.dto.response;

import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.model.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BankResponse {
    private String id;
    private String name;
    private List<Branch> branchList;
    private List<Account> accountList;
    private BigDecimal bankRepo;

    private String changesDeposit;
    private String fromBank;
    private String changesWithdraw;
}
