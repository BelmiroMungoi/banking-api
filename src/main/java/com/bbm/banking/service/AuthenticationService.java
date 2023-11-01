package com.bbm.banking.service;

import com.bbm.banking.dto.request.AuthenticationRequest;
import com.bbm.banking.dto.response.AuthenticationResponse;
import com.bbm.banking.model.Token;
import com.bbm.banking.model.User;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
    void saveUserToken(User user, String token);
}
