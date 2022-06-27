package com.kraken.uk.repository;

import com.kraken.uk.domain.Outage;
import com.kraken.uk.exception.NotFoundException;
import com.kraken.uk.properties.MicroservicesProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OutageRepository {

    private final RestTemplate externalRestTemplate;
    private final MicroservicesProperties microservicesProperties;

    public List<Outage> getReportedOutage() {
        final Outage[] response = externalRestTemplate.getForObject(microservicesProperties.getOutageUri(), Outage[].class);
        return Optional.ofNullable(response)
                       .map(Arrays::asList)
                       .filter(CollectionUtils::isNotEmpty)
                       .orElseThrow(NotFoundException::new);
    }

}
