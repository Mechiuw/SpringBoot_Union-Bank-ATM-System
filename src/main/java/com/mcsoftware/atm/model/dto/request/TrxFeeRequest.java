package com.mcsoftware.atm.model.dto.request;

import com.mcsoftware.atm.constant.EFeeCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrxFeeRequest {
    private EFeeCategory feeCategory;
    private BigDecimal cost;
    private String regulations;
}
