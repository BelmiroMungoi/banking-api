package com.bbm.banking.repository;

import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    boolean existsByAccountNumber(String accountNumber);

    BankAccount findByIdAndContacts(Long id, BankAccount contact);

    Optional<BankAccount> findBankAccountByAccountNumber(String accountNumber);

    Optional<BankAccount> findBankAccountByUser(User user);
}
