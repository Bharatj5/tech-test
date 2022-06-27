package com.kraken.uk.config;

import com.kraken.uk.exception.RestTemplateResponseErrorHandler;
import com.kraken.uk.properties.ExternalRestProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ExternalRestTemplateConfigTest {

    private ExternalRestTemplateConfig classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new ExternalRestTemplateConfig(new RestTemplateBuilder(), new ExternalRestProperties());
    }


    @Test
    void externalRestTemplate_happyPath_restTemplateCreated() {
        final RestTemplate actualRestTemplate = classUnderTest.externalRestTemplate();

        assertThat(actualRestTemplate.getErrorHandler()).isInstanceOf(RestTemplateResponseErrorHandler.class);
        assertThat(actualRestTemplate.getInterceptors()
                                     .get(0)).isInstanceOf(KrakenRestTemplateHeaderInterceptor.class);
    }
}