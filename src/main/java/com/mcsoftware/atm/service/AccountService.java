package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.AccountRequest;
import com.mcsoftware.atm.model.dto.response.AccountResponse;
import com.mcsoftware.atm.model.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    //TODO MAIN CRUD SERVICE LAYER
    AccountResponse create(AccountRequest accountRequest);
    List<Account> getAll();
    AccountResponse getById(String id);
    AccountResponse update(String id,AccountRequest accountRequest);
    void delete(String id);

    //TODO BUSINESS LOGIC SERVICE LAYER
    AccountResponse softDeleteAccount(String id);
    AccountResponse checkCurrentBalance(String id);

    //TODO VALIDATIONS LAYER
    void receivedTransfer(Account account);
    void minimumDeposit(BigDecimal balance);
    void checkAccountChanges(Account account,AccountRequest accountRequest);
    void checkAccountDeletion(String accId,Account account);
}
