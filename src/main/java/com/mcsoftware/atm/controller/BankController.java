package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.ATMRequest;
import com.mcsoftware.atm.model.dto.request.BankRequest;
import com.mcsoftware.atm.model.dto.response.ATMResponse;
import com.mcsoftware.atm.model.dto.response.BankResponse;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.model.entity.Bank;
import com.mcsoftware.atm.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                        .message("successfully created atm")
                        .data(bankResponse)
                        .build()
        );
    }

    public ResponseEntity<?> getById(String id){
        BankResponse bankResponse = bankService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully created atm")
                        .data(bankResponse)
                        .build()
        );
    }
    public ResponseEntity<?> getAll(){
        List<Bank> banks = bankService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully created atm")
                        .data(banks)
                        .build()
        );
    }

    public ResponseEntity<?> update(String id,BankRequest bankRequest){
        BankResponse bankResponse = bankService.update(id,bankRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully created atm")
                        .data(bankResponse)
                        .build()
        );
    }
}
