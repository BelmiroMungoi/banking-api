package com.bbm.banking.service;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.request.TransactionRequest;
import com.bbm.banking.dto.request.TransferRequest;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    HttpResponse createAccount(AccountRequestDto accountRequestDto);
    HttpResponse transfer(TransferRequest transferRequest);
    HttpResponse deposit(TransactionRequest transactionRequest);
    HttpResponse withdraw(TransactionRequest withdrawRequest);
    List<AccountInfo> findAllAccounts();
    AccountInfo findAccountById(Long accountId);
    BankAccount getAccountById(Long accountId);
    BankAccount getAccountByAccountNumber(String accountNumber);
}
