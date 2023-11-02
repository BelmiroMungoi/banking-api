package com.bbm.banking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountDetails {

    private Long accountId;
    private String accountNumber;
    private String accountName;
    private BigDecimal accountBalance;

}
