package com.microservice.customerproduct.infrastructure.repositories;

import com.microservice.customerproduct.domain.repositories.ICustomerRestRepository;
import com.microservice.customerproduct.infrastructure.dto.CustomerDto;
import com.microservice.customerproduct.infrastructure.dto.CustomerTypeDto;
import com.microservice.customerproduct.infrastructure.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public class CustomerRestRepository implements ICustomerRestRepository {
    private String uriBase = "http://CUSTOMER-SERVICE";
    @Autowired
    private WebClient.Builder builder;

    @Override
    public Mono<ResponseDto> getCustomerType(String idCustomerType) {
        return this.builder.baseUrl(this.uriBase).build().get().uri("/customerType/get/{id}", idCustomerType)
                .retrieve()
                .bodyToMono(ResponseDto.class);

    }

    @Override
    public Mono<ResponseDto> getById(String idCustomer) {
        return this.builder.baseUrl(this.uriBase).build().get().uri("/customer/get/{id}", idCustomer)
                .retrieve()
                .bodyToMono(ResponseDto.class);
    }
}
