package com.mcsoftware.atm.repository;

import com.mcsoftware.atm.model.entity.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ATMRepository extends JpaRepository<ATM,String> {}
