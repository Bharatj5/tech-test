package com.kraken.uk.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MicroservicesProperties {

    @Value("${kraken.api.siteInfoUrl}")
    private String siteInfoUrl;

    @Value("${kraken.api.siteOutageUrl}")
    private String siteOutageUrl;

    @Value("${kraken.api.outageUrl}")
    private String outageUri;
}
