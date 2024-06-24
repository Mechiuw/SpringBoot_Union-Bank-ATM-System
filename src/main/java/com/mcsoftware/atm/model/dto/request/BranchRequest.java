package com.mcsoftware.atm.model.dto.request;

import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Bank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchRequest {
    private String name;
    private String location;
    private Bank bank;
    private List<ATM> atmList;
}
