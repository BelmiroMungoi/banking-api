package com.bbm.banking.mapper;

import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.UserResponseDto;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.User;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

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
        for (User user: users) {
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
        return accountInfo;
    }

    public static List<AccountInfo> mapBankAccountToAccountInfoList(List<BankAccount> accounts) {
        List<AccountInfo>  accountInfoList = new ArrayList<>();
        for (BankAccount account: accounts) {
            accountInfoList.add(mapBankAccountToAccountInfo(account));
        }
        return accountInfoList;
    }
}
