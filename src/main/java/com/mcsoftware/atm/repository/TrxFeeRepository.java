package com.mcsoftware.atm.repository;

import com.mcsoftware.atm.model.entity.TrxFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxFeeRepository extends JpaRepository<TrxFee,String> {
}
