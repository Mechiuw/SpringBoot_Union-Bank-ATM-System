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

import java.util.*;
import java.util.stream.Collectors;

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
        try {
            List<Branch> branches = branchRepository.findAll();
            if(branches.isEmpty()){
                System.err.println("violation branch table regulations : branch is empty");
                return Collections.emptyList();
            } else {
                return branches;
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public BranchResponse update(String id, BranchRequest branchRequest) {
        try {
            Branch branch = branchRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Branch not found with id: " + id));

            if (!branchRequest.getName().equals(branch.getName())) {
                branch.setName(branchRequest.getName());
            }
            if (!branchRequest.getLocation().equals(branch.getLocation())) {
                branch.setLocation(branchRequest.getLocation());
            }

            if (branchRequest.getAtmList() != null && !branchRequest.getAtmList().isEmpty()) {
                branch.setAtms(branchRequest.getAtmList());
            }

            Branch savedBranch = branchRepository.saveAndFlush(branch);

            return BranchResponse.builder()
                    .id(savedBranch.getId())
                    .name(savedBranch.getName())
                    .location(savedBranch.getLocation())
                    .bank(savedBranch.getBank().getId())
                    .atmList(savedBranch.getAtms())
                    .build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        try{
            Branch branch = branchRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found any branch with id " +id));
            branchRepository.delete(branch);
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ATM> getAllAtm(String branchId) {
        try {
            Branch branch = branchRepository.findById(branchId)
                    .orElseThrow(() -> new NoSuchElementException("not found any branch with id"));
            if (!branch.getAtms().isEmpty()) {
                return branch.getAtms();
            } else {
                System.err.println("empty atm in one branch");
                return Collections.emptyList();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public BranchResponse bankReference(String branchId) {
        try {
            Branch branch = branchRepository.findById(branchId)
                    .orElseThrow(() -> new NoSuchElementException("not found any branch"));
            if (branch.getBank() != null) {
                return BranchResponse.builder()
                        .name(branch.getName())
                        .location(branch.getLocation())
                        .bank(branch.getBank().getId())
                        .build();
            } else {
                return BranchResponse.builder()
                        .name("not found any branch name")
                        .location("not found any branch location")
                        .bank("not found any branch's bank")
                        .build();
            }
        } catch (Exception e){
            System.err.println("Exception caught: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BranchGroupingResponse> groupedBranchLocations() {
        try {
            List<Branch> branches = branchRepository.findAll();

            Map<String, List<Branch>> groupedByLocations = branches.stream()
                    .collect(Collectors.groupingBy(Branch::getLocation));

            List<BranchGroupingResponse> responses = groupedByLocations.entrySet().stream()
                    .map(entry -> BranchGroupingResponse.builder()
                            .branchId(entry.getValue().get(0).getId())
                            .locations(entry.getKey())
                            .branches(entry.getValue())
                            .build()).toList();

            return responses;
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
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
