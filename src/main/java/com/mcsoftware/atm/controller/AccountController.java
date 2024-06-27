package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.AccountRequest;
import com.mcsoftware.atm.model.dto.response.AccountResponse;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppPath.ACCOUNT)
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> create(AccountRequest accountRequest){
        AccountResponse accountResponse = accountService.create(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully created account")
                        .data(accountResponse)
                        .build());
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getById(@PathVariable String id){
        AccountResponse accountResponse = accountService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch data")
                        .data(accountResponse)
                        .build()
        );
    }

    @GetMapping(AppPath.GET_ALL)
    public ResponseEntity<?> getAll(){
        List<Account> accountList = accountService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully fetch all data")
                        .data(accountList)
                        .build()
        );
    }

    @PutMapping(AppPath.PUT_BY_ID)
    public ResponseEntity<?> update(@PathVariable String id,@RequestBody AccountRequest accountRequest){
        AccountResponse accountResponse = accountService.update(id,accountRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully updates data")
                        .data(accountResponse)
                        .build()
        );
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public void delete(@PathVariable String id){
        accountService.delete(id);
        ResponseEntity.ok();
    }
}
