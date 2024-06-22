package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.AccountRequest;
import com.mcsoftware.atm.model.dto.response.AccountResponse;
import com.mcsoftware.atm.model.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountResponse create(AccountRequest accountRequest);
    List<Account> getAll();
    AccountResponse getById(String id);
    AccountResponse update(String id,AccountRequest accountRequest);
    void delete(String id);
    AccountResponse softDeleteAccount(String id);
    AccountResponse checkCurrentBalance(String id);
    AccountResponse depositBalance(String id, BigDecimal deposit);
    AccountResponse withdrawBalance(String id, BigDecimal withdraw);
    AccountResponse transferBalance(String accountId,String transferId, BigDecimal transfer);

    void recievedTransfer(Account account);
}
