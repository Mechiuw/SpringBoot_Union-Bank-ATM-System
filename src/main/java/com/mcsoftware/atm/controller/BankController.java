package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.ATMRequest;
import com.mcsoftware.atm.model.dto.request.BankRequest;
import com.mcsoftware.atm.model.dto.response.ATMResponse;
import com.mcsoftware.atm.model.dto.response.BankResponse;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Bank;
import com.mcsoftware.atm.model.entity.Branch;
import com.mcsoftware.atm.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(AppPath.BANK)
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;

    public ResponseEntity<?> create(BankRequest bankRequest){
        BankResponse bankResponse = bankService.create(bankRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully created bank")
                        .data(bankResponse)
                        .build()
        );
    }

    public ResponseEntity<?> getById(String id){
        BankResponse bankResponse = bankService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch data")
                        .data(bankResponse)
                        .build()
        );
    }
    public ResponseEntity<?> getAll(){
        List<Bank> banks = bankService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch all bank")
                        .data(banks)
                        .build()
        );
    }

    public ResponseEntity<?> update(String id,BankRequest bankRequest){
        BankResponse bankResponse = bankService.update(id,bankRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully update bank")
                        .data(bankResponse)
                        .build()
        );
    }

    public void delete(String id){
        bankService.delete(id);
        ResponseEntity.ok();
    }

    public ResponseEntity<?> listAllBranch(String id){
        List<Branch> branches = bankService.listAllBranch(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully created atm")
                        .data(branches)
                        .build()
        );
    }

    public ResponseEntity<?> listAllBranch(String bankId,String branchId){
        List<ATM> atms = bankService.listAllBranchAtms(bankId,branchId);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully created atm")
                        .data(atms)
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<?> depositToAtm(
            @RequestParam String bankId,
            @RequestParam String branchId,
            @RequestParam String atmId,
            @RequestParam BigDecimal depositFromRepo)
    {
        try {
            BankResponse bankResponse = bankService.depositToAtm(bankId, branchId, atmId, depositFromRepo);
            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Successfully updated bank")
                            .data(bankResponse)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonResponse.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to update bank: " + e.getMessage())
                            .build()
            );
        }
    }


}
