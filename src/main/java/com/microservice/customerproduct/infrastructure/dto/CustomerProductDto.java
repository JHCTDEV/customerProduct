package com.microservice.customerproduct.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProductDto {
    private String id;
    private String idCustomer;
    private String idProduct;
    private String balance;
    private List<HolderDto> holder;
    private List<SignatoryDto> signatory;
    private Date createAt;
    private Date updateAt;
    private String status;
}
