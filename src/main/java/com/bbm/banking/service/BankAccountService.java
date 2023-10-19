package com.bbm.banking.service;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.request.DepositRequest;
import com.bbm.banking.dto.request.TransferRequest;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.model.BankAccount;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {

    HttpResponse createAccount(AccountRequestDto accountRequestDto);
    HttpResponse transfer(TransferRequest transferRequest);
    HttpResponse deposit(DepositRequest depositRequest);
    List<AccountInfo> findAllAccounts();
    AccountInfo findAccountById(Long accountId);
    BankAccount getAccountById(Long accountId);
    BankAccount getAccountByAccountNumber(String accountNumber);
}
