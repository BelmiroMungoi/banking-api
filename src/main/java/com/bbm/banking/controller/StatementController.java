package com.bbm.banking.controller;

import com.bbm.banking.dto.response.StatementResponseDto;
import com.bbm.banking.exception.handler.StandardErrorResponse;
import com.bbm.banking.model.User;
import com.bbm.banking.service.BankStatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statement")
@Tag(name = "Statement Management")
public class StatementController {

    private final BankStatementService statementService;

    @Operation(
            summary = "An endpoint to get all the statements",
            description = "This endpoint will find all the statements for a specific account by passing the account id",
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
                                            title = "Authenticate",
                                            example = "You need to authenticate to use this endpoint"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<StatementResponseDto>> findAllStatements(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(statementService.findAllStatementsByAccount(user));
    }

    @Operation(
            summary = "An endpoint to get a specific statement",
            description = "This endpoint will find a specific statement for a specific account " +
                    "by passing the statement id and the account id",
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
                                            title = "Authenticate",
                                            example = "You need to authenticate to use this endpoint"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "NOT_FOUND",
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = StandardErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<StatementResponseDto> findStatementById(@PathVariable("id") Long id,
                                                                  @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(statementService.findStatementByIdAndAccount(id, user));
    }
}
