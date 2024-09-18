package com.example.api.service;

import com.example.core.dto.request.InsertBillPaymentRequest;
import com.example.core.dto.request.PaymentStatus;
import com.example.core.dto.request.UpdatePaymentRequest;
import com.example.core.dto.response.InsertResponse;
import com.example.core.dto.response.LatePaymentResponse;
import com.example.core.exception.BusinessValidationException;
import com.example.core.mapper.BillPaymentMapper;
import com.example.core.model.BillPayment;
import com.example.core.repository.BillPaymentRepository;
import com.example.core.repository.MerchantRepository;
import com.example.kafka.EventType;
import com.example.kafka.MessageProducer;
import com.example.kafka.dto.Outbox;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.core.Constants.ERROR_CODE_MERCHANT_NOT_EXITS;
import static com.example.core.Constants.ERROR_MSG_MERCHANT_NOT_EXITS;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillPaymentService {
    private final BillPaymentRepository billPaymentRepository;
    private final MerchantRepository merchantRepository;
    private final ReactiveMongoTemplate template;
    private final MessageProducer messageProducer;
    private final ObjectMapper objectMapper;

    //Insert Bill Payment
    @Transactional
    public Mono<InsertResponse> insertBillPayment(InsertBillPaymentRequest request) {

        val billPayment = BillPaymentMapper.MAPPER.mapInsertBillPaymentToBillPayment(request);
        billPayment.setCreatedDate(LocalDateTime.now());
        billPayment.setLastModifiedDate(LocalDateTime.now());
        billPayment.setLastModifiedBy(request.getCreatedBy());

        return merchantRepository.findByMerchantNo(request.getMerchantNo())
                .switchIfEmpty(Mono.error(new BusinessValidationException(ERROR_CODE_MERCHANT_NOT_EXITS, ERROR_MSG_MERCHANT_NOT_EXITS)))
                .map(x -> {
                    billPayment.setMerchant(x);
                    return billPayment;
                })
                .flatMap(x -> billPaymentRepository.save(billPayment))
                .map(x -> {
                    val response = new InsertResponse();
                    response.setId(x.getId());
                    return response;
                })
                .doOnError(throwable -> log.error("[insertBillPayment] : " + throwable.getMessage()));
    }

    //Updated Bil Payment Status
    @Transactional
    public Mono<List<LatePaymentResponse>> updatePaymentStatus(UpdatePaymentRequest request) {

        if (request.getPaymentDetailList().isEmpty()) {
            return Mono.empty();
        }

        return Flux.fromIterable(request.getPaymentDetailList())
                .flatMap(x -> billPaymentRepository.findByRef1AndRef2(x.getRef1(), x.getRef2())
                        .map(res -> {

                            res.setStatus(PaymentStatus.COMPLETED.value());
                            res.setLastModifiedBy(request.getUpdatedBy());
                            res.setLastModifiedDate(LocalDateTime.now());
                            return res;

                        })
                        .flatMap(billPaymentRepository::save)
                ).collectList()
                .flatMap(x -> getLatePayment().flatMap(payload -> {
                    try {
                        if (!payload.isEmpty()) {

                            val value = objectMapper.writeValueAsString(payload);
                            val outbox = new Outbox();

                            outbox.setPayload(value);
                            outbox.setType(EventType.LATE_PAYMENT_NOTIFICATION.value());
                            outbox.setId(UUID.randomUUID().toString());

                            val outboxValue = objectMapper.writeValueAsString(outbox);

                            messageProducer.sendMessage("dev-demo", outboxValue);
                        }
                        return Mono.just(payload);
                    } catch (JsonProcessingException e) {
                        throw new BusinessValidationException("", e.getMessage());
                    }
                }));
    }

    //Get Late Bill Payment
    public Mono<List<LatePaymentResponse>> getLatePayment(){
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("status").is(PaymentStatus.AWAITING_PAYMENT),
                        Criteria.where("dueDate").lt(LocalDateTime.now())
                )
        );

        return template.find(query, BillPayment.class)
                .map(BillPaymentMapper.MAPPER::mapBillPaymentToLatePayment)
                .collectList();
    }
}
