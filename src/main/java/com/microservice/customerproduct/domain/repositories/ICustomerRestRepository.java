package com.microservice.customerproduct.domain.repositories;

import com.microservice.customerproduct.infrastructure.dto.CustomerDto;
import com.microservice.customerproduct.infrastructure.dto.ResponseDto;
import reactor.core.publisher.Mono;


public interface ICustomerRestRepository {
    Mono<ResponseDto> getById(String idCustomer);
    Mono<ResponseDto> getCustomerType(String idCustomerType);
}
