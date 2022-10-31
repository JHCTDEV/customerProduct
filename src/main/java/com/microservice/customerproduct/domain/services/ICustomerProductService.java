package com.microservice.customerproduct.domain.services;

import com.microservice.customerproduct.domain.enums.CrudEnum;
import com.microservice.customerproduct.infrastructure.dto.*;
import reactor.core.publisher.Mono;

public interface ICustomerProductService {
    Mono<ResponseDto> getProductsByCustomer(String idCustomer);
    Mono<ResponseDto> save(CustomerProductDto customerProductRequestDto, CrudEnum crudAction);
    Mono<Void> delete(String id);
    Mono<ResponseDto> findById(String id);
    Mono<ResponseDto> findTransactionLimitByType(String idCustomerProduct, String idTransactionType);
}
