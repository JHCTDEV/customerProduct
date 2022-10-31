package com.microservice.customerproduct.domain.repositories;

import com.microservice.customerproduct.domain.entities.CustomerProductTransactionLimitEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ICustomerProductTransactionLimitRepository extends ReactiveMongoRepository<CustomerProductTransactionLimitEntity, String> {
    public Mono<CustomerProductTransactionLimitEntity> findByIdCustomerProductAndIdTransactionType(String idCustomerProduct, String idTransactionType);
}

