package com.bbm.banking.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime moment;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateLimit;

    private BigDecimal income;
    private BigDecimal amount;
    private String job;

    @ManyToOne
    @JoinColumn(name = "accountOwner_id")
    private BankAccount accountOwner;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany
    private List<BankStatement> statements;

}
