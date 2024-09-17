package com.example.api.service;

import com.example.core.dto.request.InsertMerchantRequest;
import com.example.core.dto.response.InsertResponse;
import com.example.core.mapper.MerchantMapper;
import com.example.core.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;

    //Insert Merchant
    @Transactional
    public Mono<InsertResponse> insertMerchant(InsertMerchantRequest request) {
        val merchant = MerchantMapper.MAPPER.mapInsertMerchantToMerchant(request);
        merchant.setCreatedDate(LocalDateTime.now());
        return merchantRepository.insert(merchant)
                .map(x -> {
                    val response = new InsertResponse();
                    response.setId(x.getId());
                    return response;
                })
                .doOnError(throwable -> log.error("[insertMerchant] : " + throwable.getMessage()));
    }
}
