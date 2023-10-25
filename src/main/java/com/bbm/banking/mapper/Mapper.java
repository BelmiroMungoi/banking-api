package com.bbm.banking.mapper;

import com.bbm.banking.dto.response.*;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.BankStatement;
import com.bbm.banking.model.CreditCard;
import com.bbm.banking.model.User;
import com.bbm.banking.model.enums.StatementType;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private static final String withoutValue = "-";

    public static UserResponseDto mapUserToResponseDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setUserId(user.getId());
        responseDto.setFirstname(user.getFirstname());
        responseDto.setLastname(user.getLastname());
        responseDto.setEmail(user.getEmail());
        responseDto.setPhoneNumber(user.getPhoneNumber());
        responseDto.setBirthdate(user.getBirthdate());
        return responseDto;
    }

    public static List<UserResponseDto> mapUserToResponseListDto(List<User> users) {
        List<UserResponseDto> responseDtoList = new ArrayList<>();
        for (User user : users) {
            responseDtoList.add(mapUserToResponseDto(user));
        }
        return responseDtoList;
    }

    public static AccountInfo mapBankAccountToAccountInfo(BankAccount account) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountId(account.getId());
        accountInfo.setAccountNumber(account.getAccountNumber());
        accountInfo.setAccountBalance(account.getAccountBalance());
        accountInfo.setAccountOwner(mapUserToResponseDto(account.getUser()));
        accountInfo.setContacts(mapBankAccountToContactResponseList(account.getContacts()));
        if (account.getCreditCard() != null) {
            accountInfo.setCreditCardInfo(mapCreditCardToCreditCardInfo(account.getCreditCard()));
        }
        return accountInfo;
    }

    public static List<AccountInfo> mapBankAccountToAccountInfoList(List<BankAccount> accounts) {
        List<AccountInfo> accountInfoList = new ArrayList<>();
        for (BankAccount account : accounts) {
            accountInfoList.add(mapBankAccountToAccountInfo(account));
        }
        return accountInfoList;
    }

    public static ContactResponseDto mapBankAccountToContactResponse(BankAccount account) {
        ContactResponseDto contactResponse = new ContactResponseDto();
        contactResponse.setAccountId(account.getId());
        contactResponse.setAccountName(account.getUser().getFirstname() + " " + account.getUser().getLastname());
        contactResponse.setAccountNumber(account.getAccountNumber());
        return contactResponse;
    }

    public static List<ContactResponseDto> mapBankAccountToContactResponseList(List<BankAccount> accounts) {
        List<ContactResponseDto> contactResponseList = new ArrayList<>();
        for (BankAccount account : accounts) {
            contactResponseList.add(mapBankAccountToContactResponse(account));
        }
        return contactResponseList;
    }

    public static StatementResponseDto mapBankStatementToStatementResponse(BankStatement statement) {
        StatementResponseDto statementResponse = new StatementResponseDto();
        statementResponse.setDate(statement.getCreatedAt());
        statementResponse.setMessage(statement.getMessage());
        statementResponse.setAmount(statement.getAmount());
        statementResponse.setType(statement.getStatementType());
        statementResponse.setAccountOwner(statement.getAccountOwner().getAccountNumber());
        statementResponse.setAccountOwnerName(statement.getAccountOwner().getUser().getFirstname());
        if (statement.getAccountRecipient() != null) {
            statementResponse.setAccountRecipient(statement.getAccountRecipient().getAccountNumber());
            statementResponse.setAccountRecipientName(statement.getAccountRecipient().getUser().getFirstname() + " " +
                    statement.getAccountRecipient().getUser().getLastname());
        } else {
            statementResponse.setAccountRecipient(withoutValue);
            statementResponse.setAccountRecipientName(withoutValue);
        }
        return statementResponse;
    }

    public static List<StatementResponseDto> mapBankStatementToStatementResponseList(List<BankStatement> statements) {
        List<StatementResponseDto> statementResponseList = new ArrayList<>();
        for (BankStatement statement : statements) {
            statementResponseList.add(mapBankStatementToStatementResponse(statement));
        }
        return statementResponseList;
    }

    public static CreditCardInfo mapCreditCardToCreditCardInfo(CreditCard creditCard) {
        CreditCardInfo creditCardInfo = new CreditCardInfo();
        creditCardInfo.setCardNumber(creditCard.getCardNumber());
        creditCardInfo.setBalance(creditCard.getBalance());
        creditCardInfo.setInvoice(creditCard.getInvoice());
        return creditCardInfo;
    }

    public static List<CreditCardInfo> mapCreditCardToCreditCardInfoList(List<CreditCard> creditCards) {
        List<CreditCardInfo> creditCardInfoList = new ArrayList<>();
        for (CreditCard creditCard : creditCards) {
            creditCardInfoList.add(mapCreditCardToCreditCardInfo(creditCard));
        }
        return creditCardInfoList;
    }
}
