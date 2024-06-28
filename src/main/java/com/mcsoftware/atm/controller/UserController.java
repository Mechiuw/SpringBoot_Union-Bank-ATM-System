package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.UserRequest;
import com.mcsoftware.atm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppPath.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserRequest userRequest){}

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getById(@PathVariable String id){}

    @GetMapping(AppPath.GET_ALL)
    public ResponseEntity<?> getAll(){}

    @PutMapping(AppPath.PUT_BY_ID)
    public ResponseEntity<?> update(@PathVariable String id,@RequestBody UserRequest userRequest){}

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public void delete(@PathVariable String id){}

    @PutMapping(AppPath.SOFT_DELETE_BY_ID)
    public ResponseEntity<?> softDelete(@PathVariable String id){}

    @PutMapping("/block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable String id){}
}
