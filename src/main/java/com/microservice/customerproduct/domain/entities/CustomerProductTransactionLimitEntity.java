package com.microservice.customerproduct.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "customerProductTransactionLimit")
@Data
public class CustomerProductTransactionLimitEntity {
    @Id
    private String  id;
    private String  idCustomerProduct;
    private Integer limit;
    private String  idTransactionType;
    private Date    createAt;
    private Date    updateAt;
    private String    status;
}
