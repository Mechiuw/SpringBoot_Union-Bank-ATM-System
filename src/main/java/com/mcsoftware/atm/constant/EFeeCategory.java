package com.mcsoftware.atm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EFeeCategory {
    public final BigDecimal MICRO_FEE = new BigDecimal("1500");
    public final BigDecimal STANDARD_FEE = new BigDecimal("2000");
    public BigDecimal EXCESSIVE_FEE = new BigDecimal("2200");

    public final BigDecimal MICRO_RANGE = new BigDecimal("100000");
    public final BigDecimal STANDARD_RANGE = new BigDecimal("500000");
    public final BigDecimal EXCESSIVE_RANGE = new BigDecimal("1000000");
}
