package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.model.dto.request.UserRequest;
import com.mcsoftware.atm.model.dto.response.UserResponse;
import com.mcsoftware.atm.model.entity.User;
import com.mcsoftware.atm.repository.CardRepository;
import com.mcsoftware.atm.repository.UserRepository;
import com.mcsoftware.atm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Override
    public UserResponse create(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .build();
        User savedUser = userRepository.save(user);
        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .build();
    }

    @Override
    public UserResponse getById(String id) {
        return null;
    }

    @Override
    public List<User> getAll(UserRequest userRequest) {
        return null;
    }

    @Override
    public UserResponse update(String id, UserRequest userRequest) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public UserResponse softDelete(String id) {
        return null;
    }

    @Override
    public UserResponse blockUser(String id) {
        return null;
    }

    @Override
    public User validateUser(User user) {
        return null;
    }
}
