package com.bbm.banking.dto.request;

import com.bbm.banking.model.BankAccount;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CardRequestDto {

    private BigDecimal amount;
    private String accountNumber;
}
