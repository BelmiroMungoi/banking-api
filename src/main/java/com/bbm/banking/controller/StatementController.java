package com.bbm.banking.controller;

import com.bbm.banking.dto.response.StatementResponseDto;
import com.bbm.banking.service.BankStatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statement")
public class StatementController {

    private final BankStatementService statementService;

    @GetMapping
    public ResponseEntity<List<StatementResponseDto>> findAllStatements(@RequestParam("accountId") Long accountId) {
        return ResponseEntity.ok(statementService.findAllStatementsByAccountId(accountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatementResponseDto> findStatementById(@PathVariable("id") Long id,
                                                                  @RequestParam("accountId") Long accountId) {
        return ResponseEntity.ok(statementService.findStatementByIdAndAccountId(id, accountId));
    }
}
