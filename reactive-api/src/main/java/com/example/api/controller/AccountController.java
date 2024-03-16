package com.example.api.controller;

import com.example.api.service.AccountService;
import com.example.core.dto.request.AccountRequest;
import com.example.core.dto.response.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    @Operation(summary = "Get all account")
    @ApiResponse(responseCode = "200", description = "have account", content = @Content(schema = @Schema(implementation = AccountRequest.class)))
    @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = ChangeSetPersister.NotFoundException.class)))
    @PostMapping("/fetch/{id}")
    public Mono<List<AccountResponse>> getBookByIsbn(@PathVariable String id , @RequestBody final AccountRequest account) {
        return accountService.findAll(account);
    }

}
