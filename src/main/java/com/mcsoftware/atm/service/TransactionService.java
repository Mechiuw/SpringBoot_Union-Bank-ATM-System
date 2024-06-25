package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.TransactionRequest;
import com.mcsoftware.atm.model.dto.response.TransactionResponse;
import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.model.entity.Card;
import com.mcsoftware.atm.model.entity.Transaction;
import com.mcsoftware.atm.model.entity.TrxFee;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    //CRUD SERVICE
    TransactionResponse create(TransactionRequest transactionRequest);
    TransactionResponse getById(String id);
    TransactionResponse getAll();
    TransactionResponse update(String id, TransactionRequest transactionRequest);

    //BUSINESS LOGIC SERVICE
    TransactionResponse softDelete(String id);
    List<TransactionResponse> getAllTransactionHistory();
    List<TrxFee> getAllTransactionFee();

    //HELPER SERVICE
    //TRX MANAGER
    void trxDeposit(Transaction transaction);
    void trxWithdrawal(Transaction transaction);
    void trxTransfer(Transaction transaction);
    void trxRollback(Exception e);

    //VALIDATORS
    void validateTransaction(Transaction transaction);
    void validateCard(Card card);
    void validateAccount(Account account);
    void validateBalance(BigDecimal balance);
}
