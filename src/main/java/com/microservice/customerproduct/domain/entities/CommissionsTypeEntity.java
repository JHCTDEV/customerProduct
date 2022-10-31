package com.microservice.customerproduct.domain.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "commissionType")
@Data
public class CommissionsTypeEntity {
    private String id;
    private String code;
    private String name;
    private String description;
    private Date createAt;
    private Date updateAt;
    private String status;
}
