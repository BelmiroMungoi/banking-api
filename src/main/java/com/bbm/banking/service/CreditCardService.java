package com.bbm.banking.service;

import com.bbm.banking.dto.request.CardRequestDto;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.CreditCard;

public interface CreditCardService {

    HttpResponse createCreditCard(String accountNumber);
    HttpResponse payInvoice(CardRequestDto cardRequestDto);
    CreditCard findByBankAccount(BankAccount account);
}