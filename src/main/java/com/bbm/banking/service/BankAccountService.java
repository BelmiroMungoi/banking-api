package com.bbm.banking.service;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.request.TransactionRequest;
import com.bbm.banking.dto.request.TransferRequest;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.User;

import java.util.List;

public interface BankAccountService {

    HttpResponse createAccount(AccountRequestDto accountRequestDto);

    HttpResponse transfer(TransferRequest transferRequest, User user);

    HttpResponse deposit(TransactionRequest transactionRequest, User user);

    HttpResponse withdraw(TransactionRequest withdrawRequest, User user);

    HttpResponse transferToCreditCard(TransactionRequest transactionRequest, User user);

    List<AccountInfo> findAllAccounts();

    AccountInfo findAccountById(Long accountId);

    BankAccount getAccountById(Long accountId);

    BankAccount getAccountByAccountNumber(String accountNumber);
    BankAccount getAccountByUser(User user);
}
