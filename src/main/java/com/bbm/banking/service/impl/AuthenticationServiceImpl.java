package com.bbm.banking.service.impl;

import com.bbm.banking.dto.request.AuthenticationRequest;
import com.bbm.banking.dto.response.AuthenticationResponse;
import com.bbm.banking.model.Token;
import com.bbm.banking.model.User;
import com.bbm.banking.model.enums.TokenType;
import com.bbm.banking.repository.TokenRepository;
import com.bbm.banking.repository.UserRepository;
import com.bbm.banking.security.JWTService;
import com.bbm.banking.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public void saveUserToken(User user, String token) {
        var jwtToken = Token.builder()
                .user(user)
                .createdDate(LocalDateTime.now())
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(jwtToken);
    }
}
