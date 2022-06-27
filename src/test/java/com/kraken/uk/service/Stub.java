package com.kraken.uk.service;

import com.kraken.uk.domain.Device;
import com.kraken.uk.domain.Outage;
import com.kraken.uk.domain.SiteInfo;
import com.kraken.uk.domain.SiteOutage;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;

public class Stub {

    protected static final String SITE_ID = "Kingfisher";
    protected static final String SITE_ID_MATCHED_1 = randomUUID().toString();
    protected static final String SITE_ID_MATCHED_1_DEVICE_NAME = "Battery 2";
    protected static final String SITE_ID_MATCHED_2 = randomUUID().toString();
    protected static final String SITE_ID_MATCHED_2_DEVICE_NAME = "Battery 5";

    protected static final LocalDateTime BEGIN_TIME_STAMP = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0);
    protected static final LocalDateTime END_TIME_STAMP = LocalDateTime.of(2022, Month.DECEMBER, 1, 0, 0);

    protected static List<SiteOutage> getExpectedSiteOutage() {
        return asList(SiteOutage.of()
                                .id(SITE_ID_MATCHED_1)
                                .name(SITE_ID_MATCHED_1_DEVICE_NAME)
                                .begin(BEGIN_TIME_STAMP.toString())
                                .end(END_TIME_STAMP.toString())
                                .build(),
                SiteOutage.of()
                          .id(SITE_ID_MATCHED_1)
                          .name(SITE_ID_MATCHED_1_DEVICE_NAME)
                          .begin(BEGIN_TIME_STAMP.plusDays(1)
                                                 .toString())
                          .end(END_TIME_STAMP.toString())
                          .build());
    }

    protected static List<SiteOutage> getBorderTimeStampSiteOutage() {
        return asList(SiteOutage.of()
                                .id(SITE_ID_MATCHED_1)
                                .name(SITE_ID_MATCHED_1_DEVICE_NAME)
                                .begin(BEGIN_TIME_STAMP.minusSeconds(1)
                                                       .toString())
                                .end(END_TIME_STAMP.toString())
                                .build(),
                SiteOutage.of()
                          .id(SITE_ID_MATCHED_2)
                          .name(SITE_ID_MATCHED_2_DEVICE_NAME)
                          .begin(BEGIN_TIME_STAMP.minusSeconds(1)
                                                 .toString())
                          .end(END_TIME_STAMP.toString())
                          .build());
    }

    protected SiteInfo getSiteInfo() {
        final SiteInfo siteInfo = new SiteInfo();
        siteInfo.setId(SITE_ID);
        siteInfo.setName(SITE_ID);
        siteInfo.setDevices(getDevices());
        return siteInfo;
    }

    private List<Device> getDevices() {
        return asList(Device.of()
                            .id(SITE_ID_MATCHED_1)
                            .name(SITE_ID_MATCHED_1_DEVICE_NAME)
                            .build(),
                Device.of()
                      .id(SITE_ID_MATCHED_2)
                      .name(SITE_ID_MATCHED_2_DEVICE_NAME)
                      .build(),
                Device.of()
                      .id(randomUUID().toString())
                      .name("Battery 10")
                      .build());
    }

    protected List<Outage> getOutages() {
        return Stream.of(getMatchedButBefore1stJan2022(), getTwoOutOfThreeAfter1stJan2022(), getUnmatchedSiteOutages())
                     .flatMap(Collection::stream)
                     .collect(Collectors.toList());
    }

    private List<Outage> getTwoOutOfThreeAfter1stJan2022() {
        return asList(new Outage(SITE_ID_MATCHED_1, BEGIN_TIME_STAMP.toString(), END_TIME_STAMP.toString()),
                new Outage(SITE_ID_MATCHED_1, BEGIN_TIME_STAMP.plusDays(1)
                                                              .toString(), END_TIME_STAMP.toString()),
                new Outage(SITE_ID_MATCHED_1, BEGIN_TIME_STAMP.minusSeconds(1)
                                                              .toString(), END_TIME_STAMP.toString()));
    }

    private List<Outage> getMatchedButBefore1stJan2022() {
        return asList(new Outage(SITE_ID_MATCHED_2, BEGIN_TIME_STAMP.minusSeconds(1)
                                                                    .toString(), END_TIME_STAMP.toString()),
                new Outage(SITE_ID_MATCHED_2, BEGIN_TIME_STAMP.minusYears(2)
                                                              .toString(), END_TIME_STAMP.toString()));
    }


    private List<Outage> getUnmatchedSiteOutages() {
        return asList(new Outage(randomUUID().toString(), BEGIN_TIME_STAMP.plusMinutes(12)
                                                                          .toString(), END_TIME_STAMP.toString()),
                new Outage(randomUUID().toString(), BEGIN_TIME_STAMP.plusDays(40)
                                                                    .toString(), END_TIME_STAMP.toString()));
    }
}
