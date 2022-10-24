package com.microservice.customerproduct.domain.enums;

public enum CrudEnum {
    UPDATE(1),
    CREATE(2),
    DELETE(3),
    READ(4);

    private int value;

    CrudEnum(int value) {
        this.value = value;
    }

    public int getValue() {

        return value;
    }
}
