package com.example.api.service;

import com.example.core.dto.request.InsertMerchantRequest;
import com.example.core.dto.response.InsertResponse;
import com.example.core.mapper.MerchantMapper;
import com.example.core.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;

    //Insert Merchant
    public Mono<InsertResponse> insertMerchant(@Validated InsertMerchantRequest request) {
        return merchantRepository.insert(MerchantMapper.MAPPER.mapInsertMerchantToMerchant(request))
                .map(x -> {
                    val response = new InsertResponse();
                    response.setId(x.getId());
                    response.setStatusCode("200");
                    response.setMsgCode("success");
                    return response;
                })
                .doOnError(throwable -> log.error("[insertMerchant] : " + throwable.getMessage()));
    }
}
