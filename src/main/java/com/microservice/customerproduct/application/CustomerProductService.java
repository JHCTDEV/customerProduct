package com.microservice.customerproduct.application;

import com.microservice.customerproduct.domain.CustomerProductEntity;
import com.microservice.customerproduct.domain.ICustomerProductRepository;
import com.microservice.customerproduct.domain.ICustomerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerProductService implements ICustomerProductService {
    @Autowired
    private ICustomerProductRepository customerProductRepository;
    @Override
    public Flux<CustomerProductEntity> findAll() {
        return this.customerProductRepository.findAll();
    }

    @Override
    public Mono<CustomerProductEntity> save(CustomerProductEntity customer) {
        return this.customerProductRepository.save(customer);
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.customerProductRepository.deleteById(id);
    }

    @Override
    public Mono<CustomerProductEntity> findById(String id) {
        return this.customerProductRepository.findById(id);
    }
}
