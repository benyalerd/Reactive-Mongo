package com.example.api.service;

import com.example.core.dto.request.InsertBillPaymentRequest;
import com.example.core.dto.request.PaymentStatus;
import com.example.core.dto.request.UpdatePaymentRequest;
import com.example.core.dto.response.InsertResponse;
import com.example.core.dto.response.LatePaymentResponse;
import com.example.core.mapper.BillPaymentMapper;
import com.example.core.model.BillPayment;
import com.example.core.repository.BillPaymentRepository;
import com.example.core.repository.MerchantRepository;
import com.example.kafka.EventType;
import com.example.kafka.MessageProducer;
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
import org.webjars.NotFoundException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

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
        val merchantOptional = merchantRepository.findByMerchantNo(request.getMerchantNo());
        val billPayment = BillPaymentMapper.MAPPER.mapInsertBillPaymentToBillPayment(request);
        billPayment.setCreatedDate(LocalDateTime.now());
        merchantOptional.ifPresent(billPayment::setMerchant);

        return billPaymentRepository.insert(billPayment)
                .map(x -> {
                    val response = new InsertResponse();
                    response.setId(x.getId());
                    return response;
                })
                .doOnError(throwable -> log.error("[insertBillPayment] : " + throwable.getMessage()));
    }

    //Updated Bil Payment Status
    @Transactional
    public void updatePaymentStatus(UpdatePaymentRequest request) throws JsonProcessingException {
           for(int i=0;i<request.getPaymentDetailList().size();i++){
               val detail = request.getPaymentDetailList().get(i);
               val billPaymentOptional = billPaymentRepository.findByRef1AndRef2(detail.getRef1(), detail.getRef2());
               if(billPaymentOptional.isPresent()){
                   val billPayment = billPaymentOptional.get();
                   billPayment.setStatus(PaymentStatus.COMPLETED.value());
                   billPaymentRepository.save(billPayment);
               }
               else
               {
                   log.info(String.format("ref 1 : %s ref 2 : %s not found",detail.getRef1(), detail.getRef2()));
                   throw new NotFoundException("bill payment not exits");
               }
           }
           val latePayment = getLatePayment();
            messageProducer.sendMessage(EventType.LATE_PAYMENT_NOTIFICATION.value(), objectMapper.writeValueAsString(latePayment));
    }

    //Get Late Bill Payment
    @Transactional
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
