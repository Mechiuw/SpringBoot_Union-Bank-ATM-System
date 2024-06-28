package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.BranchRequest;
import com.mcsoftware.atm.model.dto.response.BankResponse;
import com.mcsoftware.atm.model.dto.response.BranchGroupingResponse;
import com.mcsoftware.atm.model.dto.response.BranchResponse;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Branch;
import com.mcsoftware.atm.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AppPath.BRANCH)
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;
    public ResponseEntity<?> create(BranchRequest branchRequest){
        BranchResponse branchResponse = branchService.create(branchRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully created branch")
                        .data(branchResponse)
                        .build()
        );
    }
    public ResponseEntity<?> getById(String id){
        BranchResponse branchResponse = branchService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch branch")
                        .data(branchResponse)
                        .build()
        );
    }
    public ResponseEntity<?> getAll(){
        List<Branch> branches = branchService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch all branch")
                        .data(branches)
                        .build()
        );
    }

    public ResponseEntity<?> update(String id,BranchRequest branchRequest){
        BranchResponse branchResponse = branchService.update(id,branchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully update branch")
                        .data(branchResponse)
                        .build()
        );
    }
    public void delete(String id){
        branchService.delete(id);
        ResponseEntity.ok();
    }

    public ResponseEntity<?> getAllAtm(String id){
        List<ATM> atms = branchService.getAllAtm(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully update branch")
                        .data(atms)
                        .build()
        );
    }

    public ResponseEntity<?> bankReference(String branchId){
        BranchResponse branchResponse = branchService.bankReference(branchId);
        return ResponseEntity.status(HttpStatus.OK).body(
            CommonResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Successfully fetch bank reference")
                    .data(branchResponse)
                    .build()
        );
    }

    public ResponseEntity<?> groupedBranchLocations(String id){
        List<BranchGroupingResponse> branchGroupingResponses = branchService.groupedBranchLocations();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully update branch")
                        .data(branchGroupingResponses)
                        .build()
        );
    }
}
