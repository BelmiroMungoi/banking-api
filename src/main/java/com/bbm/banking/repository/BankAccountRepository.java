package com.bbm.banking.repository;

import com.bbm.banking.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    boolean existsByAccountNumber(String accountNumber);
    Optional<BankAccount> findBankAccountByAccountNumber(String accountNumber);
}
