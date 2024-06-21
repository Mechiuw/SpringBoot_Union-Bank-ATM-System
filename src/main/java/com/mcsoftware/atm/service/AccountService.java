package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.response.AccountResponse;
import com.mcsoftware.atm.model.entity.Account;

import java.util.List;

public interface AccountService {
    AccountResponse create(AccountResponse accountResponse);
    List<Account> getAll();
    AccountResponse getById(String id);
    AccountResponse update(String id,AccountResponse accountResponse);
    void delete(String id);
}
