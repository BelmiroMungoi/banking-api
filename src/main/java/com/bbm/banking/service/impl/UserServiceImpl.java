package com.bbm.banking.service.impl;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.exception.BadRequestException;
import com.bbm.banking.model.Address;
import com.bbm.banking.model.User;
import com.bbm.banking.repository.UserRepository;
import com.bbm.banking.security.JWTService;
import com.bbm.banking.service.AddressService;
import com.bbm.banking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(AccountRequestDto userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BadRequestException("Já existe uma conta registada com esse email!");
        }
        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new BadRequestException("Oops. Já existe uma conta registada com esse número de celular");
        }
        var savedAddress = addressService.saveAddress(userRequest);
        User userToBeSaved = User.builder()
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .birthdate(userRequest.getBirthdate())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .isUserEnabled(true)
                .isUserNonLocked(true)
                .address(savedAddress)
                .role(userRequest.getRole())
                .build();
        return userRepository.save(userToBeSaved);

    }
}
