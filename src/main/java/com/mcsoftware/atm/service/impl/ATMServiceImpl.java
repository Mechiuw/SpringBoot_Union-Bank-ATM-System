package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.model.dto.request.ATMRequest;
import com.mcsoftware.atm.model.dto.response.ATMResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Branch;
import com.mcsoftware.atm.repository.ATMRepository;
import com.mcsoftware.atm.repository.BranchRepository;
import com.mcsoftware.atm.service.ATMService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ATMServiceImpl implements ATMService {
    private final BranchRepository branchRepository;
    private final ATMRepository atmRepository;
    private final BigDecimal leastBalance = new BigDecimal("1000000");


    public void checkBalance(BigDecimal balance){
        if(balance == null || balance.equals(BigDecimal.ZERO)){
            throw new IllegalArgumentException("can't make atm if cash balance is null o zero");
        }
        if(balance.compareTo(leastBalance) < 0){
            throw new IllegalArgumentException("can't make atm if cash balance below the least balance: Rp" + leastBalance);
        }
    }

    @Override
    public ATMResponse create(ATMRequest atmRequest) {
        try {
            checkBalance(atmRequest.getCashBalance());

            Branch branch = branchRepository.findById(atmRequest.getBranch().getId())
                    .orElseThrow(() -> new NoSuchElementException("not found branch with id: " + atmRequest.getBranch().getId()));

            ATM atm = ATM.builder()
                    .location(atmRequest.getLocation())
                    .cashBalance(atmRequest.getCashBalance())
                    .branch(branch)
                    .build();

            ATM savedAtm = atmRepository.save(atm);

            return ATMResponse.builder()
                    .id(savedAtm.getId())
                    .branch(savedAtm.getBranch().getId())
                    .cashBalance(savedAtm.getCashBalance())
                    .location(savedAtm.getLocation())
                    .build();
        } catch (NoSuchElementException e){
            System.err.println("not found any branch: " + e.getMessage());
            throw e;
        } catch (Exception e){
            System.err.println("Exception caught: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public ATMResponse getById(String id) {
        try {
            ATM atm = atmRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found atm with id: " + id));

            Branch branch = branchRepository.findById(atm.getBranch().getId())
                    .orElseThrow(() -> new NoSuchElementException("not found branch with id: " + atm.getBranch().getId()));

            return ATMResponse.builder()
                    .id(atm.getId())
                    .location(atm.getLocation())
                    .branch(branch.getId())
                    .cashBalance(atm.getCashBalance())
                    .build();
        } catch (NoSuchElementException e){
            System.err.println("not found any trace : " + e.getMessage() );
            throw e;
        } catch (Exception e){
            System.err.println("Exception caught: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ATM> getAll() {
        try {
            List<ATM> atmList = atmRepository.findAll();
            if (atmList.isEmpty()) {
                return Collections.emptyList();
            }
            return atmList;
        }  catch (Exception e){
            System.err.println("Exception caught: " + e.getMessage());
            throw new RuntimeException("Error Occurred to atm: ",e);
        }
    }

    @Override
    public ATMResponse update(String id, ATMRequest atmRequest) {
        try{
            ATM atm = atmRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found atm with id" + id));


            atm.setBranch(atmRequest.getBranch());
            atm.setLocation(atmRequest.getLocation());
            atm.setCashBalance(atmRequest.getCashBalance());

            ATM saved = atmRepository.saveAndFlush(atm);

            return ATMResponse.builder()
                    .id(saved.getId())
                    .branch(saved.getBranch().getId())
                    .location(saved.getLocation())
                    .cashBalance(saved.getCashBalance())
                    .build();
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        try{
            ATM deleted = atmRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("not found atm"));
            atmRepository.delete(deleted);
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public ATMResponse checkCashBalance(String id) {
        return null;
    }

    @Override
    public ATMResponse withdraw(BigDecimal withdrawal) {
        return null;
    }

    @Override
    public ATMResponse deposit(BigDecimal deposit) {
        return null;
    }
}
