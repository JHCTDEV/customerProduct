package com.microservice.customerproduct.application;

import com.microservice.customerproduct.domain.services.ICustomerProductExceptionService;
import com.microservice.customerproduct.infrastructure.dto.ExceptionDto;
import com.microservice.customerproduct.infrastructure.dto.ResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerProductExceptionService implements ICustomerProductExceptionService {
    @Override
    public Mono<ResponseDto> convertToDto(Throwable exception) {
        ResponseDto responseDto = new ResponseDto();
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(exception.getMessage());
        responseDto.setSuccess(false);
        responseDto.setError(exceptionDto);
        return Mono.just(responseDto);
    }
}
