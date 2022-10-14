package com.microservice.customerproduct.domain;

import lombok.Data;

@Data
public class SignatoryEntity {
    private String name;
    private String documentType;
    private String documentNumber;
}
