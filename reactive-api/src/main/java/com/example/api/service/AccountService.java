package com.example.api.service;

import com.example.core.dto.request.AccountRequest;
import com.example.core.dto.response.AccountResponse;
import com.example.core.mapper.AccountMapper;
import com.example.core.model.Account;
import com.example.core.repository.AccountRepository;
import com.example.kafka.MessageProducer;
import com.example.kafka.dto.Outbox;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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

    private final ObjectMapper objectMapper;

    public Mono<Account> findById(String id) {
        return template.findById(id, Account.class);
    }

    public Mono<List<AccountResponse>> findAll(@Validated AccountRequest account) {
        return accountRepository.findAll()
                .map(AccountMapper.MAPPER::map)
                .collectList();
    }

    public void testKafka() throws JsonProcessingException {
        Outbox message = new Outbox();
        message.setId("1234");
        message.setType("demo");
        val account =  new AccountRequest();
        account.setId("123");
        message.setPayload(objectMapper.writeValueAsString(account));
        messageProducer.sendMessage("dev-demo", objectMapper.writeValueAsString(message));
    }
}
