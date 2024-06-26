package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.UserRequest;
import com.mcsoftware.atm.model.dto.response.UserResponse;
import com.mcsoftware.atm.model.entity.User;

import java.util.List;

public interface UserService {
    UserResponse create(UserRequest userRequest);
    UserResponse getById(String id);
    List<User> getAll();
    UserResponse update(String id, UserRequest userRequest);
    void delete(String id);
    UserResponse softDelete(String id);
    UserResponse blockUser(String id);
    User validateUser(User user);
}
