package com.bbm.banking.controller;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.dto.request.TransactionRequest;
import com.bbm.banking.dto.request.TransferRequest;
import com.bbm.banking.dto.response.AccountInfo;
import com.bbm.banking.dto.response.HttpResponse;
import com.bbm.banking.exception.handler.StandardErrorResponse;
import com.bbm.banking.model.User;
import com.bbm.banking.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account Management")
public class AccountController {

    private final BankAccountService accountService;

    @Operation(
            summary = "This endpoint is used to create a new bank account",
            description = "This endpoint facilitates the creation of a bank account by receiving the " +
                    "necessary data within the request body.",
            responses = {
                    @ApiResponse(
                            description = "Successful Operation",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403",
                            content = @Content
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
    @PostMapping("/")
    public ResponseEntity<HttpResponse> createAccount(@Valid @RequestBody AccountRequestDto requestDto) {
        return new ResponseEntity<>(accountService.createAccount(requestDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "This endpoint is used to get all the accounts",
            description = "This endpoint allows you to retrieve a list of all accounts within the system",
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
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<AccountInfo>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAllAccounts());
    }

    @Operation(
            summary = "This endpoint is used to get an account",
            description = "This endpoint allows you to retrieve an account by providing its ID as a parameter.",
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
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountInfo> findAccountById(@PathVariable("accountId") Long accountId) {
        return ResponseEntity.ok(accountService.findAccountById(accountId));
    }

    @Operation(
            summary = "This endpoint is used to transfer money",
            description = "This endpoint enables you to transfer money to another account by providing " +
                    "the necessary transaction details in the request body.",
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
    @PutMapping("/transfer")
    public ResponseEntity<HttpResponse> transferMoney(@Valid @RequestBody TransferRequest request,
                                                      @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.transfer(request, user));
    }

    @Operation(
            summary = "This endpoint is used to deposit money",
            description = "This endpoint enables you to deposit money in your account by providing " +
                    "the necessary transaction details in the request body.",
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
    @PutMapping("/deposit")
    public ResponseEntity<HttpResponse> depositMoney(@Valid @RequestBody TransactionRequest request,
                                                     @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.deposit(request, user));
    }

    @Operation(
            summary = "This endpoint is used to withdraw money",
            description = "This endpoint enables you to withdraw your money in your account by providing " +
                    "the necessary transaction details in the request body.",
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
    @PutMapping("/withdraw")
    public ResponseEntity<HttpResponse> withdrawMoney(@Valid @RequestBody TransactionRequest request,
                                                      @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.withdraw(request, user));
    }

    @Operation(
            summary = "This endpoint is used to transfer money to your card",
            description = "This endpoint enables you to transfer money in your account to your credit card" +
                    " by providing the necessary transaction details in the request body.",
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
    @PutMapping("/transferToCredit")
    public ResponseEntity<HttpResponse> transferToCredit(@Valid @RequestBody TransactionRequest request,
                                                         @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.transferToCreditCard(request, user));
    }
}
