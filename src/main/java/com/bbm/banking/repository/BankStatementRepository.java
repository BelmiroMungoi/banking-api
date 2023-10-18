package com.bbm.banking.repository;

import com.bbm.banking.model.BankStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {
}
