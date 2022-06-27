package com.kraken.uk.service;

import com.kraken.uk.domain.Outage;
import com.kraken.uk.domain.SiteInfo;
import com.kraken.uk.domain.SiteOutage;
import com.kraken.uk.dto.SiteOutagePostResponseDto;
import com.kraken.uk.exception.NotFoundException;
import com.kraken.uk.repository.SiteOutageRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class SiteOutageService {

    public static final LocalDateTime DEFAULT_BEGIN_TIME = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0);
    private final OutageService outageService;
    private final SiteService siteService;
    private final SiteOutageExtractor siteOutageExtractor;
    private final SiteOutageRepository siteOutageRepository;

    @CircuitBreaker(name = "siteOutage")
    @Retry(name = "siteOutage")
    public SiteOutagePostResponseDto reportSiteOutage(final String siteId) {
        final List<SiteOutage> siteOutages = reportSiteOutage(siteId, DEFAULT_BEGIN_TIME);
        return SiteOutagePostResponseDto.of()
                                        .noOfOutageReported(siteOutages.size())
                                        .reportedAt(LocalDateTime.now()).build();
    }

    private List<SiteOutage> reportSiteOutage(final String siteId, final LocalDateTime beginTime) {
        final List<Outage> reportedOutages = outageService.getReportedOutage();
        final SiteInfo siteInfo = siteService.getSiteInfo(siteId);
        final List<SiteOutage> siteOutages = siteOutageExtractor.validSiteOutages(siteInfo, reportedOutages, beginTime);
        log.info("Site outages for the site {} are: {}", siteInfo.getName(), siteOutages);
        postOutages(siteId, siteOutages);
        return siteOutages;
    }

    private void postOutages(final String siteId, final List<SiteOutage> siteOutages) {
        if (isNotEmpty(siteOutages)) {
            siteOutageRepository.postOutages(siteId, siteOutages);
        } else {
            throw new NotFoundException(String.format("No outage to post for site: %s", siteId));
        }
    }

}
