package com.bbm.banking.service.impl;

import com.bbm.banking.dto.response.StatementResponseDto;
import com.bbm.banking.exception.EntityNotFoundException;
import com.bbm.banking.mapper.Mapper;
import com.bbm.banking.model.BankAccount;
import com.bbm.banking.model.BankStatement;
import com.bbm.banking.model.User;
import com.bbm.banking.repository.BankStatementRepository;
import com.bbm.banking.service.BankAccountService;
import com.bbm.banking.service.BankStatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankStatementServiceImpl implements BankStatementService {

    private final BankStatementRepository statementRepository;
    private final BankAccountService accountService;

    @Override
    @Transactional(readOnly = true)
    public List<StatementResponseDto> findAllStatementsByAccount(User user) {
        var account = accountService.getAccountByUser(user);
        List<BankStatement> statements = statementRepository.findAllByAccountOwner(account);
        return Mapper.mapBankStatementToStatementResponseList(statements);
    }

    @Override
    @Transactional(readOnly = true)
    public StatementResponseDto findStatementByIdAndAccount(Long id, User user) {
        var account = accountService.getAccountByUser(user);
        BankStatement statement = statementRepository.findByIdAndAccountOwner(id, account)
                .orElseThrow(() -> new EntityNotFoundException("Extracto da transferência não foi encontrado"));
        return Mapper.mapBankStatementToStatementResponse(statement);
    }
}
