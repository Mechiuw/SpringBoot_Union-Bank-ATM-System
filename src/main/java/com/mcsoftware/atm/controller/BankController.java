package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.BankServicePath;
import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.BankRequest;
import com.mcsoftware.atm.model.dto.response.AccountResponse;
import com.mcsoftware.atm.model.dto.response.BankResponse;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.model.entity.Bank;
import com.mcsoftware.atm.model.entity.Branch;
import com.mcsoftware.atm.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(AppPath.BANK)
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BankRequest bankRequest){
        BankResponse bankResponse = bankService.create(bankRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully created bank")
                        .data(bankResponse)
                        .build()
        );
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getById(@PathVariable String id){
        BankResponse bankResponse = bankService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch data")
                        .data(bankResponse)
                        .build()
        );
    }

    @GetMapping(AppPath.GET_ALL)
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

    @PutMapping(AppPath.PUT_BY_ID)
    public ResponseEntity<?> update(@PathVariable String id,@RequestBody BankRequest bankRequest){
        BankResponse bankResponse = bankService.update(id,bankRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully update bank")
                        .data(bankResponse)
                        .build()
        );
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public void delete(@PathVariable String id){
        bankService.delete(id);
        ResponseEntity.ok();
    }

    @GetMapping(BankServicePath.LIST_ALL_BRANCH)
    public ResponseEntity<?> listAllBranch(@PathVariable String id){
        List<Branch> branches = bankService.listAllBranch(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch all branches")
                        .data(branches)
                        .build()
        );
    }

    @GetMapping(BankServicePath.LIST_ALL_BRANCH_ATM)
    public ResponseEntity<?> listAllBranchAtms(@PathVariable String bankId, @RequestParam String branchId){
        List<ATM> atms = bankService.listAllBranchAtms(bankId,branchId);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch all atms")
                        .data(atms)
                        .build()
        );
    }

    @PutMapping(BankServicePath.DEPOSIT_ATM)
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
                            .message("Successfully deposit to atm")
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


    @PutMapping(BankServicePath.WITHDRAWAL_ATM)
    public ResponseEntity<?> withdrawalFromAtm(
            @RequestParam String bankId,
            @RequestParam String branchId,
            @RequestParam String atmId,
            @RequestParam BigDecimal depositFromRepo)
    {
        try {
            BankResponse bankResponse = bankService.withdrawalFromAtm(bankId, branchId, atmId, depositFromRepo);
            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Successfully withdrawal from atm")
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

    @PutMapping(BankServicePath.REQ_DEPOSIT)
    public ResponseEntity<?> requestToDeposit(@PathVariable String id,@RequestParam BigDecimal amount){
        try {
            AccountResponse accountResponse = bankService.requestToDeposit(id, amount);
            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Successfully request deposit")
                            .data(accountResponse)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonResponse.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to request deposit: " + e.getMessage())
                            .build()
            );
        }
    }

    @PutMapping(BankServicePath.REQ_WITHDRAWAL)
    public ResponseEntity<?> requestToWithdraw(@PathVariable String id,@RequestParam BigDecimal amount){
        try {
            AccountResponse accountResponse = bankService.requestToWithdraw(id, amount);
            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Successfully request withdrawal")
                            .data(accountResponse)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonResponse.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to request withdrawal: " + e.getMessage())
                            .build()
            );
        }
    }

}
