package com.bbm.banking.dto.request;

import com.bbm.banking.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class AccountRequestDto {

    @NotBlank
    @Size(min = 3, max = 60)
    private String firstname;

    @NotBlank
    @Size(min = 3, max = 60)
    private String lastname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 9, max = 12)
    private String phoneNumber;

    @NotBlank
    @Size(min = 5, max = 30)
    private String username;

    @NotBlank
    @Size(min = 5, max = 30)
    private String password;

    @NotBlank
    private String province;

    @NotBlank
    private String district;

    @NotBlank
    private String street;

    @NotBlank
    private Integer houseNumber;

    @NotBlank
    private Integer zipCode;

    @NotBlank
    private LocalDate birthdate;

    @NotBlank
    private Role role;

}
