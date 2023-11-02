package com.bbm.banking.service;

import com.bbm.banking.dto.request.TransactionRequest;
import com.bbm.banking.dto.response.CreditCardInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.model.CreditCard;
import com.bbm.banking.model.User;

import java.util.List;

public interface CreditCardService {

    HttpResponse createCreditCard(User user);

    HttpResponse payInvoice(TransactionRequest cardRequestDto, User user);

    HttpResponse makeCreditPurchase(TransactionRequest cardRequestDto, User user);

    HttpResponse makeDebitPurchase(TransactionRequest cardRequestDto, User user);

    CreditCardInfo findByAccount(User user);

    List<CreditCardInfo> findAllCreditCard();

    CreditCard findByAccountNumber(String accountNumber);

}
