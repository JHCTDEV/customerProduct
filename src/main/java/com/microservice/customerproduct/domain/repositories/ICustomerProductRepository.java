package com.microservice.customerproduct.domain.repositories;

import com.microservice.customerproduct.domain.entities.CustomerProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ICustomerProductRepository extends ReactiveMongoRepository<CustomerProductEntity, String> {
    public Flux<CustomerProductEntity> findByIdCustomer(String idCustomer);
}

