package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.BankRequest;
import com.mcsoftware.atm.model.dto.response.BankResponse;
import com.mcsoftware.atm.model.dto.response.BranchResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Bank;
import com.mcsoftware.atm.model.entity.Branch;

import java.math.BigDecimal;
import java.util.List;

public interface BankService {
    BankResponse create(BankRequest bankRequest);
    BankResponse getById(String id);
    List<Bank> getAll();
    BankResponse update(String id,BankRequest bankRequest);
    void delete(String id);

    List<Branch> listAllBranch();
    List<ATM> listAllBranchAtms(String branchId);

    BankResponse depositToAtm(String bankId, String atmId, BigDecimal depositFromRepo);
    BankResponse withdrawalFromAtm(String bankId, String atmId, BigDecimal withdrawalFromAtm);

    //TODO UTILS
    BigDecimal repositoryManager(BigDecimal balance);
    void transactionsValidator(BigDecimal transactions);

}
