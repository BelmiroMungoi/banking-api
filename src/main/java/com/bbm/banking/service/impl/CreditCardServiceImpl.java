package com.bbm.banking.service.impl;

import com.bbm.banking.dto.request.CardRequestDto;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.exception.EntityNotFoundException;
import com.bbm.banking.mapper.Mapper;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.CreditCard;
import com.bbm.banking.repository.BankAccountRepository;
import com.bbm.banking.repository.CreditCardRepository;
import com.bbm.banking.service.BankAccountService;
import com.bbm.banking.service.CreditCardService;
import com.bbm.banking.utils.RandomNumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.bbm.banking.utils.BankUtils.CREDIT_CARD_CREATED_SUCCESSFULLY;
import static com.bbm.banking.utils.BankUtils.INVOICE_PAID_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final BankAccountService accountService;
    private final BankAccountRepository accountRepository;
    private final CreditCardRepository creditCardRepository;

    @Override
    public HttpResponse createCreditCard(String accountNumber) {
        var generateCardNumber = generateCardNumber();
        var savedAccount = accountService.getAccountByAccountNumber(accountNumber);
        CreditCard creditCard = CreditCard.builder()
                .cardNumber(generateCardNumber)
                .balance(new BigDecimal("10000.00"))
                .invoice(BigDecimal.ZERO)
                .bankAccount(savedAccount)
                .build();
        creditCardRepository.save(creditCard);
        return httpResponse(HttpStatus.CREATED,
                CREDIT_CARD_CREATED_SUCCESSFULLY,
                savedAccount);
    }

    @Override
    public HttpResponse payInvoice(CardRequestDto cardRequestDto) {
        CreditCard creditCard = findByBankAccount(cardRequestDto.getAccount());
        creditCard.payInvoice(cardRequestDto.getAmount());
        creditCardRepository.save(creditCard);
        accountRepository.save(creditCard.getBankAccount());
        return httpResponse(HttpStatus.OK,
                INVOICE_PAID_SUCCESSFULLY,
                cardRequestDto.getAccount());
    }

    @Override
    public CreditCard findByBankAccount(BankAccount account) {
        return creditCardRepository.findByBankAccount(account).orElseThrow(() ->
                new EntityNotFoundException("Cartão de crédito não existe"));
    }

    private String generateCardNumber() {
        final int NUMBER_OF_DIGITS = 16;
        boolean isAccountNumberInvalid;
        String randomNumber;
        do {
            randomNumber = RandomNumberUtil.generateRandomNumber(NUMBER_OF_DIGITS);
            isAccountNumberInvalid = creditCardRepository.existsByCardNumber(randomNumber);
        } while (isAccountNumberInvalid);
        return randomNumber;
    }

    private static HttpResponse httpResponse(HttpStatus status, String message, BankAccount account) {
        return HttpResponse.builder()
                .responseCode(status.value())
                .responseStatus(status)
                .responseMessage(message)
                .createdAt(LocalDateTime.now())
                .accountInfo(AccountInfo.builder()
                        .accountId(account.getId())
                        .accountNumber(account.getAccountNumber())
                        .accountBalance(account.getAccountBalance())
                        .accountOwner(Mapper.mapUserToResponseDto(account.getUser()))
                        .build())
                .build();
    }
}
