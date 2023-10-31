package com.bbm.banking.controller;

import com.bbm.banking.dto.request.CardRequestDto;
import com.bbm.banking.dto.response.CreditCardInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.service.CreditCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/creditCard")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<HttpResponse> createCreditCard(@RequestParam("accountNumber")
                                                             String accountNumber) {
        return ResponseEntity.ok(creditCardService.createCreditCard(accountNumber));
    }

    @PutMapping("/invoice")
    public ResponseEntity<HttpResponse> payInvoice(@Valid @RequestBody CardRequestDto cardRequestDto) {
        return ResponseEntity.ok(creditCardService.payInvoice(cardRequestDto));
    }

    @PutMapping("/purchase")
    public ResponseEntity<HttpResponse> creditPurchase(@Valid @RequestBody CardRequestDto cardRequestDto) {
        return ResponseEntity.ok(creditCardService.makeCreditPurchase(cardRequestDto));
    }

    @PutMapping("/purchase/debit")
    public ResponseEntity<HttpResponse> debitPurchase(@Valid @RequestBody CardRequestDto cardRequestDto) {
        return ResponseEntity.ok(creditCardService.makeDebitPurchase(cardRequestDto));
    }

    @GetMapping
    public ResponseEntity<CreditCardInfo> findByAccountId(@RequestParam("accountId") Long accountId) {
        return ResponseEntity.ok(creditCardService.findByBankAccountId(accountId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CreditCardInfo>> findAllCreditCard() {
        return ResponseEntity.ok(creditCardService.findAllCreditCard());
    }
}
