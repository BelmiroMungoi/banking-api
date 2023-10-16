package com.bbm.banking.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private int accountNumber;
    @Column(nullable = false)
    private BigDecimal accountBalance;
    @OneToOne
    private User user;
    @OneToOne
    @JoinColumn(name = "creditCard_id")
    private CreditCard creditCard;
    @OneToMany
    private List<BankStatement> statements;
    @OneToMany
    private List<BankAccount> contacts;

    public void transferTo(BigDecimal amount, BankAccount recipient) {
        recipient.setAccountBalance(recipient.getAccountBalance().add(amount));
        this.setAccountBalance(this.getAccountBalance().subtract(amount));
    }

    public void deposit(BigDecimal amount) {
        this.setAccountBalance(this.getAccountBalance().add(amount));
    }

    public void withdraw(BigDecimal amount) {
        this.setAccountBalance(this.getAccountBalance().subtract(amount));
    }

    public void pay(BigDecimal amount) {
        if (!this.hasAvailableAmount(amount)) {
            throw new RuntimeException("Você não têm dinheiro suficiente para fazer um pagamento");
        }
        this.setAccountBalance(this.getAccountBalance().subtract(amount));
    }

    public void addContact(BankAccount contact) {
        this.contacts.add(contact);
    }

    public boolean hasAvailableAmount(BigDecimal amount) {
        return this.getAccountBalance().compareTo(amount) > 0;
    }
}
