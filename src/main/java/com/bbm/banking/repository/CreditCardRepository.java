package com.bbm.banking.repository;

import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    Optional<CreditCard> findByBankAccount(BankAccount account);
    Optional<CreditCard> findByBankAccountAccountNumber(String accountNumber);
    boolean existsByCardNumber(String cardNumber);
    boolean existsByBankAccount(BankAccount account);
}
