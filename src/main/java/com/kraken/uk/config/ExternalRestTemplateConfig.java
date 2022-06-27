package com.kraken.uk.config;

import com.kraken.uk.exception.RestTemplateResponseErrorHandler;
import com.kraken.uk.properties.ExternalRestProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static java.time.Duration.ofMillis;


@Configuration
@RequiredArgsConstructor
public class ExternalRestTemplateConfig {

    private final RestTemplateBuilder restTemplateBuilder;
    private final ExternalRestProperties externalRestProperties;

    @Bean
    @Qualifier("externalRestTemplate")
    public RestTemplate externalRestTemplate() {
        return restTemplateBuilder.setConnectTimeout(ofMillis(externalRestProperties.getConnectionTimeOut()))
                                  .setReadTimeout(ofMillis(externalRestProperties.getReadTimeOut()))
                                  .errorHandler(new RestTemplateResponseErrorHandler())
                                  .interceptors(new KrakenRestTemplateHeaderInterceptor(externalRestProperties.getKrakenAuthKey()))
                                  .build();
    }


}
