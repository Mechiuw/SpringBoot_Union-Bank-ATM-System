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

        List<Account> limitedAccounts = accountLimiter(bankRequest.getAccountList());
        List<Branch> branches = branchManager(bankRequest.getBranchList());
        Bank bank = Bank.builder()
                .name(bankRequest.getName())
                .branches(branches)
                .accountList(limitedAccounts)
                .bankBalanceRepository(bankRequest.getBankRepo())
                .build();

        Bank savedBank = bankRepository.saveAndFlush(bank);
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
        if(accounts == null || !accounts.isEmpty()){
            Bank bank = bankRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));

            assert accounts != null;
            bank.getAccountList().addAll(accounts);

            Bank savedBank = bankRepository.saveAndFlush(bank);
            return savedBank.getAccountList();
        } else {
            throw new IllegalArgumentException("accounts in bank are empty");
        }
    }

    @Override
    public BigDecimal repositoryManager(BigDecimal balance) {
        BigDecimal standart = new BigDecimal("100000000");
        if(balance.compareTo(standart) < 0){
            throw new IllegalArgumentException("");
        }
        return null;
    }

    @Override
    public void transactionsValidator(BigDecimal transactions) {

    }

    public List<Account> accountLimiter(List<Account> accounts){
        if(accounts.size() < 5){
            throw new IllegalArgumentException("accounts should be atleast 5");
        }
        if(accounts.size() >= 10){
            System.err.println("regulations for initializing bank, to have 5 to 20 members only at first");
            return accounts.stream().limit(10).toList();
        }
        return accounts;
    }

    public List<Branch> branchManager(List<Branch> branches){
        if(branches.size() < 5){
            throw new IllegalArgumentException("branches should be atleast 5");
        }
        for(Branch branch : branches){
            if(branch.getAtms().isEmpty()){
                throw new IllegalArgumentException("ATM should be atleast 5");
            }
            if(branch.getAtms().size() < 5){
                throw new IllegalArgumentException("regulations for initializing bank, to atleast have 5 atm each branch");
            }
        }
        return branches;
    }
}
