package com.microservice.customerproduct.domain.repositories;

import com.microservice.customerproduct.infrastructure.dto.CustomerDto;
import com.microservice.customerproduct.infrastructure.dto.CustomerTypeDto;
import com.microservice.customerproduct.infrastructure.dto.ResponseDto;
import reactor.core.publisher.Mono;

public interface IProductRestRepository {
    Mono<ResponseDto> getAll();
    Mono<ResponseDto> getById(String id);
}
