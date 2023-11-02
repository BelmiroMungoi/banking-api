package com.bbm.banking.controller;

import com.bbm.banking.dto.request.TransactionRequest;
import com.bbm.banking.dto.response.CreditCardInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.exception.handler.StandardErrorResponse;
import com.bbm.banking.model.User;
import com.bbm.banking.service.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/creditCard")
@Tag(name = "Credit Card Management")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Operation(
            summary = "This endpoint is used to create a new credit card",
            description = "This endpoint allows the creation of a credit card associated with a specific account." +
                    " To create a credit card, you need to provide the account number as a parameter.",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403",
                            content = @Content(
                                    schema = @Schema(
                                            title = "Authentication",
                                            example = "To use this endpoint, authentication is required." +
                                                    " Only authenticated users or applications with the appropriate" +
                                                    " credentials are permitted to access and utilize this endpoint."
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "BAD_REQUEST",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = StandardErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<HttpResponse> createCreditCard(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(creditCardService.createCreditCard(user));
    }

    @Operation(
            summary = "This endpoint is designed for processing payments towards an invoice.",
            description = "This endpoint is responsible for processing payments for existing invoices. " +
                    "To initiate a payment, you need to provide the necessary payment data in the request body. " +
                    "If a matching invoice is found, the payment will be applied to settle the invoice.",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403",
                            content = @Content(
                                    schema = @Schema(
                                            title = "Authentication",
                                            example = "To use this endpoint, authentication is required." +
                                                    " Only authenticated users or applications with the appropriate" +
                                                    " credentials are permitted to access and utilize this endpoint."
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "BAD_REQUEST",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = StandardErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @PutMapping("/invoice")
    public ResponseEntity<HttpResponse> payInvoice(@Valid @RequestBody TransactionRequest cardRequestDto,
                                                   @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(creditCardService.payInvoice(cardRequestDto, user));
    }

    @Operation(
            summary = "This endpoint facilitates the process of making a purchase",
            description = "This endpoint is used to initiate a credit card purchase transaction. " +
                    "It enables users or applications to make purchases using a credit card stored in the system.",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403",
                            content = @Content(
                                    schema = @Schema(
                                            title = "Authentication",
                                            example = "To use this endpoint, authentication is required." +
                                                    " Only authenticated users or applications with the appropriate" +
                                                    " credentials are permitted to access and utilize this endpoint."
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "BAD_REQUEST",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = StandardErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @PutMapping("/purchase")
    public ResponseEntity<HttpResponse> creditPurchase(@Valid @RequestBody TransactionRequest cardRequestDto,
                                                       @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(creditCardService.makeCreditPurchase(cardRequestDto, user));
    }

    @Operation(
            summary = "This endpoint is for initiating a debit purchase transaction.",
            description = "This endpoint is designed to initiate a debit card purchase transaction, " +
                    "allowing users or applications to make purchases using a card.",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403",
                            content = @Content(
                                    schema = @Schema(
                                            title = "Authentication",
                                            example = "To use this endpoint, authentication is required." +
                                                    " Only authenticated users or applications with the appropriate" +
                                                    " credentials are permitted to access and utilize this endpoint."
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "BAD_REQUEST",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = StandardErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @PutMapping("/purchase/debit")
    public ResponseEntity<HttpResponse> debitPurchase(@Valid @RequestBody TransactionRequest cardRequestDto,
                                                      @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(creditCardService.makeDebitPurchase(cardRequestDto, user));
    }

    @Operation(
            summary = "This endpoint allows you to find a credit card",
            description = "This endpoint allows you to retrieve a specific credit card by providing the associated " +
                    "account ID as a parameter.",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403",
                            content = @Content(
                                    schema = @Schema(
                                            title = "Authentication",
                                            example = "To use this endpoint, authentication is required." +
                                                    " Only authenticated users or applications with the appropriate" +
                                                    " credentials are permitted to access and utilize this endpoint."
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "BAD_REQUEST",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = StandardErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<CreditCardInfo> findByAccount(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(creditCardService.findByAccount(user));
    }

    @Operation(
            summary = "This endpoint is designed to retrieve a list of all credit cards",
            description = "This endpoint is used to retrieve a list of all credit cards stored in the database. " +
                    "It allows you to access and view information about all credit cards currently on record.",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403",
                            content = @Content(
                                    schema = @Schema(
                                            title = "Authentication",
                                            example = "To use this endpoint, authentication is required." +
                                                    " Only authenticated users or applications with the appropriate" +
                                                    " credentials are permitted to access and utilize this endpoint."
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "BAD_REQUEST",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = StandardErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<CreditCardInfo>> findAllCreditCard() {
        return ResponseEntity.ok(creditCardService.findAllCreditCard());
    }
}
