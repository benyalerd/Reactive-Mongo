package com.example.core.repository;

import com.example.core.model.Merchant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends ReactiveMongoRepository<Merchant, String>
{
    Optional<Merchant> findByMerchantNo (String merchantNo);
}
