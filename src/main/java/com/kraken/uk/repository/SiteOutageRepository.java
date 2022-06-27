package com.kraken.uk.repository;

import com.kraken.uk.domain.SiteOutage;
import com.kraken.uk.domain.SiteOutagePostResponse;
import com.kraken.uk.properties.MicroservicesProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SiteOutageRepository {
    private final RestTemplate externalRestTemplate;
    private final MicroservicesProperties microservicesProperties;

    public void postOutages(final String siteId, final List<SiteOutage> siteOutages) {
        externalRestTemplate.postForObject(microservicesProperties.getSiteOutageUrl(), siteOutages, SiteOutagePostResponse.class, siteId);
        log.info("Site outage posted successfully!");
    }
}
