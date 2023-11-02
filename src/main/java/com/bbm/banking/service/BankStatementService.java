package com.bbm.banking.service;

import com.bbm.banking.dto.response.StatementResponseDto;
import com.bbm.banking.model.User;

import java.util.List;

public interface BankStatementService {

    List<StatementResponseDto> findAllStatementsByAccount(User user);

    StatementResponseDto findStatementByIdAndAccount(Long id, User user);
}
