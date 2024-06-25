package com.mcsoftware.atm.model.dto.response;

import com.mcsoftware.atm.constant.EFeeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrxFeeResponse {
    private String id;
    private EFeeCategory feeCategory;
    private BigDecimal cost;
    private String regulations;
}
