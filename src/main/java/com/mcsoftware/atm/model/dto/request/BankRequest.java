package com.mcsoftware.atm.model.dto.request;

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
public class BankRequest {
    private String name;
    private List<BranchRequest> branchList;
    private List<AccountRequest> accountList;
    private BigDecimal bankRepo;
}
