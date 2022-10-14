package com.microservice.customerproduct.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerProductService {
    Flux<CustomerProductEntity> findAll();
    Mono<CustomerProductEntity> save(CustomerProductEntity customer);
    Mono<Void> delete(String id);
    Mono<CustomerProductEntity> findById(String id);
}
