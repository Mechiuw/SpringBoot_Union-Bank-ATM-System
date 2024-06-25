package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.repository.*;
import com.mcsoftware.atm.service.TransactionService;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = RollbackException.class)
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final ATMRepository atmRepository;
    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;
    private final BankRepository bankRepository;
    private final TrxFeeRepository trxFeeRepository;
    private final UserRepository userRepository;
}
