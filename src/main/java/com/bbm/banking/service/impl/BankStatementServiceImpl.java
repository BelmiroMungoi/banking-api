package com.bbm.banking.service.impl;

import com.bbm.banking.dto.response.StatementResponseDto;
import com.bbm.banking.exception.EntityNotFoundException;
import com.bbm.banking.mapper.Mapper;
import com.bbm.banking.model.BankStatement;
import com.bbm.banking.repository.BankStatementRepository;
import com.bbm.banking.service.BankStatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankStatementServiceImpl implements BankStatementService {

    private final BankStatementRepository statementRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StatementResponseDto> findAllStatementsByAccountId(Long accountId) {
        List<BankStatement> statements = statementRepository.findAllByAccountOwnerId(accountId);
        return Mapper.mapBankStatementToStatementResponseList(statements);
    }

    @Override
    @Transactional(readOnly = true)
    public StatementResponseDto findStatementByIdAndAccountId(Long id, Long accountId) {
        BankStatement statement = statementRepository.findByIdAndAccountOwnerId(id, accountId)
                .orElseThrow(() -> new EntityNotFoundException("Extracto da transferência não foi encontrado"));
        return Mapper.mapBankStatementToStatementResponse(statement);
    }
}
