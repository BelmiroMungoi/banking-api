package com.bbm.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardInfo {

    private String cardNumber;
    private String accountName;
    private BigDecimal balance;
    private BigDecimal invoice;
}
