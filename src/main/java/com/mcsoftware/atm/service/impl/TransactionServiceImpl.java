package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.constant.EFeeCategory;
import com.mcsoftware.atm.constant.TransactionType;
import com.mcsoftware.atm.model.dto.request.TransactionRequest;
import com.mcsoftware.atm.model.dto.response.TransactionResponse;
import com.mcsoftware.atm.model.entity.*;
import com.mcsoftware.atm.repository.*;
import com.mcsoftware.atm.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(rollbackOn = {Exception.class, RuntimeException.class})
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final List<TransactionResponse> transactionsHistory;
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final ATMRepository atmRepository;
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final EFeeCategory eFeeCategory;

    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {

        //FETCH DATA
        ATM atm = atmRepository.findById(transactionRequest.getAtm().getId())
                .orElseThrow(() -> new NoSuchElementException("can't scan ATM"));
        LocalDate timeTrx = transactionRequest.getLocalDate();
        BigDecimal amount = transactionRequest.getAmount();
        TransactionType trxType = transactionRequest.getType();
        Account account = accountRepository.findById(transactionRequest.getAccount().getId())
                .orElseThrow(() -> new NoSuchElementException("not found any account"));
        Bank bank = bankRepository.findById(transactionRequest.getBank().getId())
                .orElseThrow(() -> new NoSuchElementException("can't scan Bank"));
        Card card = cardRepository.findById(transactionRequest.getCard().getId())
                .orElseThrow(() -> new NoSuchElementException("not found any card"));

        //VALIDATE DATA
        Account validatedAccount = validateAccount(account); // validate account
        BigDecimal fetchBalance = validatedAccount.getBalance(); // fetch balance from validated account
        Card validatedCard = validateCard(card,account); // validate card
        BigDecimal userBalance = validateBalance(fetchBalance); // validate balance from the fetchBalance
        BigDecimal userBalanceWithFee = arrangeAmount(amount,trxType,userBalance); // this is user balance with fee , changed previous validated-fetch balance with fee
        BigDecimal changedAtmBalance = arrangeBalanceAtm(atm,amount);  // this is atm balance added from amount

        //UPDATE DATA
        account.setBalance(userBalanceWithFee); // to update the user balance
        atm.setCashBalance(changedAtmBalance); // to update the atm balance

        //SAVE DATA
        Account savedAccount = accountRepository.saveAndFlush(validatedAccount); // to save the user/account balance
        ATM savedAtm = atmRepository.saveAndFlush(atm); // to save the atm balance

        Transaction finalTransaction = Transaction.builder()
                .atm(savedAtm)
                .transactionDate(timeTrx)
                .amount(amount)
                .type(trxType)
                .account(savedAccount)
                .card(validatedCard)
                .bank(bank)
                .build();
        try {
                Transaction validatedTransaction = validateTransaction(finalTransaction);
                transactionRepository.saveAndFlush(validatedTransaction);
                TransactionResponse response = TransactionResponse.builder()
                        .id(validatedTransaction.getId())
                        .atm(validatedTransaction.getAtm())
                        .localDate(validatedTransaction.getTransactionDate())
                        .amount(validatedTransaction.getAmount())
                        .type(validatedTransaction.getType())
                        .account(validatedTransaction.getAccount().getId())
                        .bank(validatedTransaction.getBank().getId())
                        .card(validatedTransaction.getCard().getId())
                        .build();

                transactionsHistory.add(response);

                return response;
            } catch (Exception e) {
                return TransactionResponse.builder()
                        .id("not found || rolling back...")
                        .atm(null)
                        .localDate(null)
                        .amount(BigDecimal.ZERO)
                        .type(null)
                        .account("not found account || rolling back...")
                        .bank("not found bank || rolling back...")
                        .card("not found card || rolling back...")
                        .build();
            }
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
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found any transaction"));
        transaction.setAtm(null);
        transaction.setTransactionDate(null);
        transaction.setAmount(BigDecimal.ZERO);
        transaction.setType(null);
        transaction.setAccount(null);
        transaction.setBank(null);
        transaction.setCard(null);

        Transaction softDelete = transactionRepository.saveAndFlush(transaction);
        return TransactionResponse.builder()
                .id(softDelete.getId())
                .atm(softDelete.getAtm())
                .localDate(softDelete.getTransactionDate())
                .amount(softDelete.getAmount())
                .type(softDelete.getType())
                .account(softDelete.getAccount().getId())
                .bank(softDelete.getBank().getId())
                .card(softDelete.getCard().getId())
                .build();
    }

    @Override
    public List<TransactionResponse> getAllTransactionHistory() {
        try {
            if(!transactionsHistory.isEmpty()){
                return transactionsHistory;
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }


    @Override
    public Transaction validateTransaction(Transaction transaction) {
        if(transaction.getAtm() == null){
            throw new IllegalArgumentException("can't process transaction atm is null");
        }
        if(transaction.getTransactionDate() == null){
            throw new IllegalArgumentException("can't process transaction transaction date is null");
        }
        if(transaction.getAmount() == null){
            throw new IllegalArgumentException("can't process transaction amount is null");
        }
        if(transaction.getType() == null){
            throw new IllegalArgumentException("can't process transaction transaction type is null");
        }
        if(transaction.getAccount() == null){
            throw new IllegalArgumentException("can't process transaction account is null");
        }
        if(transaction.getBank() == null){
            throw new IllegalArgumentException("can't process transaction bank is null");
        }
        if(transaction.getCard() == null){
            throw new IllegalArgumentException("can't process transaction card is null");
        }
        else {
            return transaction;
        }
    }

    @Override
    public Card validateCard(Card card,Account account) {
        if(card.getAccount().equals(account)){
            return card;
        } else {
            throw new IllegalArgumentException("card and account are has different access");
        }
    }


    @Override
    public Account validateAccount(Account account) {
        if(account.getAccountNumber() == null){
            throw new IllegalArgumentException("account number can't be null");
        }
        if(account.getUser() == null){
            throw new IllegalArgumentException("account user can't be null");
        }
        if(account.getBalance() == null){
            throw new IllegalArgumentException("account balance can't be null");
        }
        else {
            return account;
        }
    }

    @Override
    public BigDecimal validateBalance(BigDecimal balance) {
        final BigDecimal least = new BigDecimal("1000");
        if(balance.compareTo(least) > 0){
            return balance;
        } else {
            throw new IllegalArgumentException("transaction failed, account balance doesn't meet with the least balance " + least);
        }
    }

    @Override
    public BigDecimal arrangeAmount(BigDecimal amount,TransactionType type,BigDecimal atmBalance) {
        try {
            final BigDecimal MICRO_FEE = eFeeCategory.MICRO_FEE;
            final BigDecimal STANDARD_FEE = eFeeCategory.STANDARD_FEE;
            final BigDecimal EXCESSIVE_FEE = eFeeCategory.EXCESSIVE_FEE;

            final BigDecimal MICRO_RANGE = eFeeCategory.MICRO_RANGE;
            final BigDecimal STANDARD_RANGE = eFeeCategory.STANDARD_RANGE;
            final BigDecimal EXCESSIVE_RANGE = eFeeCategory.EXCESSIVE_RANGE;

            if (type == TransactionType.DEPOSIT || type == TransactionType.TRANSFER || type == TransactionType.WITHDRAWAL) {
                if (amount.compareTo(MICRO_RANGE) < 0) {
                    return atmBalance.subtract(MICRO_FEE.add(amount));
                } else if (amount.compareTo(STANDARD_RANGE) <= 0) {
                    return atmBalance.subtract(STANDARD_FEE.add(amount));
                } else if (amount.compareTo(EXCESSIVE_RANGE) <= 0) {
                    return atmBalance.subtract(EXCESSIVE_FEE.add(amount));
                } else {
                    return atmBalance.subtract(EXCESSIVE_FEE);
                }
            } else {
                throw new IllegalArgumentException("Unsupported transaction type: " + type);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public BigDecimal arrangeBalanceAtm(ATM atm,BigDecimal amount) {
        if(amount != null){
               return atm.getCashBalance().subtract(amount);
        } else {
            throw new IllegalArgumentException("amount or feeIncome can't be null");
        }
    }
}
