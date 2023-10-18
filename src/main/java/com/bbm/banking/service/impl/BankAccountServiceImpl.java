package com.bbm.banking.service.impl;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.request.DepositRequest;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.mapper.Mapper;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.BankStatement;
import com.bbm.banking.repository.BankAccountRepository;
import com.bbm.banking.repository.BankStatementRepository;
import com.bbm.banking.service.BankAccountService;
import com.bbm.banking.service.UserService;
import com.bbm.banking.utils.RandomNumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.bbm.banking.utils.BankUtils.ACCOUNT_CREATED_SUCCESSFULLY;
import static com.bbm.banking.utils.BankUtils.DEPOSIT_CREATED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository accountRepository;
    private final BankStatementRepository statementRepository;
    private final UserService userService;

    @Override
    public HttpResponse createAccount(AccountRequestDto accountRequestDto) {
        var accountNumber = generateAccountNumber();
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(accountNumber)
                .accountBalance(BigDecimal.ZERO)
                .user(userService.createUser(accountRequestDto))
                .build();
        var savedAccount = accountRepository.save(bankAccount);

        return HttpResponse.builder()
                .responseCode(HttpStatus.CREATED.value())
                .responseStatus(HttpStatus.CREATED)
                .responseMessage(ACCOUNT_CREATED_SUCCESSFULLY)
                .createdAt(LocalDateTime.now())
                .accountInfo(AccountInfo.builder()
                        .accountId(savedAccount.getId())
                        .accountNumber(savedAccount.getAccountNumber())
                        .accountBalance(savedAccount.getAccountBalance())
                        .accountOwner(Mapper.mapUserToResponseDto(savedAccount.getUser()))
                        .build())
                .build();
    }

    @Override
    public HttpResponse deposit(DepositRequest request) {
        if (request.getAmount() == null || request.getAmount().equals(BigDecimal.ZERO)) {
            throw new RuntimeException("Falha na transacção. Insira um valor para realizar o depósito");
        }
        BankAccount bankAccount = getAccountById(request.getAccountId());
        bankAccount.deposit(request.getAmount());
        var savedStatement = statementRepository.save(BankStatement
                .createDepositStatement(request.getAmount(), "Depósito realizado", bankAccount));
        bankAccount.getStatements().add(savedStatement);
        var account = accountRepository.save(bankAccount);

        return HttpResponse.builder()
                .responseCode(HttpStatus.OK.value())
                .responseStatus(HttpStatus.OK)
                .responseMessage(DEPOSIT_CREATED_SUCCESSFULLY)
                .createdAt(LocalDateTime.now())
                .accountInfo(AccountInfo.builder()
                        .accountId(account.getId())
                        .accountNumber(account.getAccountNumber())
                        .accountBalance(account.getAccountBalance())
                        .accountOwner(Mapper.mapUserToResponseDto(account.getUser()))
                        .build())
                .build();
    }

    @Override
    public List<AccountInfo> findAllAccounts() {
        List<BankAccount> accounts = accountRepository.findAll();
        return Mapper.mapBankAccountToAccountInfoList(accounts);
    }

    @Override
    public AccountInfo findAccountById(Long accountId) {
        var account = accountRepository.findById(accountId).orElseThrow(() ->
                new RuntimeException("Conta não foi encontrada"));
        return Mapper.mapBankAccountToAccountInfo(account);
    }

    @Override
    public BankAccount getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() ->
                new RuntimeException("Conta não foi encontrada"));
    }

    private String generateAccountNumber() {
        final int NUMBER_OF_DIGITS = 10;
        boolean isAccountNumberInvalid;
        String randomNumber;
        do {
            randomNumber = RandomNumberUtil.generateRandomNumber(NUMBER_OF_DIGITS);
            isAccountNumberInvalid = accountRepository.existsByAccountNumber(randomNumber);
        }while (isAccountNumberInvalid);
        return randomNumber;
    }
}
