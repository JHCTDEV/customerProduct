package com.microservice.customerproduct.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ICustomerProductRepository extends ReactiveMongoRepository<CustomerProductEntity, String> {
}
