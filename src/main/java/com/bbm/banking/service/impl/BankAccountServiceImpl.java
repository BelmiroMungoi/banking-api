package com.bbm.banking.service.impl;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.mapper.Mapper;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.repository.BankAccountRepository;
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

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository accountRepository;
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
                .responseCode(HttpStatus.CREATED)
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
    public List<AccountInfo> findAllAccounts() {
        List<BankAccount> account = accountRepository.findAll();
        return Mapper.mapBankAccountToAccountInfoList(account);
    }

    private static int generateAccountNumber() {
        final int NUMBER_OF_DIGITS = 8;
        String randomNumber = RandomNumberUtil.generateRandomNumber(NUMBER_OF_DIGITS);
        return Integer.parseInt(randomNumber);
    }
}
