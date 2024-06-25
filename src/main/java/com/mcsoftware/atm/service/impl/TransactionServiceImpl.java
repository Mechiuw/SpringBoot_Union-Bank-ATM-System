package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.constant.TransactionType;
import com.mcsoftware.atm.model.dto.request.TransactionRequest;
import com.mcsoftware.atm.model.dto.response.TransactionResponse;
import com.mcsoftware.atm.model.entity.*;
import com.mcsoftware.atm.repository.*;
import com.mcsoftware.atm.service.TransactionService;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(rollbackOn = RollbackException.class)
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final ATMRepository atmRepository;
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final TrxFeeRepository trxFeeRepository;

    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {

        //FETCH DATA
        ATM atm =atmRepository.findById(transactionRequest.getAtm().getId())
                .orElseThrow(() -> new NoSuchElementException("can't scan ATM"));
        LocalDateTime timeTrx = transactionRequest.getLocalDateTime();
        BigDecimal amount = transactionRequest.getAmount();
        TransactionType trxType = transactionRequest.getType();
        Account account = accountRepository.findById(transactionRequest.getAccount().getId())
                .orElseThrow(() -> new NoSuchElementException("not found any account"));
        Bank bank = bankRepository.findById(transactionRequest.getBank().getId())
                .orElseThrow(() -> new NoSuchElementException("can't scan Bank"));
        Card card = cardRepository.findById(transactionRequest.getCard().getId())
                .orElseThrow(() -> new NoSuchElementException("not found any card"));


        TrxFee trxFee = trxFeeRepository.findById(transactionRequest.getTrxFee().getId())
                .orElseThrow(() -> new NullPointerException("not found any transaction fee"));

        //VALIDATE DATA
        validateAccount(account);
        validateCard(card);
        validateBalance(amount);
        validateTrxType(trxType);
        assert atm != null :"cannot find any atm";
        assert bank != null : "cannot find any bank";
        assert trxFee != null : "empty tax is forbidden";

        Transaction transaction = Transaction.builder()
                .build();

        return null;
    }

    @Override
    public TransactionResponse getById(String id) {
        return null;
    }

    @Override
    public TransactionResponse getAll() {
        return null;
    }

    @Override
    public TransactionResponse update(String id, TransactionRequest transactionRequest) {
        return null;
    }

    @Override
    public TransactionResponse softDelete(String id) {
        return null;
    }

    @Override
    public List<TransactionResponse> getAllTransactionHistory() {
        return null;
    }

    @Override
    public List<TrxFee> getAllTransactionFee() {
        return null;
    }

    @Override
    public void trxDeposit(Transaction transaction) {

    }

    @Override
    public void trxWithdrawal(Transaction transaction) {

    }

    @Override
    public void trxTransfer(Transaction transaction) {

    }

    @Override
    public void trxRollback(Exception e) {

    }

    @Override
    public void validateTransaction(Transaction transaction) {

    }

    @Override
    public void validateCard(Card card) {

    }

    @Override
    public void validateTrxType(TransactionType type) {

    }

    @Override
    public void validateAccount(Account account) {

    }

    @Override
    public void validateBalance(BigDecimal balance) {

    }

    @Override
    public BigDecimal arrangeAmount(BigDecimal amount,TransactionType type) {
        final BigDecimal MICRO_FEE = new BigDecimal("1500");
        final BigDecimal STANDARD_FEE = new BigDecimal("2000");
        final BigDecimal EXCESSIVE_FEE = new BigDecimal("2200");

        final BigDecimal MICRO_RANGE = new BigDecimal("100000");
        final BigDecimal STANDARD_RANGE = new BigDecimal("500000");
        final BigDecimal EXCESSIVE_RANGE = new BigDecimal("1000000");

        if (type == TransactionType.DEPOSIT || type == TransactionType.TRANSFER || type == TransactionType.WITHDRAWAL) {
            if (amount.compareTo(MICRO_RANGE) < 0) {
                return amount.subtract(MICRO_FEE);
            } else if (amount.compareTo(STANDARD_RANGE) < 0) {
                return amount.subtract(STANDARD_FEE);
            } else if (amount.compareTo(EXCESSIVE_RANGE) < 0) {
                return amount.subtract(EXCESSIVE_FEE);
            } else {
                // Handle amounts larger than excessive range
                throw new IllegalArgumentException("Amount exceeds excessive range");
            }
        } else {
            // Handle unknown transaction types
            throw new IllegalArgumentException("Unsupported transaction type: " + type);
        }
    }
}
