package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.BranchRequest;
import com.mcsoftware.atm.model.dto.response.BranchGroupingResponse;
import com.mcsoftware.atm.model.dto.response.BranchResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Branch;

import java.util.List;

public interface BranchService {
    BranchResponse create(BranchRequest branchRequest);
    BranchResponse getById(String id);
    List<Branch> getAll();
    BranchResponse update(String id,BranchRequest branchRequest);
    void delete(String id);
    List<ATM> getAllAtm(String branchId);
    BranchResponse bankReference(String branchId);
    BranchGroupingResponse groupedBranchLocations();

    List<ATM> atmValidator(List<ATM> atms);
}
