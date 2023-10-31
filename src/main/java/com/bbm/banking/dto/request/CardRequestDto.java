package com.bbm.banking.dto.request;

import com.bbm.banking.model.BankAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CardRequestDto {

    @NotBlank
    private BigDecimal amount;

    @NotBlank
    private String accountNumber;
}
