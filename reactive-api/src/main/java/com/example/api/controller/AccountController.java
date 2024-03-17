package com.example.api.controller;

import com.example.api.service.AccountService;
import com.example.core.dto.request.AccountRequest;
import com.example.core.dto.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
     @PostMapping("/fetch/{id}")
    public Mono<List<AccountResponse>> getBookByIsbn(@PathVariable String id , @RequestBody final AccountRequest account) {
        return accountService.findAll(account);
    }

}
