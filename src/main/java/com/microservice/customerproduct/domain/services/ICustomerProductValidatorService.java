package com.microservice.customerproduct.domain.services;

import com.microservice.customerproduct.domain.entities.CustomerProductEntity;
import com.microservice.customerproduct.domain.enums.CrudEnum;
import com.microservice.customerproduct.domain.enums.CustomerTypeEnum;
import com.microservice.customerproduct.infrastructure.dto.CustomerDto;
import com.microservice.customerproduct.infrastructure.dto.CustomerProductDto;
import com.microservice.customerproduct.infrastructure.dto.ResponseDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICustomerProductValidatorService {
    Mono<ResponseDto> validate(CustomerDto customerDto, CustomerProductDto customerProductDto, CrudEnum crudAction);
    Mono<ResponseDto> validatePersonalCustomer(CustomerProductDto customerProductDto, CrudEnum crudAction, CustomerTypeEnum customerTypeEnum);
    Mono<ResponseDto> validateEnterpriseCustomer(CustomerProductDto customerProductDto,CrudEnum crudAction, CustomerTypeEnum customerTypeEnum);
}
