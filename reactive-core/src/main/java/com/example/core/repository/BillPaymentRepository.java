package com.example.core.repository;

import com.example.core.model.BillPayment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface BillPaymentRepository extends ReactiveMongoRepository<BillPayment, String>
{
   Mono<BillPayment> findByRef1AndRef2(String ref1, String ref2);
}
