package com.example.api.service;

import com.example.core.dto.request.InsertBillPaymentRequest;
import com.example.core.dto.request.PaymentStatus;
import com.example.core.dto.response.InsertResponse;
import com.example.core.dto.response.LatePaymentResponse;
import com.example.core.mapper.BillPaymentMapper;
import com.example.core.model.BillPayment;
import com.example.core.repository.BillPaymentRepository;
import com.example.core.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillPaymentService {
    private final BillPaymentRepository billPaymentRepository;
    private final MerchantRepository merchantRepository;
    private final ReactiveMongoTemplate template;

    //Insert Bill Payment
    public Mono<InsertResponse> insertBillPayment(@Validated InsertBillPaymentRequest request) {
        val merchantOptional = merchantRepository.findByMerchantNo(request.getMerchantNo());
        val billPayment = BillPaymentMapper.MAPPER.mapInsertBillPaymentToBillPayment(request);
        merchantOptional.ifPresent(billPayment::setMerchant);

        return billPaymentRepository.insert(billPayment)
                .map(x -> {
                    val response = new InsertResponse();
                    response.setId(x.getId());
                    response.setStatusCode("200");
                    response.setMsgCode("success");
                    return response;
                })
                .doOnError(throwable -> log.error("[insertBillPayment] : " + throwable.getMessage()));
    }

    //Updated Bil Payment Status



    //Get Late Bill Payment
    public Mono<List<LatePaymentResponse>> getLatePayment(){
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("status").is(PaymentStatus.AWAITING_PAYMENT),
                        Criteria.where("due_date").lt(LocalDateTime.now())
                )
        );

        return template.find(query, BillPayment.class)
                .map(BillPaymentMapper.MAPPER::mapBillPaymentToLatePayment)
                .collectList();
    }
}
