package com.mcsoftware.atm.service;

import com.mcsoftware.atm.constant.TransactionType;
import com.mcsoftware.atm.model.dto.request.TransactionRequest;
import com.mcsoftware.atm.model.dto.response.TransactionResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.model.entity.Card;
import com.mcsoftware.atm.model.entity.Transaction;

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

    //HELPER SERVICE
    //TRX MANAGER
    void trxDeposit(Transaction transaction);
    void trxWithdrawal(Transaction transaction);
    void trxTransfer(Transaction transaction);
    BigDecimal arrangeAmount(BigDecimal amount,TransactionType type,BigDecimal atmBalance);
    BigDecimal arrangeBalanceAtm(ATM atm, BigDecimal amount);

    //VALIDATORS
    Transaction validateTransaction(Transaction transaction);
    Card validateCard(Card card,Account account);
    Account validateAccount(Account account);
    BigDecimal validateBalance(BigDecimal balance);
}
