package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.AccountRequest;
import com.mcsoftware.atm.model.dto.response.AccountResponse;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppPath.ACCOUNT)
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

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
}
