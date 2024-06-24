package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.model.dto.request.BankRequest;
import com.mcsoftware.atm.model.dto.response.BankResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.model.entity.Bank;
import com.mcsoftware.atm.model.entity.Branch;
import com.mcsoftware.atm.repository.ATMRepository;
import com.mcsoftware.atm.repository.BankRepository;
import com.mcsoftware.atm.repository.BranchRepository;
import com.mcsoftware.atm.service.BankService;
import lombok.RequiredArgsConstructor;
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
    private final ATMRepository atmRepository;
    private final BranchRepository branchRepository;
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
        try {
            Bank bank = bankRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found any bank with id : " + id));
            if (bank != null) {
                return BankResponse.builder()
                        .id(bank.getId())
                        .name(bank.getName())
                        .bankRepo(bank.getBankBalanceRepository())
                        .build();
            } else {
                throw new RuntimeException("Exception caught");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
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
    public List<Branch> listAllBranch(String bankId) {
        try {
            Bank bank = bankRepository.findById(bankId)
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));
            if (!bank.getBranches().isEmpty()) {
                return bank.getBranches();
            } else {
                throw new IllegalArgumentException("not found any branches");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ATM> listAllBranchAtms(String bankId,String branchId) {
        try{
            Bank bank = bankRepository.findById(bankId)
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));
            Branch filterBranch = bank.getBranches().stream()
                    .filter(id -> id.getId().equals(branchId)).findFirst()
                    .orElseThrow(() -> new NoSuchElementException("not found any branch wired with branch id"));

            return filterBranch.getAtms();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public BankResponse depositToAtm(String bankId,String branchId,String atmId, BigDecimal depositFromRepo) {
        try {
            Bank bank = bankRepository.findById(bankId)
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));
            Branch branch = bank.getBranches().stream()
                    .filter(x -> x.getId().equals(branchId)).findFirst()
                    .orElseThrow(() -> new NoSuchElementException("not found any branch"));
            ATM atm = branch.getAtms().stream()
                    .filter(x -> x.getId().equals(atmId)).findFirst()
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));

            transactionsValidator(depositFromRepo);

            if (atm != null) {
                BigDecimal deposit = atm.getCashBalance().add(depositFromRepo);
                atm.setCashBalance(deposit);
                BigDecimal withdrawBank = bank.getBankBalanceRepository().subtract(depositFromRepo);
                bank.setBankBalanceRepository(withdrawBank);

                atmRepository.saveAndFlush(atm);
                branchRepository.saveAndFlush(branch);
                Bank save = bankRepository.saveAndFlush(bank);

                return BankResponse.builder()
                        .id(save.getId())
                        .name(save.getName())
                        .changesDeposit("atm balance : " + depositFromRepo + "+")
                        .fromBank("bank repo balance : " + depositFromRepo + "-")
                        .bankRepo(save.getBankBalanceRepository())
                        .build();
            } else {
                return BankResponse.builder()
                        .id("-")
                        .name("-")
                        .changesDeposit("nothing changes, no atm detected")
                        .fromBank("nothing changes, bank balance still normal")
                        .bankRepo(bank.getBankBalanceRepository())
                        .build();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public BankResponse withdrawalFromAtm(String bankId,String branchId, String atmId, BigDecimal withdrawalFromAtm) {
        try {
            Bank bank = bankRepository.findById(bankId)
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));

            Branch branch = bank.getBranches().stream()
                    .filter(x -> x.getId().equals(branchId))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));
            ATM atm = branch.getAtms().stream()
                    .filter(x -> x.getId().equals(atmId))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));

            transactionsValidator(withdrawalFromAtm);

            if (atm != null) {
                BigDecimal minFromAtm = atm.getCashBalance().subtract(withdrawalFromAtm);
                atm.setCashBalance(minFromAtm);
                BigDecimal plusToRepo = bank.getBankBalanceRepository().add(withdrawalFromAtm);
                bank.setBankBalanceRepository(plusToRepo);

                atmRepository.saveAndFlush(atm);
                branchRepository.saveAndFlush(branch);
                Bank savedBank = bankRepository.saveAndFlush(bank);

                return BankResponse.builder()
                        .id(savedBank.getId())
                        .name(savedBank.getName())
                        .changesDeposit("atm balance : " + withdrawalFromAtm + "-")
                        .fromBank("bank repo balance : " + withdrawalFromAtm + "+")
                        .bankRepo(savedBank.getBankBalanceRepository())
                        .build();
            } else {
                return BankResponse.builder()
                        .id("-")
                        .name("-")
                        .changesDeposit("nothing changes, no atm detected")
                        .fromBank("nothing changes, bank balance still normal")
                        .bankRepo(bank.getBankBalanceRepository())
                        .build();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Account> addAccounts(String id,List<Account> accounts) {
        try {
            if (accounts == null || !accounts.isEmpty()) {
                Bank bank = bankRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("not found any bank"));

                assert accounts != null;
                bank.getAccountList().addAll(accounts);

                Bank savedBank = bankRepository.saveAndFlush(bank);
                return savedBank.getAccountList();
            } else {
                throw new IllegalArgumentException("accounts in bank are empty");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
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

    //TODO HELPER
    public LocalDateTime timeExecutor(){
        LocalDate nextYear = LocalDate.now().plusYears(1).withDayOfYear(1);
        LocalTime afternoon = LocalTime.NOON;
        return LocalDateTime.of(nextYear,afternoon);
    }

    @Override
    public void transactionsValidator(BigDecimal transactions) {
        try {
            BigDecimal maximumDeposit = new BigDecimal("3000000");
            BigDecimal maximumWithdraw = new BigDecimal("3000000");
            BigDecimal minimumLimit = BigDecimal.ZERO;

            if (transactions.compareTo(maximumDeposit) > 0) {
                throw new IllegalArgumentException("maximum deposit around " + maximumDeposit);
            }
            if (transactions.compareTo(maximumWithdraw) > 0) {
                throw new IllegalArgumentException("maximum withdrawal atm around " + maximumWithdraw);
            }
            if (transactions.compareTo(minimumLimit) <= 0) {
                throw new IllegalArgumentException("Transaction amount must be greater than zero");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public List<Account> accountLimiter(List<Account> accounts){
        try {
            if (accounts.size() < 5) {
                throw new IllegalArgumentException("accounts should be atleast 5");
            }
            if (accounts.size() >= 10) {
                System.err.println("regulations for initializing bank, to have 5 to 20 members only at first");
                return accounts.stream().limit(10).toList();
            }
            return accounts;
        } catch (Exception e){
            System.err.println("Exception caught : " + e.getMessage());
            throw e;
        }
    }

    public List<Branch> branchManager(List<Branch> branches){
        try {
            if (branches.size() < 5) {
                throw new IllegalArgumentException("branches should be atleast 5");
            }
            for (Branch branch : branches) {
                if (branch.getAtms().isEmpty()) {
                    throw new IllegalArgumentException("ATM should be atleast 5");
                }
                if (branch.getAtms().size() < 5) {
                    throw new IllegalArgumentException("regulations for initializing bank, to atleast have 5 atm each branch");
                }
            }
            return branches;
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }
}
