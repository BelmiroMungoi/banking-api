package com.bbm.banking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private Long id;
    private LocalDateTime moment;
    private LocalDateTime dateLimit;
    private BigDecimal income;
    private BigDecimal amount;
    private String job;

    @ManyToOne
    private BankAccount accountOwner;
    @ManyToOne
    private Employee employee;
    @OneToMany
    private List<BankStatement> statements;
}
