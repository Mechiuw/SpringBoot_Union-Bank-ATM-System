package com.mcsoftware.atm.model.dto.request;

import com.mcsoftware.atm.model.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ATMRequest {
    private String location;
    private Branch branch;
    private BigDecimal cashBalance;
}
