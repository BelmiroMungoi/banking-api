package com.bbm.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionRequest {

    @NotBlank
    private Long accountId;

    @NotBlank
    private BigDecimal amount;
}
