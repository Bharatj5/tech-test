package com.kraken.uk.properties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ExternalRestProperties {

    @Value("${kraken.api.key}")
    private String krakenAuthKey;

    @Min(value = 1_0000)
    @Max(value = 30_000)
    @Value("${microservice.connectionTimeOut}")
    private int connectionTimeOut;


    @Min(value = 1_0000)
    @Max(value = 120_000)
    @Value("${microservice.readTimeOut}")
    private int readTimeOut;

    @Min(value = 1)
    @Value("${microservice.readTimeOut}")
    private int httpMaxConnection;


}
