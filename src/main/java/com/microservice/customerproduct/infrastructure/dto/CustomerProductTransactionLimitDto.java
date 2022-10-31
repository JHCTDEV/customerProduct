package com.microservice.customerproduct.infrastructure.dto;

import lombok.Data;

import java.util.Date;
@Data
public class CustomerProductTransactionLimitDto {
    private String  id;
    private  String  idCustomerProduct;
    private  Integer limit;
    private  String  idTransactionType;
    private  Date    createAt;
    private  Date    updateAt;
    private  String  status;
}
