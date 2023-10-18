package com.bbm.banking.controller;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.service.BankAccountService;
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
    public ResponseEntity<HttpResponse> createAccount(@RequestBody AccountRequestDto requestDto) {
        return new ResponseEntity<>(accountService.createAccount(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AccountInfo>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAllAccounts());
    }
}
