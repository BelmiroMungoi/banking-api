package com.bbm.banking.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class AccountRequestDto {

    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private LocalDate birthdate;
}
