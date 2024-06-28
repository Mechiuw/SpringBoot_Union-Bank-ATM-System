package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.model.dto.request.UserRequest;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.model.dto.response.UserResponse;
import com.mcsoftware.atm.model.entity.User;
import com.mcsoftware.atm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppPath.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully created account")
                        .data(userResponse)
                        .build());
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getById(@PathVariable String id){
        UserResponse userResponse = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch data")
                        .data(userResponse)
                        .build()
        );
    }

    @GetMapping(AppPath.GET_ALL)
    public ResponseEntity<?> getAll(){
        List<User> userList = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("successfully fetch data")
                        .data(userList)
                        .build()
        );
    }

    @PutMapping(AppPath.PUT_BY_ID)
    public ResponseEntity<?> update(@PathVariable String id,@RequestBody UserRequest userRequest){}

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public void delete(@PathVariable String id){}

    @PutMapping(AppPath.SOFT_DELETE_BY_ID)
    public ResponseEntity<?> softDelete(@PathVariable String id){}

    @PutMapping("/block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable String id){}
}
