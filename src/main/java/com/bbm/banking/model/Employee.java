package com.bbm.banking.model;

import com.bbm.banking.model.enums.Role;
import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String employeeIdentifier;
    @Enumerated(EnumType.STRING)
    private Role role;
}
