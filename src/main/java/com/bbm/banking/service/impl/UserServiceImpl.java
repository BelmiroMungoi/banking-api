package com.bbm.banking.service.impl;

import com.bbm.banking.dto.request.UserRequestDto;
import com.bbm.banking.model.User;
import com.bbm.banking.repository.UserRepository;
import com.bbm.banking.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(UserRequestDto userRequest) {
        User userToBeSaved = User.builder()
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .birthdate(userRequest.getBirthdate())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .isUserEnabled(true)
                .isUserNonLocked(true)
                .build();
        userRepository.save(userToBeSaved);
    }
}
