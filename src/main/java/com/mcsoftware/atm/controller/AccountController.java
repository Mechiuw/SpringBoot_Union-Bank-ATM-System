package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppPath.ACCOUNT)
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    public ResponseEntity
}
