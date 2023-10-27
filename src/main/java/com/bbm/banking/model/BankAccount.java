package com.bbm.banking.model;

import com.bbm.banking.exception.BadRequestException;
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
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal accountBalance;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "card_id")
    private CreditCard creditCard;

    @OneToMany
    private List<BankStatement> statements;

    @OneToMany
    @JoinTable(
            name = "bank_account_contacts",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<BankAccount> contacts;

    public void transferTo(BigDecimal amount, BankAccount recipient) {
        if (!this.hasAvailableAmount(amount)) {
            throw new RuntimeException("Você não têm saldo suficiente para realizar a transferência");
        }
        recipient.setAccountBalance(recipient.getAccountBalance().add(amount));
        this.setAccountBalance(this.getAccountBalance().subtract(amount));
    }

    public void deposit(BigDecimal amount) {
        if (this.hasNonAmount(amount)) {
            throw new RuntimeException("Falha na transacção. Você precisa no mínimo de 50.00 MZN para realizar um depósito");
        }
        this.setAccountBalance(this.getAccountBalance().add(amount));
    }

    public void withdraw(BigDecimal amount) {
        if (this.hasAvailableAmount(amount)) {
            if (this.isAmountGreaterThanBalance(amount)) {
                throw new RuntimeException("Falha na transacção. O valor inserido é maior ao valor disponível na sua conta!");
            }
            this.setAccountBalance(this.getAccountBalance().subtract(amount));
        } else {
            throw new RuntimeException("Você não têm dinheiro suficiente para fazer um saque");
        }
    }

    public void pay(BigDecimal amount) {
        if (!this.hasAvailableAmount(amount)) {
            throw new RuntimeException("Você não têm dinheiro suficiente para fazer um pagamento");
        }
        this.setAccountBalance(this.getAccountBalance().subtract(amount));
    }

    public void transferToCreditCard(BigDecimal amount) {
        if (this.hasAvailableAmount(amount)) {
            this.creditCard.setBalance(this.creditCard.getBalance().add(amount));
            this.setAccountBalance(this.getAccountBalance().subtract(amount));
        } else {
            throw new BadRequestException("Você não têm dinheiro suficiente para transferir para o cartão!");
        }
    }

    public void addStatement(BankStatement statement) {
        this.statements.add(statement);
    }

    public void addContact(BankAccount contact) {
        this.contacts.add(contact);
    }

    public boolean hasAvailableAmount(BigDecimal amount) {
        return this.getAccountBalance().compareTo(amount) > 0;
    }

    public boolean hasNonAmount(BigDecimal amount) {
        return (amount == null || amount.equals(BigDecimal.ZERO) || amount.compareTo(new BigDecimal("50")) < 0);
    }

    public boolean isAmountGreaterThanBalance(BigDecimal amount) {
        return amount.compareTo(accountBalance) > 0;
    }
}
