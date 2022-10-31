package com.microservice.customerproduct.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.customerproduct.domain.entities.CustomerProductEntity;
import com.microservice.customerproduct.domain.enums.CrudEnum;
import com.microservice.customerproduct.domain.enums.CustomerTypeEnum;
import com.microservice.customerproduct.domain.enums.ProductEnum;
import com.microservice.customerproduct.domain.repositories.ICustomerRestRepository;
import com.microservice.customerproduct.domain.repositories.IProductRestRepository;
import com.microservice.customerproduct.domain.services.ICustomerProductExceptionService;
import com.microservice.customerproduct.domain.repositories.ICustomerProductRepository;
import com.microservice.customerproduct.domain.services.ICustomerProductValidatorService;
import com.microservice.customerproduct.infrastructure.dto.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CustomerProductValidatorService implements ICustomerProductValidatorService {
    @Autowired
    private ICustomerProductExceptionService customerProductExceptionService;
    @Autowired
    private ICustomerProductRepository       customerProductRepository;
    @Autowired
    private ICustomerRestRepository          customerRestRepository;
    @Autowired
    private IProductRestRepository           productRestRepository;
    private ModelMapper                      modelMapper = new ModelMapper();

    @Override
    public Mono<ResponseDto> validate(CustomerDto customerDto, CustomerProductDto customerProductDto, CrudEnum crudAction) {
        Function<ResponseDto, Mono<ResponseDto>> validate = responseDto -> {
            CustomerTypeDto        customerTypeDto      = this.modelMapper.map(responseDto.getData(), CustomerTypeDto.class);
            List<CustomerTypeEnum> listCustomerTypeEnum = Arrays.stream(CustomerTypeEnum.values()).filter(customerTypeEnum -> customerTypeEnum.getType().equals(customerTypeDto.getCode())).collect(Collectors.toList());
            if (listCustomerTypeEnum.size() == 0)
                return Mono.error(new Exception("the client does not have a valid type"));
            CustomerTypeEnum customerTypeEnum = listCustomerTypeEnum.get(0);
            if (customerTypeEnum == CustomerTypeEnum.ENTERPRISE || customerTypeEnum == CustomerTypeEnum.ENTERPRISE_PYME)
                return this.validateEnterpriseCustomer(customerProductDto, crudAction, customerTypeEnum);
            if (customerTypeEnum == CustomerTypeEnum.PERSONAL_VIP || customerTypeEnum == CustomerTypeEnum.PERSONAL)
                return this.validatePersonalCustomer(customerProductDto, crudAction, customerTypeEnum);
            return Mono.just(new ResponseDto());
        };
        return this.customerRestRepository.getCustomerType(customerDto.getCustomerType())
                .flatMap(validate)
                .onErrorResume(this.customerProductExceptionService::convertToDto);
    }

    @Override
    public Mono<ResponseDto> validatePersonalCustomer(CustomerProductDto customerProductDto, CrudEnum crudAction, CustomerTypeEnum customerTypeEnum) {
        Function<List<CustomerProductEntity>, Mono<ResponseDto>> validatePersonalCustomerAccountMaximus = listCustomerProductEntity -> {
            return this.productRestRepository.getAll().flatMap(responseDto -> {
                if (responseDto.isSuccess()) {
                    ObjectMapper                     ob                                  = new ObjectMapper();
                    List<ProductDto>                 listProductDto                      = ob.convertValue(responseDto.getData(), new TypeReference<List<ProductDto>>() {});
                    ProductDto                       productToRegisterDto                = listProductDto.stream().filter(productDto -> productDto.getId().equals(customerProductDto.getIdProduct())).collect(Collectors.toList()).get(0);
                    ProductDto                       productCreditCardDto                = listProductDto.stream().filter(productDto -> productDto.getCode().equals(ProductEnum.CREDIT_CARD.getValue())).collect(Collectors.toList()).get(0);
                    Long                             numberOfRegisteredProductCreditCard = listCustomerProductEntity.stream().filter(customerProductEntity -> customerProductEntity.getIdProduct().equals(productCreditCardDto.getId())).count();
                    Predicate<CustomerProductEntity> filterNumberProductForCreate        = customerProductEntity -> customerProductEntity.getIdProduct().equals(productToRegisterDto.getId());
                    Predicate<CustomerProductEntity> filterNumberProductForUpdate        = customerProductEntity -> {
                        return customerProductEntity.getIdProduct().equals(productToRegisterDto.getId()) && !customerProductEntity.getId().equals(customerProductDto.getId());
                    };
                    Predicate<CustomerProductEntity> filterNumberProduct        = crudAction == CrudEnum.CREATE ? filterNumberProductForCreate : filterNumberProductForUpdate;
                    Long                             numberOfRegisteredProducts = listCustomerProductEntity.stream().filter(filterNumberProduct).count();
                    if (numberOfRegisteredProducts > 0 && (productToRegisterDto.getCode().equals(ProductEnum.CURRENT_ACCOUNT.getValue()) || productToRegisterDto.getCode().equals(ProductEnum.SAVINGS_ACCOUNT.getValue()))) {
                        return Mono.error(new Exception("the customer already has the product, only one account of this type is allowed per customer"));
                    }
                    if (numberOfRegisteredProductCreditCard == 0 && productToRegisterDto.getCode().equals(ProductEnum.SAVINGS_ACCOUNT.getValue()) && customerTypeEnum == CustomerTypeEnum.PERSONAL_VIP)
                        return Mono.error(new Exception("the customer must have a credit card before purchasing this product"));
                    return Mono.just(responseDto);
                }
                return Mono.error(new Exception("error getting product list"));
            }).onErrorResume(this.customerProductExceptionService::convertToDto);
        };
        return this.customerProductRepository.findByIdCustomer(customerProductDto.getIdCustomer())
                .collectList()
                .flatMap(validatePersonalCustomerAccountMaximus)
                .onErrorResume(this.customerProductExceptionService::convertToDto);
    }

    @Override
    public Mono<ResponseDto> validateEnterpriseCustomer(CustomerProductDto customerProductDto, CrudEnum crudAction, CustomerTypeEnum customerTypeEnum) {
        Function<List<CustomerProductEntity>, Mono<ResponseDto>> validate = listCustomerProductEntity -> {
            return this.productRestRepository.getAll().flatMap(responseDto -> {
                if (responseDto.isSuccess()) {
                    ObjectMapper     ob                                   = new ObjectMapper();
                    List<ProductDto> listProductDto                       = ob.convertValue(responseDto.getData(), new TypeReference<List<ProductDto>>() {
                    });
                    ProductDto       productToRegisterDto                 = listProductDto.stream().filter(productDto -> productDto.getId() == customerProductDto.getIdProduct()).collect(Collectors.toList()).get(0);
                    ProductDto       productCreditCardDto                 = listProductDto.stream().filter(productDto -> productDto.getCode().equals(ProductEnum.CREDIT_CARD.getValue())).collect(Collectors.toList()).get(0);
                    Long             numberOfRegisteredProductsCreditCard = listCustomerProductEntity.stream().filter(customerProductEntity -> customerProductEntity.getIdProduct().equals(productCreditCardDto.getId())).count();
                    if (!productToRegisterDto.getCode().equals(ProductEnum.CURRENT_ACCOUNT.getValue()) && !productToRegisterDto.getCode().equals(ProductEnum.CREDIT_CARD.getValue()))
                        return Mono.error(new Exception("the client can only have current accounts and credit card"));
                    if (customerProductDto.getHolder().size() == 0)
                        return Mono.error(new Exception("The account must have at least one owner"));
                    if (numberOfRegisteredProductsCreditCard == 0 && productToRegisterDto.getCode().equals(ProductEnum.CURRENT_ACCOUNT.getValue()) && customerTypeEnum == CustomerTypeEnum.ENTERPRISE_PYME)
                        return Mono.error(new Exception("the customer must have a credit card before purchasing this product"));
                    return Mono.just(responseDto);
                }
                return Mono.error(new Exception("error getting product list"));
            }).onErrorResume(this.customerProductExceptionService::convertToDto);
        };
        return this.customerProductRepository.findByIdCustomer(customerProductDto.getIdCustomer())
                .collectList()
                .flatMap(validate)
                .onErrorResume(this.customerProductExceptionService::convertToDto);
    }


}
