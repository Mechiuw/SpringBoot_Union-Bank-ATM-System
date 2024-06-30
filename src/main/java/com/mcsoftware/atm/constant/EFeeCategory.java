package com.mcsoftware.atm.constant;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;

@Configuration
@Getter
public class EFeeCategory {

    @Bean
    @Primary
    public EFeeCategory eFeeCategoryBean(){
        return new EFeeCategory();
    }

    public final BigDecimal MICRO_FEE = new BigDecimal("1500");
    public final BigDecimal STANDARD_FEE = new BigDecimal("2000");
    public BigDecimal EXCESSIVE_FEE = new BigDecimal("2200");

    public final BigDecimal MICRO_RANGE = new BigDecimal("100000");
    public final BigDecimal STANDARD_RANGE = new BigDecimal("500000");
    public final BigDecimal EXCESSIVE_RANGE = new BigDecimal("1000000");
}
