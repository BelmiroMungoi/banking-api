package com.bbm.banking.service.impl;

import com.bbm.banking.dto.request.CardRequestDto;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.exception.BadRequestException;
import com.bbm.banking.exception.EntityNotFoundException;
import com.bbm.banking.mapper.Mapper;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.BankStatement;
import com.bbm.banking.model.CreditCard;
import com.bbm.banking.repository.BankAccountRepository;
import com.bbm.banking.repository.BankStatementRepository;
import com.bbm.banking.repository.CreditCardRepository;
import com.bbm.banking.service.BankAccountService;
import com.bbm.banking.service.CreditCardService;
import com.bbm.banking.utils.RandomNumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.bbm.banking.utils.BankUtils.*;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final BankAccountService accountService;
    private final BankAccountRepository accountRepository;
    private final CreditCardRepository creditCardRepository;
    private final BankStatementRepository statementRepository;

    @Override
    public HttpResponse createCreditCard(String accountNumber) {
        var savedAccount = accountService.getAccountByAccountNumber(accountNumber);
        if (creditCardRepository.existsByBankAccount(savedAccount)) {
            throw new BadRequestException("Essa conta já possui um cartão de crédito!!!");
        }
        var generateCardNumber = generateCardNumber();
        CreditCard creditCard = CreditCard.builder()
                .cardNumber(generateCardNumber)
                .balance(new BigDecimal("10000.00"))
                .invoice(BigDecimal.ZERO)
                .bankAccount(savedAccount)
                .build();
        savedAccount.setCreditCard(creditCard);
        creditCardRepository.save(creditCard);
        accountRepository.save(savedAccount);

        return httpResponse(HttpStatus.CREATED,
                CREDIT_CARD_CREATED_SUCCESSFULLY,
                savedAccount);
    }

    @Override
    public HttpResponse payInvoice(CardRequestDto cardRequestDto) {
        CreditCard creditCard = findByAccountNumber(cardRequestDto.getAccountNumber());
        if (creditCard.isAmountLowerThanInvoice(cardRequestDto.getAmount())) {
            throw new BadRequestException("Falha na transacção. O valor inserido é menor ao da factura!");
        }
        creditCard.payInvoice(cardRequestDto.getAmount());
        var statement = statementRepository.save(BankStatement.createInvoicePaymentStatement(cardRequestDto.getAmount().negate(),
                "Factura Paga", creditCard.getBankAccount()));
        creditCard.addStatement(statement);
        creditCardRepository.save(creditCard);
        accountRepository.save(creditCard.getBankAccount());

        return httpResponse(HttpStatus.OK,
                INVOICE_PAID_SUCCESSFULLY,
                creditCard.getBankAccount());
    }

    @Override
    public HttpResponse makeCreditPurchase(CardRequestDto cardRequestDto) {
        CreditCard creditCard = findByAccountNumber(cardRequestDto.getAccountNumber());
        creditCard.makeCreditPurchase(cardRequestDto.getAmount());
        var statement = statementRepository.save(BankStatement.createCreditCardStatement(cardRequestDto.getAmount(),
                "Compra Realizada", creditCard.getBankAccount()));
        creditCard.addStatement(statement);
        creditCardRepository.save(creditCard);

        return httpResponse(HttpStatus.OK,
                CREDIT_PURCHASE_SUCCESSFULLY,
                creditCard.getBankAccount());
    }

    @Override
    public CreditCard findByBankAccount(BankAccount account) {
        return creditCardRepository.findByBankAccount(account).orElseThrow(() ->
                new EntityNotFoundException("Cartão de crédito não existe!!!"));
    }

    @Override
    public CreditCard findByAccountNumber(String accountNumber) {
        return creditCardRepository.findByBankAccountAccountNumber(accountNumber).orElseThrow(() ->
                new EntityNotFoundException("Essa conta não possui um cartão de crédito!!!"));
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
