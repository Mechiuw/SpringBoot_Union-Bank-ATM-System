package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.TransactionRequest;
import com.mcsoftware.atm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppPath.TRX)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionRequest transactionRequest){}
    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getById(@PathVariable String id){}
    @GetMapping(AppPath.GET_ALL)
    public ResponseEntity<?> getAll(){}
    @PutMapping(AppPath.PUT_BY_ID)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody TransactionRequest transactionRequest){}

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public void delete(@PathVariable String id){}

    @PutMapping(AppPath.SOFT_DELETE_BY_ID)
    public ResponseEntity<?> softDelete(@PathVariable String id){}

    @GetMapping("/all-trx/history")
    public ResponseEntity<?> getAllTransactionHistory(){}
}
