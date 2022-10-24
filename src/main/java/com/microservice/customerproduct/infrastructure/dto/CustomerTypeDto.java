package com.microservice.customerproduct.infrastructure.dto;

import lombok.Data;

@Data
public class CustomerTypeDto {
    private String id;
    private String code;
    private String name;
    private String description;
    private String status;
}
