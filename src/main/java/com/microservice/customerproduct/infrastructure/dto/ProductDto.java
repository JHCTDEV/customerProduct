package com.microservice.customerproduct.infrastructure.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {
    private String id;
    private String code;
    private String name;
    private String description;
    private String type;
    private Date createAt;
    private Date updateAt;
    private String status;

}
