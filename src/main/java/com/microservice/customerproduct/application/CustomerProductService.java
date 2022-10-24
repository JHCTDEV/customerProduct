package com.microservice.customerproduct.application;

import com.microservice.customerproduct.domain.entities.CustomerProductEntity;
import com.microservice.customerproduct.domain.enums.CrudEnum;
import com.microservice.customerproduct.domain.repositories.ICustomerRestRepository;
import com.microservice.customerproduct.domain.services.ICustomerProductRepository;
import com.microservice.customerproduct.domain.services.ICustomerProductService;
import com.microservice.customerproduct.domain.services.ICustomerProductValidatorService;
import com.microservice.customerproduct.infrastructure.dto.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Log4j2
public class CustomerProductService implements ICustomerProductService {
    @Autowired
    private ICustomerProductRepository customerProductRepository;
    @Autowired
    private ICustomerRestRepository customerRestRepository;
    @Autowired
    private ICustomerProductValidatorService customerProductValidatorService;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Flux<CustomerProductEntity> getProductsByCustomer(String idCustomer) {
        log.info("list");
        return this.customerProductRepository.findByIdCustomer(idCustomer);
    }

    @Override
    public Mono<ResponseDto> save(CustomerProductDto customerProductDto, CrudEnum crudAction) {
        CustomerProductEntity customerProductEntity = this.modelMapper.map(customerProductDto,CustomerProductEntity.class);
        Function<ResponseDto,Mono<ResponseDto>> accountRegister = responseDto -> {
            if (responseDto.isSuccess())
                return this.customerProductRepository.save(customerProductEntity).flatMap(y ->{
                    CustomerProductResponseDto dto = this.modelMapper.map(y,CustomerProductResponseDto.class);
                    ResponseDto r = new ResponseDto();
                    r.setSuccess(true);
                    r.setData(dto);
                    return Mono.just(r);
                });
            return Mono.just(responseDto);
        };
        return this.customerRestRepository.getById(customerProductDto.getIdCustomer())
                .flatMap(responseDto ->  {
                    CustomerDto customerDto =  this.modelMapper.map(responseDto.getData(),CustomerDto.class);
                    return this.customerProductValidatorService.validate(customerDto, customerProductDto, crudAction);
                })
                .flatMap(accountRegister::apply);
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
