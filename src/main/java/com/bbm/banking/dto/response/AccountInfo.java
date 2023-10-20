package com.bbm.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {

    private Long accountId;
    private String accountNumber;
    private BigDecimal accountBalance;
    private UserResponseDto accountOwner;
    private List<ContactResponseDto> contacts;
}
