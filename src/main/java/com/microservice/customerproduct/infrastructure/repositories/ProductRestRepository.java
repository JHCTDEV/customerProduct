package com.microservice.customerproduct.infrastructure.repositories;

import com.microservice.customerproduct.domain.repositories.IProductRestRepository;
import com.microservice.customerproduct.infrastructure.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class ProductRestRepository implements IProductRestRepository {
    private String uriBase = "http://PRODUCT-SERVICE";
    @Autowired
    private WebClient.Builder builder;
    @Override
    public Mono<ResponseDto> getAll() {
        return this.builder.baseUrl(this.uriBase).build().get().uri("/product/list")
                .retrieve()
                .bodyToMono(ResponseDto.class);
    }

    @Override
    public Mono<ResponseDto> getById(String id) {
        return this.builder.baseUrl(this.uriBase).build().get().uri("/product/get/{id}",id)
                .retrieve()
                .bodyToMono(ResponseDto.class);

    }
}
