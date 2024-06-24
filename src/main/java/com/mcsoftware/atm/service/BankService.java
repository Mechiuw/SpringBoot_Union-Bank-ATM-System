package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.BankRequest;
import com.mcsoftware.atm.model.dto.response.BankResponse;
import com.mcsoftware.atm.model.dto.response.BranchResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.model.entity.Bank;
import com.mcsoftware.atm.model.entity.Branch;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

public interface BankService {

    //TODO CRUD SERVICE
    BankResponse create(BankRequest bankRequest);
    BankResponse getById(String id);
    List<Bank> getAll();
    BankResponse update(String id,BankRequest bankRequest);
    void delete(String id);

    //TODO BUSINESS LOGIC SERVICE
    List<Branch> listAllBranch(String bankId);
    List<ATM> listAllBranchAtms(String bankId,String branchId);

    BankResponse depositToAtm(String bankId, String branchId,String atmId, BigDecimal depositFromRepo);
    BankResponse withdrawalFromAtm(String bankId, String branchId,String atmId, BigDecimal withdrawalFromAtm);

    List<Account> addAccounts(String id,List<Account> accounts);

    //TODO UTILS
    BigDecimal repositoryManager(BigDecimal balance);
    void transactionsValidator(BigDecimal transactions);
    List<Account> accountLimiter(List<Account> accounts);
}
