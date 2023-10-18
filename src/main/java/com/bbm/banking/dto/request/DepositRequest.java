package com.bbm.banking.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DepositRequest {

    private Long accountId;
    private BigDecimal amount;
}
