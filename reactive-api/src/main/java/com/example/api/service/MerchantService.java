package com.example.api.service;

import com.example.core.dto.request.InsertMerchantRequest;
import com.example.core.dto.response.InsertResponse;
import com.example.core.exception.BusinessValidationException;
import com.example.core.mapper.MerchantMapper;
import com.example.core.model.Merchant;
import com.example.core.repository.MerchantRepository;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.core.Constants.ERROR_CODE_EMAIL_ALREADY_EXITS;
import static com.example.core.Constants.ERROR_MSG_EMAIL_ALREADY_EXITS;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final Validator validator;
    //Insert Merchant
    @Transactional
    public Mono<InsertResponse> insertMerchant(InsertMerchantRequest request) {

        val result = validator.validate(request);
        if (!result.isEmpty()) {
            List<String> error = new ArrayList<>();
            result.forEach(e -> error.add(e.getPropertyPath().toString() + " : " + e.getMessage()));
            throw new BusinessValidationException("",error.toString());
        }

        val merchant = MerchantMapper.MAPPER.mapInsertMerchantToMerchant(request);
        merchant.setCreatedDate(LocalDateTime.now());
        merchant.setMerchantNo(UUID.randomUUID().toString());
        merchant.setLastModifiedDate(LocalDateTime.now());
        merchant.setLastModifiedBy(request.getCreatedBy());

        return merchantRepository.findByemail(request.getEmail())
                .flatMap(x -> Mono.error(new BusinessValidationException(ERROR_CODE_EMAIL_ALREADY_EXITS, ERROR_MSG_EMAIL_ALREADY_EXITS)))
                .switchIfEmpty(merchantRepository.save(merchant))
                .cast(Merchant.class)
                .map(x -> {
                    val response = new InsertResponse();
                    response.setId(x.getId());
                    return response;
                })
                .doOnError(throwable -> log.error("[insertMerchant] : " + throwable.getMessage()));

    }
}
