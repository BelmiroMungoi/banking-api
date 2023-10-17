package com.bbm.banking.service;

import com.bbm.banking.dto.request.UserRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createUser(UserRequestDto userRequest);
}
