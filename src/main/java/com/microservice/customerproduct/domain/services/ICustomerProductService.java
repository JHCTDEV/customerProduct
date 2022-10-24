package com.microservice.customerproduct.domain.services;

import com.microservice.customerproduct.domain.entities.CustomerProductEntity;
import com.microservice.customerproduct.domain.enums.CrudEnum;
import com.microservice.customerproduct.infrastructure.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerProductService {
    Flux<CustomerProductEntity> getProductsByCustomer(String idCustomer);
    Mono<ResponseDto> save(CustomerProductDto customerProductRequestDto, CrudEnum crudAction);
    Mono<Void> delete(String id);
    Mono<CustomerProductEntity> findById(String id);
}
