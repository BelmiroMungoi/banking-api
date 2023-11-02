package com.bbm.banking.repository;

import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.BankStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {

    List<BankStatement> findAllByAccountOwner(BankAccount account);

    Optional<BankStatement> findByIdAndAccountOwner(Long id, BankAccount account);
}
