package com.bbm.banking.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransferRequest {

    private Long accountSenderId;
    private String accountRecipient;
    private BigDecimal amount;
}
