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
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
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
        BigDecimal bankBalance = repositoryManager(bankRequest.getBankRepo());
        Bank bank = Bank.builder()
                .name(bankRequest.getName())
                .branches(branches)
                .accountList(limitedAccounts)
                .bankBalanceRepository(bankBalance)
                .build();

        Bank savedBank = bankRepository.saveAndFlush(bank);
        return BankResponse.builder()
                .id(savedBank.getId())
                .name(savedBank.getName())
                .branchList(savedBank.getBranches())
                .accountList(savedBank.getAccountList())
                .bankRepo(savedBank.getBankBalanceRepository())
                .build();
    }

    @Override
    public BankResponse getById(String id) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found any bank with id : "+ id));
        if(bank != null){
            return BankResponse.builder()
                    .id(bank.getId())
                    .name(bank.getName())
                    .bankRepo(bank.getBankBalanceRepository())
                    .build();
        } else {
            throw new RuntimeException("Exception caught");
        }
    }

    @Override
    public List<Bank> getAll() {
        try {
            List<Bank> banks = bankRepository.findAll();
            if (banks.isEmpty()) {
                System.err.println("not found any bank");
                return Collections.emptyList();
            } else {
                return banks;
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public BankResponse update(String id, BankRequest bankRequest) {

        try{
            Bank bank = bankRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found the associated bank with id : " + id));
            if(bank != null){
                bank.setName(bankRequest.getName());
                bank.setBranches(bankRequest.getBranchList());
                bank.setAccountList(bankRequest.getAccountList());
                bank.setBankBalanceRepository(bankRequest.getBankRepo());

                Bank newUpdatedBank = bankRepository.saveAndFlush(bank);
                return BankResponse.builder()
                        .id(newUpdatedBank.getId())
                        .name(newUpdatedBank.getName())
                        .branchList(newUpdatedBank.getBranches())
                        .bankRepo(newUpdatedBank.getBankBalanceRepository())
                        .accountList(newUpdatedBank.getAccountList())
                        .build();
            }
            return BankResponse.builder()
                    .id("not found id")
                    .name("not found name")
                    .bankRepo(BigDecimal.ZERO)
                    .accountList(Collections.emptyList())
                    .branchList(Collections.emptyList())
                    .build();
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        try{
        Bank foundBank = bankRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found any bank with id: " + id));
        if(foundBank != null){
            bankRepository.delete(foundBank);
        }
        } catch ( Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
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
        BigDecimal standard = new BigDecimal("100000000");
        BigDecimal tax = new BigDecimal("2500000");

        if(balance.compareTo(standard) < 0){
            throw new IllegalArgumentException("bank repository balance should atleast meet the minimum deposit: " + standard);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeToCompare = timeExecutor();
        if(now.isAfter(timeToCompare)){
            return balance.subtract(tax);
        }

        return balance;
    }

    public LocalDateTime timeExecutor(){
        LocalDate nextYear = LocalDate.now().plusYears(1).withDayOfYear(1);
        LocalTime afternoon = LocalTime.NOON;
        return LocalDateTime.of(nextYear,afternoon);
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
