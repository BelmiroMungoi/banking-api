package com.bbm.banking.model;

import com.bbm.banking.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String employeeIdentifier;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne
    private User user;
}
