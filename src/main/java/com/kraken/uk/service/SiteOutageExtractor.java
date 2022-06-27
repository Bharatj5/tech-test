package com.kraken.uk.service;

import com.kraken.uk.domain.Device;
import com.kraken.uk.domain.Outage;
import com.kraken.uk.domain.SiteInfo;
import com.kraken.uk.domain.SiteOutage;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@AllArgsConstructor
@Component
public class SiteOutageExtractor {

    public List<SiteOutage> validSiteOutages(final SiteInfo siteInfo, final List<Outage> outages, final LocalDateTime cutOffTime) {
        final Map<String, String> siteDeviceInfo = getSiteDeviceInfo(siteInfo);
        return emptyIfNull(outages).stream()
                      .filter(isOutageBeginBefore(cutOffTime))
                      .filter(isOutageRelatedToSite(siteDeviceInfo))
                      .map(outage -> mapToSiteOutage(siteDeviceInfo.get(outage.getId()), outage))
                      .collect(Collectors.toList());
    }

    private Map<String, String> getSiteDeviceInfo(final SiteInfo siteInfo) {
        if (Objects.isNull(siteInfo) || CollectionUtils.isEmpty(siteInfo.getDevices())) {
            return new HashMap<>();
        }
        return siteInfo.getDevices()
                       .stream()
                       .collect(Collectors.toMap(Device::getId, Device::getName, (s, s2) -> s));
    }

    private Predicate<Outage> isOutageBeginBefore(final LocalDateTime beginTime) {
        return outage -> {
            final LocalDateTime outageBeginDateTime = LocalDateTime.parse(outage.getBegin(), DateTimeFormatter.ISO_DATE_TIME);
            return beginTime.isBefore(outageBeginDateTime) || beginTime.equals(outageBeginDateTime);
        };
    }

    private Predicate<Outage> isOutageRelatedToSite(final Map<String, String> siteDeviceMap) {
        return outage -> siteDeviceMap.containsKey(outage.getId());
    }

    private SiteOutage mapToSiteOutage(final String name, final Outage outage) {
        return SiteOutage.of()
                         .id(outage.getId())
                         .name(name)
                         .begin(outage.getBegin())
                         .end(outage.getEnd())
                         .build();
    }
}
