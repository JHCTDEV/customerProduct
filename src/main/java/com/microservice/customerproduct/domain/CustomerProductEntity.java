package com.microservice.customerproduct.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "customerProduct")
@Data
public class CustomerProductEntity {
    @Id
    private String id;
    private String idCustomer;
    private String idProduct;
    private String customerType;
    private String balance;
    private List<HolderEntity> holder;
    private List<SignatoryEntity> signatory;
    private String status;
}
