package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.model.dto.request.UserRequest;
import com.mcsoftware.atm.model.dto.response.UserResponse;
import com.mcsoftware.atm.model.entity.User;
import com.mcsoftware.atm.repository.CardRepository;
import com.mcsoftware.atm.repository.UserRepository;
import com.mcsoftware.atm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

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
        User validatedUser = validateUser(user);
        User savedUser = userRepository.save(validatedUser);
        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .build();
    }

    @Override
    public UserResponse getById(String id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found any user"));
        if(findUser != null){
            return UserResponse.builder()
                    .id(findUser.getId())
                    .name(findUser.getName())
                    .email(findUser.getEmail())
                    .phoneNumber(findUser.getPhoneNumber())
                    .build();
        } else {
            return UserResponse.builder()
                    .id("not found user id")
                    .name("not found user name")
                    .email("not found user email")
                    .phoneNumber("not found user phone number")
                    .build();
        }
    }

    @Override
    public List<User> getAll() {
        if(!userRepository.findAll().isEmpty()){
            return userRepository.findAll();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public UserResponse update(String id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found any user"));
        if(userRequest != null){
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            User validatedUser = validateUser(user);
            User updatedUser = userRepository.saveAndFlush(validatedUser);

            return UserResponse.builder()
                    .id(updatedUser.getId())
                    .name(updatedUser.getName())
                    .email(updatedUser.getEmail())
                    .phoneNumber(updatedUser.getPhoneNumber())
                    .build();
        } else {
            return UserResponse.builder()
                    .id("not found user id")
                    .name("not found user name")
                    .email("not found user email")
                    .phoneNumber("not found user phone number")
                    .build();
        }
    }

    @Override
    public void delete(String id) {
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found any user"));
            userRepository.delete(user);
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public UserResponse softDelete(String id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found any user"));
        if(findUser != null){
            findUser.setName("DELETED");
            findUser.setEmail("DELETED");
            findUser.setPhoneNumber("DELETED");
            User updatedUser = userRepository.saveAndFlush(findUser);
            return UserResponse.builder()
                    .id(updatedUser.getId())
                    .name(updatedUser.getName())
                    .email(updatedUser.getEmail())
                    .phoneNumber(updatedUser.getPhoneNumber())
                    .build();
        } else {
            return UserResponse.builder()
                    .id("not found user id")
                    .name("not found user name")
                    .email("not found user email")
                    .phoneNumber("not found user phone number")
                    .build();
        }
    }

    @Override
    public UserResponse blockUser(String id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found any user"));
        if(findUser != null){
            findUser.setName("BLOCKED");
            findUser.setEmail("BLOCKED");
            findUser.setPhoneNumber("BLOCKED");
            User updatedUser = userRepository.saveAndFlush(findUser);
            return UserResponse.builder()
                    .id(updatedUser.getId())
                    .name(updatedUser.getName())
                    .email(updatedUser.getEmail())
                    .phoneNumber(updatedUser.getPhoneNumber())
                    .build();
        } else {
            return UserResponse.builder()
                    .id("not found user id")
                    .name("not found user name")
                    .email("not found user email")
                    .phoneNumber("not found user phone number")
                    .build();
        }
    }

    @Override
    public User validateUser(User user) {
        if (user.getName() == null) {
            throw new IllegalArgumentException("Exception caught : user name is null");
        }
        if (user.getEmail() == null) {
            throw new IllegalArgumentException("Exception caught : user name is null");
        }
        if (user.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Exception caught : user name is null");
        } else {
            return user;
        }
    }
}
