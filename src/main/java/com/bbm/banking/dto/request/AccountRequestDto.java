package com.bbm.banking.dto.request;

import com.bbm.banking.model.enums.Role;
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
    private String province;
    private String district;
    private String street;
    private Integer houseNumber;
    private Integer zipCode;
    private LocalDate birthdate;
    private Role role;

}
