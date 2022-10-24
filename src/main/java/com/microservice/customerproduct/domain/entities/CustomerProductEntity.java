package com.microservice.customerproduct.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "customerProduct")
@Data
public class CustomerProductEntity {
    @Id
    private String id;
    private String idCustomer;
    private String idProduct;
    private float balance;
    private List<HolderEntity> holder;
    private List<SignatoryEntity> signatory;
    private Date createAt;
    private Date updateAt;
    private String status;
}
