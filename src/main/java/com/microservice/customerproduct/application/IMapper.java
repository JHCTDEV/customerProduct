package com.microservice.customerproduct.application;

public interface IMapper <INPUT, OUTPUT>{
    OUTPUT map(INPUT obj);
}
