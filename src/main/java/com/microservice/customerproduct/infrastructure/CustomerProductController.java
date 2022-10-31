package com.microservice.customerproduct.infrastructure;


import com.microservice.customerproduct.domain.enums.CrudEnum;
import com.microservice.customerproduct.domain.services.ICustomerProductService;
import com.microservice.customerproduct.infrastructure.dto.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customerProduct")
@Log4j2
public class CustomerProductController {

    private final ICustomerProductService customerProductService;

    @Autowired
    public CustomerProductController(ICustomerProductService customerProductService) {
        this.customerProductService = customerProductService;
    }

    @GetMapping(value = "list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseDto> findAll() {
        return this.customerProductService.getProductsByCustomer("dd");
    }

    @GetMapping(value = "get/{id}")
    Mono<ResponseDto> findById(@PathVariable("id") String id) {
        return this.customerProductService.findById(id);
    }

    @GetMapping(value = "getLimitsByTransaction/{idCustomerProduct}/{idTransactionType}")
    Mono<ResponseDto> findAllLimit(@PathVariable("idCustomerProduct") String idCustomerProduct, @PathVariable("idTransactionType") String idTransactionType) {
        return this.customerProductService.findTransactionLimitByType(idCustomerProduct, idTransactionType);
    }

    @DeleteMapping("delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.customerProductService.delete(id);
    }

    @PostMapping("save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDto> create(@RequestBody CustomerProductDto customerProductDto) {
        return this.customerProductService.save(customerProductDto, CrudEnum.CREATE);
    }

    @PutMapping("save")
    public Mono<ResponseDto> update(@RequestBody CustomerProductDto customerProductDto) {
        return this.customerProductService.save(customerProductDto, CrudEnum.UPDATE);
    }
}
