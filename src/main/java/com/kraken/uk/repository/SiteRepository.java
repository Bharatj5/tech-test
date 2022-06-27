package com.kraken.uk.repository;

import com.kraken.uk.domain.SiteInfo;
import com.kraken.uk.properties.MicroservicesProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class SiteRepository {
    private final RestTemplate externalRestTemplate;
    private final MicroservicesProperties microservicesProperties;

    public SiteInfo getSiteInfo(final String siteId) {
        return externalRestTemplate.getForObject(microservicesProperties.getSiteInfoUrl(), SiteInfo.class, siteId);
    }

}
