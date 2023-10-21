package com.bbm.banking.dto.response;

import com.bbm.banking.model.enums.StatementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementResponseDto {

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:MM:ss")
    private LocalDateTime date;
    private String message;
    private BigDecimal amount;
    private StatementType type;
    private String accountOwner;
    private String accountOwnerName;
    private String accountRecipient;
    private String accountRecipientName;
}
