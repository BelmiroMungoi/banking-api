package com.bbm.banking.service.impl;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.request.DepositRequest;
import com.bbm.banking.dto.request.TransferRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.bbm.banking.utils.BankUtils.*;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository accountRepository;
    private final BankStatementRepository statementRepository;
    private final UserService userService;

    @Override
    @Transactional
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
    @Transactional
    public HttpResponse transfer(TransferRequest transferRequest) {
        var accountSender = getAccountById(transferRequest.getAccountSenderId());
        var accountRecipient = getAccountByAccountNumber(transferRequest.getAccountRecipient());
        var amountToTransfer = transferRequest.getAmount();
        if (accountSender.equals(accountRecipient)) {
            throw new RuntimeException("Hah hah hah. Você não pode transferir dinheiro para você mesmo espertinho!");
        }
        if (amountToTransfer == null || amountToTransfer.compareTo(new BigDecimal("20")) < 0) {
            throw new RuntimeException("Falha na transacção. Você só pode realizar uma transferência com valores maiores á 20.00 MZN");
        }
        accountSender.transferTo(amountToTransfer, accountRecipient);
        var recipientStatement = statementRepository.save(BankStatement
                .createTransferStatement(amountToTransfer, "Transferência Realizada",
                        accountSender, accountRecipient));
        var senderStatement = statementRepository.save(BankStatement
                .createTransferStatement(amountToTransfer.negate(), "Transferência Realizada",
                        accountSender, accountRecipient));

        accountRecipient.addStatement(recipientStatement);
        accountSender.addStatement(senderStatement);

        accountSender.addContact(accountRecipient);
        //accountRecipient.addContact(accountSender);

        accountRepository.save(accountRecipient);
        accountRepository.save(accountSender);

        return HttpResponse.builder()
                .responseCode(HttpStatus.OK.value())
                .responseStatus(HttpStatus.OK)
                .responseMessage(TRANSFER_CREATED_SUCCESSFULLY)
                .createdAt(LocalDateTime.now())
                .accountInfo(AccountInfo.builder()
                        .accountId(accountSender.getId())
                        .accountNumber(accountSender.getAccountNumber())
                        .accountBalance(accountSender.getAccountBalance())
                        .accountOwner(Mapper.mapUserToResponseDto(accountSender.getUser()))
                        .build())
                .build();
    }

    @Override
    @Transactional
    public HttpResponse deposit(DepositRequest request) {
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
    @Transactional(readOnly = true)
    public List<AccountInfo> findAllAccounts() {
        List<BankAccount> accounts = accountRepository.findAll();
        return Mapper.mapBankAccountToAccountInfoList(accounts);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountInfo findAccountById(Long accountId) {
        var account = accountRepository.findById(accountId).orElseThrow(() ->
                new RuntimeException("Oops. Conta não foi encontrada"));
        return Mapper.mapBankAccountToAccountInfo(account);
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccount getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() ->
                new RuntimeException("Oops. Conta não foi encontrada!"));
    }

    @Override
    public BankAccount getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findBankAccountByAccountNumber(accountNumber).orElseThrow(() ->
                new RuntimeException("Oops. Conta não foi encontrada!"));
    }

    private String generateAccountNumber() {
        final int NUMBER_OF_DIGITS = 10;
        boolean isAccountNumberInvalid;
        String randomNumber;
        do {
            randomNumber = RandomNumberUtil.generateRandomNumber(NUMBER_OF_DIGITS);
            isAccountNumberInvalid = accountRepository.existsByAccountNumber(randomNumber);
        } while (isAccountNumberInvalid);
        return randomNumber;
    }
}
