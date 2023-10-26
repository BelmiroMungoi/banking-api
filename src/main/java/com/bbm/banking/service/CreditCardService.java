package com.bbm.banking.service;

import com.bbm.banking.dto.request.CardRequestDto;
import com.bbm.banking.dto.response.CreditCardInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.CreditCard;

public interface CreditCardService {

    HttpResponse createCreditCard(String accountNumber);

    HttpResponse payInvoice(CardRequestDto cardRequestDto);

    HttpResponse makeCreditPurchase(CardRequestDto cardRequestDto);
    HttpResponse makeDebitPurchase(CardRequestDto cardRequestDto);

    CreditCardInfo findByBankAccountId(Long accountId);

    CreditCard findByAccountNumber(String accountNumber);

}
