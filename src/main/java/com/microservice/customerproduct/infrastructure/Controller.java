package com.microservice.customerproduct.infrastructure;


import com.microservice.customerproduct.domain.CustomerProductEntity;
import com.microservice.customerproduct.domain.ICustomerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customerProduct")
public class Controller {

    @Autowired
    private ICustomerProductService customerProductService;

    @GetMapping(value = "list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CustomerProductEntity> findAll(){
        return this.customerProductService.findAll();
    }

    @GetMapping(value = "get/{id}")
    Mono<CustomerProductEntity> findById(@PathVariable("id") String id){
        return this.customerProductService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return this.customerProductService.delete(id);
    }

    @PostMapping("save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerProductEntity> save(@RequestBody CustomerProductEntity customer){
        return this.customerProductService.save(customer);

    }
}
