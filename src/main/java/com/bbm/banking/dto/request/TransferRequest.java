package com.bbm.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransferRequest {

    @NotBlank
    private String accountRecipient;
    private BigDecimal amount;
}
