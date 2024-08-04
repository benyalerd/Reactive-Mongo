package com.example.api.service;

import com.example.core.dto.request.AccountRequest;
import com.example.core.dto.response.AccountResponse;
import com.example.core.mapper.AccountMapper;
import com.example.core.model.Account;
import com.example.core.repository.AccountRepository;
import com.example.kafka.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    @Autowired
    ReactiveMongoTemplate template;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MessageProducer messageProducer;

    public Mono<Account> findById(String id) {
        return template.findById(id, Account.class);
    }

    public Mono<List<AccountResponse>> findAll(@Validated AccountRequest account) {
        return accountRepository.findAll()
                .map(AccountMapper.MAPPER::map)
                .collectList();
    }

    public void testKafka() {
        messageProducer.sendMessage("dev-demo", "");
    }
}
