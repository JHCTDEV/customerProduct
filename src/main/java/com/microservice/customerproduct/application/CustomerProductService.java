package com.microservice.customerproduct.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.customerproduct.domain.entities.CustomerProductEntity;
import com.microservice.customerproduct.domain.enums.CrudEnum;
import com.microservice.customerproduct.domain.repositories.ICustomerProductTransactionLimitRepository;
import com.microservice.customerproduct.domain.repositories.ICustomerRestRepository;
import com.microservice.customerproduct.domain.repositories.ICustomerProductRepository;
import com.microservice.customerproduct.domain.services.ICustomerProductService;
import com.microservice.customerproduct.domain.services.ICustomerProductValidatorService;
import com.microservice.customerproduct.infrastructure.dto.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Service
@Log4j2
public class CustomerProductService implements ICustomerProductService {
    private final ICustomerProductRepository                 customerProductRepository;
    private final ICustomerProductTransactionLimitRepository customerProductTransactionLimitRepository;
    private final ICustomerRestRepository                    customerRestRepository;
    private final ICustomerProductValidatorService           customerProductValidatorService;
    private       ModelMapper                                modelMapper = new ModelMapper();
    @Autowired
    public CustomerProductService(ICustomerProductRepository customerProductRepository, ICustomerProductTransactionLimitRepository customerProductTransactionLimitRepository, ICustomerRestRepository customerRestRepository, ICustomerProductValidatorService customerProductValidatorService) {
        this.customerProductRepository                 = customerProductRepository;
        this.customerProductTransactionLimitRepository = customerProductTransactionLimitRepository;
        this.customerRestRepository                    = customerRestRepository;
        this.customerProductValidatorService           = customerProductValidatorService;
    }

    @Override
    public Mono<ResponseDto> getProductsByCustomer(String idCustomer) {
        return this.customerProductRepository.findByIdCustomer(idCustomer).collectList().flatMap(customerProductEntityList -> {
            ObjectMapper             ob                     = new ObjectMapper();
            List<CustomerProductDto> customerProductDtoList = ob.convertValue(customerProductEntityList, new TypeReference<List<CustomerProductDto>>() {});
            ResponseDto              responseDto            = new ResponseDto();
            responseDto.setSuccess(true);
            responseDto.setData(customerProductDtoList);
            return Mono.just(responseDto);
        });
    }

    @Override
    public Mono<ResponseDto> findTransactionLimitByType(String idCustomerProduct, String idTransactionType) {
        return this.customerProductTransactionLimitRepository.findByIdCustomerProductAndIdTransactionType(idCustomerProduct, idTransactionType).flatMap(customerProductTransactionLimitEntity -> {
            CustomerProductTransactionLimitDto customerProductTransactionLimitDto = this.modelMapper.map(customerProductTransactionLimitEntity, CustomerProductTransactionLimitDto.class);
            ResponseDto                        responseDto1                       = new ResponseDto();
            responseDto1.setSuccess(true);
            responseDto1.setData(customerProductTransactionLimitDto);
            return Mono.just(responseDto1);
        });
    }

    @Override
    public Mono<ResponseDto> save(CustomerProductDto customerProductDto, CrudEnum crudAction) {
        CustomerProductEntity customerProductEntity = this.modelMapper.map(customerProductDto, CustomerProductEntity.class);
        Function<ResponseDto, Mono<ResponseDto>> accountRegister = responseDto -> {
            if (responseDto.isSuccess())
                return this.customerProductRepository.save(customerProductEntity).flatMap(customerProductEntity1 -> {
                    CustomerProductResponseDto dto          = this.modelMapper.map(customerProductEntity1, CustomerProductResponseDto.class);
                    ResponseDto                responseDto1 = new ResponseDto();
                    responseDto1.setSuccess(true);
                    responseDto1.setData(dto);
                    return Mono.just(responseDto1);
                });
            return Mono.just(responseDto);
        };
        return this.customerRestRepository.getById(customerProductDto.getIdCustomer())
                .flatMap(responseDto -> {
                    CustomerDto customerDto = this.modelMapper.map(responseDto.getData(), CustomerDto.class);
                    return this.customerProductValidatorService.validate(customerDto, customerProductDto, crudAction);
                }).flatMap(accountRegister::apply);
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.customerProductRepository.deleteById(id);
    }

    @Override
    public Mono<ResponseDto> findById(String id) {
        return this.customerProductRepository.findById(id).flatMap(customerProductEntity -> {
            CustomerProductDto customerProductDto = this.modelMapper.map(customerProductEntity, CustomerProductDto.class);
            ResponseDto        responseDto        = new ResponseDto();
            responseDto.setSuccess(true);
            responseDto.setData(customerProductDto);
            return Mono.just(responseDto);
        });
    }

}
