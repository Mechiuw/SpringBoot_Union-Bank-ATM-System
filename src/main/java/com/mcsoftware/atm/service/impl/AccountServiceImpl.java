package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.model.dto.request.AccountRequest;
import com.mcsoftware.atm.model.dto.response.AccountResponse;
import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.model.entity.User;
import com.mcsoftware.atm.repository.AccountRepository;
import com.mcsoftware.atm.repository.UserRepository;
import com.mcsoftware.atm.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public void minimumDeposit(BigDecimal currentBalance) throws IllegalArgumentException {
        if(currentBalance.compareTo(new BigDecimal("500000")) < 0){
            throw new IllegalArgumentException("[WARNING] DEPOSIT BALANCE MUST BE LEAST 500000");
        }

    };
    @Override
    public AccountResponse create(AccountRequest accountRequest) throws RuntimeException {

        //validates minimum deposit account
        minimumDeposit(accountRequest.getBalance());

        String userId = accountRequest.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("not found user with id : %s",userId)));

        assert user != null : "user must not be null or not found";

        Account account = Account.builder()
                .accountNumber(accountRequest.getAccountNumber())
                .balance(accountRequest.getBalance())
                .user(user)
                .balance(accountRequest.getBalance())
                .build();
        Account savedAccount = accountRepository.save(account);

        return AccountResponse.builder()
                .id(savedAccount.getId())
                .accountNumber(savedAccount.getAccountNumber())
                .balance(savedAccount.getBalance())
                .userId(savedAccount.getUser().getId())
                .build();
    }

    @Override
    public List<Account> getAll() {
        return null;
    }

    @Override
    public AccountResponse getById(String id) {
        return null;
    }

    @Override
    public AccountResponse update(String id, AccountRequest accountRequest) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public AccountResponse softDeleteAccount(String id, AccountRequest accountRequest) {
        return null;
    }

    @Override
    public AccountResponse checkCurrentBalance(String id) {
        return null;
    }

    @Override
    public AccountResponse depositBalance(String id, BigDecimal deposit) {
        return null;
    }

    @Override
    public AccountResponse withdrawBalance(String id, BigDecimal withdraw) {
        return null;
    }

    @Override
    public AccountResponse transferBalance(String accountId, String transferId, BigDecimal transfer) {
        return null;
    }
}
