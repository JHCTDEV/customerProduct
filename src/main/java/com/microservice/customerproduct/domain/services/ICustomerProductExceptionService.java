package com.microservice.customerproduct.domain.services;

import com.microservice.customerproduct.infrastructure.dto.ResponseDto;
import reactor.core.publisher.Mono;

public interface ICustomerProductExceptionService {
    Mono<ResponseDto> convertToDto(Throwable exception);
}
