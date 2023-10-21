package com.bbm.banking.service;

import com.bbm.banking.dto.response.StatementResponseDto;

import java.util.List;

public interface BankStatementService {

    List<StatementResponseDto> findAllStatementsByAccountId(Long accountId);

    StatementResponseDto findStatementByIdAndAccountId(Long id, Long accountId);
}
