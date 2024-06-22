# One of the most longest "mini-transaction" service layer i ever made 😂😂

```java
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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public void minimumDeposit(BigDecimal currentBalance) {
        if(currentBalance.compareTo(new BigDecimal("500000")) < 0){
            throw new IllegalArgumentException("[WARNING] DEPOSIT BALANCE MUST BE LEAST 500000");
        }

    };

    public void checkAccountChanges(Account account,AccountRequest accountRequest){
        String accNumber = accountRequest.getAccountNumber();
        String accUserId = accountRequest.getUser().getId();
        if(!account.getAccountNumber().equals(accNumber)){
            throw new IllegalArgumentException(String.format("not the same updated changes of %s",accNumber));
        }
        if(!account.getUser().getId().equals(accUserId)){
            throw new IllegalArgumentException(String.format("not the same updated changes of %s",accUserId));
        }
    }

    public void checkAccountDeletion(String accId, Account account) {
        Optional<Account> checkPresent = accountRepository.findById(accId);
        if (checkPresent.isPresent()) {
            accountRepository.delete(account);
            throw new IllegalArgumentException("Account was not properly deleted the first time, attempted deletion again.");
        }
    }


    @Override
    public AccountResponse create(AccountRequest accountRequest) {

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
        try {
            List<Account> accountList = accountRepository.findAll();
            if(!accountList.isEmpty()){
                return accountList;
            } else {
                throw new NoSuchElementException("not found any account on list");
            }
        } catch (Exception e){
            System.err.printf("Exception while retrieving accounts : %s%n",e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public AccountResponse getById(String id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("not found account with id : %s", id)));

            String userId = account.getUser().getId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException(String.format("not found user with id : %s", userId)));
            return AccountResponse.builder()
                    .id(account.getId())
                    .accountNumber(account.getAccountNumber())
                    .userId(user.getId())
                    .balance(account.getBalance())
                    .build();
        } catch (NoSuchElementException e) {
            System.err.printf("Not found specific user associated with the account : %s%n",e.getMessage());
            return AccountResponse.builder()
                    .id("account id not found")
                    .accountNumber("account number not found")
                    .userId("user not found")
                    .balance(BigDecimal.ZERO)
                    .build();
        }
        catch (Exception e){
            System.err.printf("Exception handled while retrieving account : %s%n",e.getMessage());
            return null;
        }
    }

    @Override
    public AccountResponse update(String id, AccountRequest accountRequest) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("not found account with id : %s", id)));
            account.setAccountNumber(accountRequest.getAccountNumber());
            account.setUser(accountRequest.getUser());

            Account updatedAccount = accountRepository.saveAndFlush(account);
            checkAccountChanges(updatedAccount, accountRequest);

            return AccountResponse.builder()
                    .id(updatedAccount.getId())
                    .accountNumber(updatedAccount.getAccountNumber())
                    .userId(updatedAccount.getUser().getId())
                    .balance(updatedAccount.getBalance())
                    .build();
        } catch (NoSuchElementException e){
            System.err.printf("No Such Element of %s%n",e.getMessage());
            return AccountResponse.builder()
                    .id("not found")
                    .accountNumber("not found")
                    .userId("not found")
                    .balance(BigDecimal.ZERO)
                    .build();
        } catch (IllegalArgumentException e){
            System.err.printf("Validations error associated with the account : %s%n",e.getMessage());
            return AccountResponse.builder()
                    .id("validation error")
                    .accountNumber("validation error")
                    .userId("validation error")
                    .balance(BigDecimal.ZERO)
                    .build();
        } catch (Exception e) {
            System.err.printf("Exception called %s%n",e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("not found account with id : %s", id)));
            accountRepository.delete(account);
            checkAccountDeletion(id, account);

        } catch (NoSuchElementException e){
            System.err.printf("No such element: %s%n",e.getMessage());
            throw e;
        } catch (IllegalArgumentException e){
            System.err.printf("Improper Deletion: %s%n",e.getMessage());
            throw e;
        } catch (Exception e){
            System.err.printf("Exception caught: %s%n",e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountResponse softDeleteAccount(String id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("not found account: %s",id)));

            account.setAccountNumber("DELETED");
            account.setBalance(BigDecimal.ZERO);
            account.setUser(null);
            account.setTransactionList(Collections.emptyList());
            Account saveChanges = accountRepository.saveAndFlush(account);
            return AccountResponse.builder()
                    .id(saveChanges.getId())
                    .accountNumber(saveChanges.getAccountNumber())
                    .userId(saveChanges.getUser() != null ? saveChanges.getUser().getId() : null)
                    .balance(saveChanges.getBalance())
                    .build();
        } catch (Exception e){
            System.err.printf("Exception caught: %s%n",e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountResponse checkCurrentBalance(String id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("not found any account with id : %s", id)));
            assert account != null : "no account scanned";
            return AccountResponse.builder()
                    .accountNumber(account.getId())
                    .balance(account.getBalance())
                    .build();
        } catch (NoSuchElementException e){
            System.err.println("not found anything");
            return AccountResponse.builder()
                    .accountNumber("not found any account number")
                    .balance(BigDecimal.ZERO)
                    .build();
        } catch (Exception e){
            System.err.printf("Exception caught: %s%n",e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountResponse depositBalance(String id, BigDecimal deposit) {
        try{
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found account with id " + id));

            if(account.getBalance() != null){
                account.setBalance(BigDecimal.ZERO);
            }

            if(account.getBalance() != null) {
                account.setBalance(account.getBalance().add(deposit));
            }
            Account updatedBalance = accountRepository.saveAndFlush(account);

            return AccountResponse.builder()
                    .id(updatedBalance.getId())
                    .accountNumber(updatedBalance.getAccountNumber())
                    .balance(updatedBalance.getBalance())
                    .build();
        } catch (NoSuchElementException e){
            System.err.println("Account not found: " + e.getMessage());
            throw e;
        } catch (Exception e){
            System.err.println("Exceptions caught: " + e.getCause());
            throw e;
        }
    }

    @Override
    public AccountResponse withdrawBalance(String id, BigDecimal withdraw) {
        try{
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found with id: " + id));

            if(account.getBalance() != null){
                account.setBalance(BigDecimal.ZERO);
            }

            if(account.getBalance() != null){
                account.setBalance(account.getBalance().subtract(withdraw));
            }

            Account withdrawalAccount = accountRepository.saveAndFlush(account);

            return AccountResponse.builder()
                    .id(withdrawalAccount.getId())
                    .accountNumber(withdrawalAccount.getAccountNumber())
                    .balance(withdrawalAccount.getBalance())
                    .build();
        } catch (NoSuchElementException e){
            System.err.println("Not found any account with id: " + id + " || with error: " + e.getMessage());
            throw e;
        } catch (Exception e){
            System.err.println("Exception caught: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountResponse transferBalance(String accountId, String transferId, BigDecimal transfer) {
        try {
            Account sender = accountRepository.findById(accountId)
                    .orElseThrow(() -> new NoSuchElementException("not found sender account with id " + accountId));
            Account receiver = accountRepository.findById(transferId)
                    .orElseThrow(() -> new NoSuchElementException("not found receiver account with id " + transferId));

            BigDecimal adminFee = new BigDecimal("3000");
            BigDecimal minimumTransfer = new BigDecimal("5000");

            if (sender.getBalance() != null && receiver.getBalance() != null) {
                if (transfer.compareTo(minimumTransfer) >= 0) {
                    BigDecimal totalDebit = transfer.add(adminFee);

                    // Check if sender has sufficient balance
                    if (sender.getBalance().compareTo(totalDebit) >= 0) {
                        // Perform the balance transfer
                        sender.setBalance(sender.getBalance().subtract(totalDebit));
                        receiver.setBalance(receiver.getBalance().add(transfer));

                        // Save the updated accounts
                        Account updatedSender = accountRepository.saveAndFlush(sender);
                        Account updatedReceiver = accountRepository.saveAndFlush(receiver);

                        recievedTransfer(updatedReceiver);

                        // Return response for the sender account
                        return AccountResponse.builder()
                                .id(updatedSender.getId())
                                .accountNumber(updatedSender.getAccountNumber())
                                .balance(updatedSender.getBalance())
                                .userId(updatedSender.getUser().getId())
                                .build();
                    } else {
                        throw new IllegalArgumentException("Sender has insufficient balance");
                    }
                } else {
                    throw new IllegalArgumentException("Transfer amount must be greater than or equal to " + minimumTransfer);
                }
            } else {
                throw new IllegalStateException("One of the accounts has a null balance");
            }
        } catch (NoSuchElementException e){
            System.err.println("Not found account: " +e.getMessage());
            throw e;
        } catch (Exception e){
            System.err.println("Exception caught: " +e.getMessage());
            throw e;
        }
    }

    @Override
    public void recievedTransfer(Account receiver) {
        logTransferReceipt(receiver);
    }

    private void logTransferReceipt(Account receiver)   {
        System.out.println("Transfer received by account ID: " + receiver.getId() + " at " + LocalDateTime.now());
        System.out.println("New balance: " + receiver.getBalance());
    }


}

```
