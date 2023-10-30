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
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private BigDecimal invoice;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "creditCard")
    private List<BankStatement> statements;

    public void payInvoice(BigDecimal amount) {
        if (hasInvoice()) {
            if (bankAccount.hasAvailableAmount(amount)) {
                if (isAmountGreaterThanInvoice(amount)) {
                    throw new BadRequestException("Falha na transacção. O valor inserido é maior ao da factura");
                }
                invoice = invoice.subtract(amount);
                bankAccount.setAccountBalance(bankAccount.getAccountBalance()
                        .subtract(amount));
            } else {
                throw new BadRequestException("Você não tem saldo o suficiente na sua conta");
            }
        } else {
            throw new BadRequestException("O seu cartão não possui nenhuma factura");
        }
    }

    public void makeCreditPurchase(BigDecimal amount) {
        if (this.hasBalance()) {
            if (this.isAmountGreaterThanBalance(amount)) {
                throw new BadRequestException("Falha na transacção. O valor inserido é maior ao valor disponível no cartão!");
            }
            balance = balance.subtract(amount);
            invoice = invoice.add( new BigDecimal("10.00"));
        } else {
            throw new BadRequestException("O seu cartão não possui saldo suficiente para realizar a compra!");
        }
    }

    public void makeDebitPurchase(BigDecimal amount) {
        this.bankAccount.pay(amount);
    }

    public void addStatement(BankStatement statement) {
        this.statements.add(statement);
    }

    public boolean isAmountGreaterThanBalance(BigDecimal amount) {
        return amount.compareTo(balance) > 0;
    }

    public boolean isAmountGreaterThanInvoice(BigDecimal amount) {
        return amount.compareTo(invoice) > 0;
    }

    public boolean isAmountLowerThanInvoice(BigDecimal amount) {
        return amount.compareTo(invoice) < 0;
    }

    public boolean hasBalance() {
        return !balance.equals(BigDecimal.ZERO);
    }

    public boolean hasInvoice() {
        return !(this.invoice.equals(BigDecimal.ZERO) && this.invoice.equals(new BigDecimal("0.00")));
    }
}
