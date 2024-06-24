package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.BranchRequest;
import com.mcsoftware.atm.model.dto.response.BranchGroupingResponse;
import com.mcsoftware.atm.model.dto.response.BranchResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Branch;

import java.util.List;

public interface BranchService {
    //CRUD SERVICE
    BranchResponse create(BranchRequest branchRequest);
    BranchResponse getById(String id);
    List<Branch> getAll();
    BranchResponse update(String id,BranchRequest branchRequest);
    void delete(String id);

    //BUSINESS LOGIC SERVICE
    List<ATM> getAllAtm(String branchId);
    BranchResponse bankReference(String branchId);
    List<BranchGroupingResponse> groupedBranchLocations();

    //UTIL CLASS / HELPER
    List<ATM> atmValidator(List<ATM> atms);
}
