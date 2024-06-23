package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.model.dto.request.BankRequest;
import com.mcsoftware.atm.model.dto.response.BankResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.model.entity.Bank;
import com.mcsoftware.atm.model.entity.Branch;
import com.mcsoftware.atm.repository.BankRepository;
import com.mcsoftware.atm.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    @Override
    public BankResponse create(BankRequest bankRequest) {
        Bank bank = Bank.builder()
                .name(bankRequest.getName())
                .build();
        return null;
    }

    @Override
    public BankResponse getById(String id) {
        return null;
    }

    @Override
    public List<Bank> getAll() {
        return null;
    }

    @Override
    public BankResponse update(String id, BankRequest bankRequest) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Branch> listAllBranch() {
        return null;
    }

    @Override
    public List<ATM> listAllBranchAtms(String branchId) {
        return null;
    }

    @Override
    public BankResponse depositToAtm(String bankId, String atmId, BigDecimal depositFromRepo) {
        return null;
    }

    @Override
    public BankResponse withdrawalFromAtm(String bankId, String atmId, BigDecimal withdrawalFromAtm) {
        return null;
    }

    @Override
    public List<Account> addAccounts(String id,List<Account> accounts) {
        if(!accounts.isEmpty()){
            Bank bank = bankRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));
            bank.setAccountList(accounts);
            Bank savedBank = bankRepository.saveAndFlush(bank);
            return savedBank.getAccountList();
        } else {
            throw new NoSuchElementException("accounts in bank are empty");
        }
    }

    @Override
    public BigDecimal repositoryManager(BigDecimal balance) {
        return null;
    }

    @Override
    public void transactionsValidator(BigDecimal transactions) {

    }
}
