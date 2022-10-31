package com.microservice.customerproduct.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document(collection = "customerProductCommission")
@Data
public class CustomerProductCommissionsEntity {
    @Id
    private String id;
    private     String idCustomerProduct;
    private     String idCommissionType;
    private     Float amount;
    private     Date   createAt;
    private     Date   updateAt;
    private     String status;

}
