package com.bbm.banking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserRequestDto {

    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private LocalDate birthdate;

}
