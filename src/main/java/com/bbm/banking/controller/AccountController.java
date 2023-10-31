package com.bbm.banking.controller;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.request.TransactionRequest;
import com.bbm.banking.dto.request.TransferRequest;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final BankAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createAccount(@Valid @RequestBody AccountRequestDto requestDto) {
        return new ResponseEntity<>(accountService.createAccount(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AccountInfo>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAllAccounts());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountInfo> findAccountById(@PathVariable("accountId") Long accountId) {
        return ResponseEntity.ok(accountService.findAccountById(accountId));
    }

    @PutMapping("/transfer")
    public ResponseEntity<HttpResponse> transferMoney(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.ok(accountService.transfer(request));
    }

    @PutMapping("/deposit")
    public ResponseEntity<HttpResponse> depositMoney(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(accountService.deposit(request));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<HttpResponse> withdrawMoney(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(accountService.withdraw(request));
    }

    @PutMapping("/transferToCredit")
    public ResponseEntity<HttpResponse> transferToCredit(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(accountService.transferToCreditCard(request));
    }
}
