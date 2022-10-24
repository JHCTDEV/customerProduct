package com.microservice.customerproduct.domain.enums;

import lombok.Getter;

@Getter
public enum CustomerTypeEnum {
    PERSONAL("PERSONAL"),
    PERSONAL_VIP("PERSONAL_VIP"),
    ENTERPRISE("ENTERPRISE"),
    ENTERPRISE_PYME("ENTERPRISE_PYME");

    private String type;
    CustomerTypeEnum(String type) {
        this.type = type;
    }
}
