package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.UserRequest;
import com.mcsoftware.atm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppPath.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    public ResponseEntity<?> create(UserRequest userRequest){}
    public ResponseEntity<?> getById(UserRequest userRequest){}
    public ResponseEntity<?> getAll(UserRequest userRequest){}
    public ResponseEntity<?> update(UserRequest userRequest){}
    public void delete(UserRequest userRequest){}
    public ResponseEntity<?> softDelete(String id){}
    public ResponseEntity<?> blockUser(String id){}
}
