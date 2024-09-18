package com.example.core.repository;

import com.example.core.model.Merchant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MerchantRepository extends ReactiveMongoRepository<Merchant, String>
{
    Mono<Merchant> findByMerchantNo (String merchantNo);
    Mono<Merchant> findByemail (String merchantNo);
}
