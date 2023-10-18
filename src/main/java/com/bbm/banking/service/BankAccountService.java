package com.bbm.banking.service;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;

import java.util.List;

public interface BankAccountService {

    HttpResponse createAccount(AccountRequestDto accountRequestDto);
    List<AccountInfo> findAllAccounts();
}
