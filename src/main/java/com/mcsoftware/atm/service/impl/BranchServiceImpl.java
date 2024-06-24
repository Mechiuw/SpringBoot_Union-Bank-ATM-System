package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.model.dto.request.BranchRequest;
import com.mcsoftware.atm.model.dto.response.BranchGroupingResponse;
import com.mcsoftware.atm.model.dto.response.BranchResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Bank;
import com.mcsoftware.atm.model.entity.Branch;
import com.mcsoftware.atm.repository.BankRepository;
import com.mcsoftware.atm.repository.BranchRepository;
import com.mcsoftware.atm.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final BankRepository bankRepository;
    @Override
    public BranchResponse create(BranchRequest branchRequest) {
        try {
            Bank bank = bankRepository.findById(branchRequest.getBank().getId())
                    .orElseThrow(() -> new NoSuchElementException("not found any bank"));
            List<ATM> validatedAtm = atmValidator(branchRequest.getAtmList());
            Branch branch = Branch.builder()
                    .name(branchRequest.getName())
                    .location(branchRequest.getLocation())
                    .bank(bank)
                    .atms(validatedAtm)
                    .build();

            Branch branchInit = branchRepository.save(branch);
            return BranchResponse.builder()
                    .id(branchInit.getId())
                    .name(branchInit.getName())
                    .location(branchInit.getLocation())
                    .bank(branchInit.getBank().getId())
                    .atmList(branchInit.getAtms())
                    .build();
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public BranchResponse getById(String id) {
        try {
            Branch branch = branchRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found any branch with id : " + id));
            Bank bank = bankRepository.findById(branch.getBank().getId())
                    .orElseThrow(() -> new NoSuchElementException("not found any bank with id" + branch.getBank().getId()));
            if(branch != null){
                throw new IllegalArgumentException("violated branch table regulations : branch is null");
            } else {
                return BranchResponse.builder()
                        .id(branch.getId())
                        .name(branch.getName())
                        .location(branch.getLocation())
                        .bank(bank.getId())
                        .build();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Branch> getAll() {
        return null;
    }

    @Override
    public BranchResponse update(String id, BranchRequest branchRequest) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<ATM> getAllAtm(String branchId) {
        return null;
    }

    @Override
    public BranchResponse bankReference(String branchId) {
        return null;
    }

    @Override
    public BranchGroupingResponse groupedBranchLocations() {
        return null;
    }

    @Override
    public List<ATM> atmValidator(List<ATM> atms) {
        if(atms.isEmpty()){
            throw new IllegalArgumentException("empty atm list is forbidden");
        }
        if(atms.size() < 5){
            throw new IllegalArgumentException("regulations initialization branch : add atm for least 5 ");
        }
        return atms;
    }
}
