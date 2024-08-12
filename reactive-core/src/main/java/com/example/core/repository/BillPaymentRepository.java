package com.example.core.repository;

import com.example.core.model.BillPayment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillPaymentRepository extends ReactiveMongoRepository<BillPayment, Long>
{

}
