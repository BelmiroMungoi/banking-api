package com.bbm.banking.service;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.model.User;

public interface UserService {

    User createUser(AccountRequestDto userRequest);
}
