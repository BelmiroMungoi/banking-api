package com.bbm.banking.model;

import com.bbm.banking.model.enums.StatementType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal amount;
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private StatementType statementType;
    @ManyToOne
    private BankAccount accountOwner;
    @ManyToOne
    private BankAccount accountRecipient;

    public static BankStatement createTransferStatement(BigDecimal amount, String message, BankAccount accountOwner,
                                                 BankAccount accountRecipient) {
        return BankStatement.builder()
                .amount(amount)
                .message(message)
                .createdAt(LocalDateTime.now())
                .statementType(StatementType.TRANSFER)
                .accountOwner(accountOwner)
                .accountRecipient(accountRecipient)
                .build();
    }

    public static BankStatement createDepositStatement(BigDecimal amount, String message, BankAccount accountOwner) {
        return BankStatement.builder()
                .amount(amount)
                .message(message)
                .createdAt(LocalDateTime.now())
                .statementType(StatementType.DEPOSIT)
                .accountOwner(accountOwner)
                .accountRecipient(null)
                .build();
    }

    public static BankStatement createWithdrawStatement(BigDecimal amount, String message, BankAccount accountOwner) {
        return BankStatement.builder()
                .amount(amount)
                .message(message)
                .createdAt(LocalDateTime.now())
                .statementType(StatementType.WITHDRAW)
                .accountOwner(accountOwner)
                .accountRecipient(null)
                .build();
    }

    public static BankStatement createDebitCardStatement(BigDecimal amount, String message, BankAccount accountOwner) {
        return BankStatement.builder()
                .amount(amount)
                .message(message)
                .createdAt(LocalDateTime.now())
                .statementType(StatementType.DEBIT_CARD_PAYMENT)
                .accountOwner(accountOwner)
                .accountRecipient(null)
                .build();
    }

    public static BankStatement createCreditCardStatement(BigDecimal amount, String message, BankAccount accountOwner) {
        return BankStatement.builder()
                .amount(amount)
                .message(message)
                .createdAt(LocalDateTime.now())
                .statementType(StatementType.CREDIT_CARD_PAYMENT)
                .accountOwner(accountOwner)
                .accountRecipient(null)
                .build();
    }

    public static BankStatement createInvoicePaymentStatement(BigDecimal amount, String message, BankAccount accountOwner) {
        return BankStatement.builder()
                .amount(amount)
                .message(message)
                .createdAt(LocalDateTime.now())
                .statementType(StatementType.INVOICE_PAYMENT)
                .accountOwner(accountOwner)
                .accountRecipient(null)
                .build();
    }
}
