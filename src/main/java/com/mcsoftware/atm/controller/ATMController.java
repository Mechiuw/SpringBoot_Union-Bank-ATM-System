package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.ATMServicePath;
import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.ATMRequest;
import com.mcsoftware.atm.model.dto.response.ATMResponse;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.model.entity.ATM;
import com.mcsoftware.atm.service.ATMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppPath.ATM)
@RequiredArgsConstructor
public class ATMController {
    private final ATMService atmService;

    @PostMapping
    public ResponseEntity<?> create(ATMRequest atmRequest){
        ATMResponse atmResponse = atmService.create(atmRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully created atm")
                        .data(atmResponse)
                        .build()
        );
    }
    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getById(@PathVariable String id){
        ATMResponse atmResponse = atmService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully created atm")
                        .data(atmResponse)
                        .build()
        );
    }
    @GetMapping(AppPath.GET_ALL)
    public ResponseEntity<?> getAll(){
        List<ATM> atms = atmService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully created atm")
                        .data(atms)
                        .build()
        );
    }
    @PutMapping(AppPath.PUT_BY_ID)
    public ResponseEntity<?> update(@PathVariable String id,@RequestBody ATMRequest atmRequest){
        ATMResponse atmResponse = atmService.update(id,atmRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully created atm")
                        .data(atmResponse)
                        .build());
    }
    @DeleteMapping(AppPath.DELETE_BY_ID)
    public void delete(String id){
        atmService.delete(id);
        ResponseEntity.ok();
    }

    @GetMapping(ATMServicePath.CHECK_CASH_BALANCE)
    public ResponseEntity<?> checkCashBalance(@PathVariable String id){
        ATMResponse atmResponse = atmService.checkCashBalance(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully created atm")
                        .data(atmResponse)
                        .build());
    }
}
