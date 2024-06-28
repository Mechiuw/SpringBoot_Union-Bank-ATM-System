package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.TransactionRequest;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.model.dto.response.TransactionResponse;
import com.mcsoftware.atm.model.entity.Transaction;
import com.mcsoftware.atm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppPath.TRX)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionRequest transactionRequest){
        TransactionResponse transactionResponse = transactionService.create(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully created account")
                        .data(transactionResponse)
                        .build());
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getById(@PathVariable String id){
        TransactionResponse transactionResponse = transactionService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch data")
                        .data(transactionResponse)
                        .build()
        );
    }
    @GetMapping(AppPath.GET_ALL)
    public ResponseEntity<?> getAll(){
        List<Transaction> transactionResponse = transactionService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch data")
                        .data(transactionResponse)
                        .build()
        );
    }
    @PutMapping(AppPath.PUT_BY_ID)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody TransactionRequest transactionRequest){
        TransactionResponse transactionResponse = transactionService.update(id,transactionRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully update data")
                        .data(transactionResponse)
                        .build()
        );

    }

    @PutMapping(AppPath.SOFT_DELETE_BY_ID)
    public ResponseEntity<?> softDelete(@PathVariable String id){
        TransactionResponse transactionResponse = transactionService.softDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully soft delete data")
                        .data(transactionResponse)
                        .build()
        );
    }

    @GetMapping("/all-trx/history")
    public ResponseEntity<?> getAllTransactionHistory(){
        List<TransactionResponse> list = transactionService.getAllTransactionHistory();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch data")
                        .data(list)
                        .build()
        );
    }
}
