package com.kraken.uk.service;

import com.kraken.uk.domain.SiteOutage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SiteOutageExtractorTest extends Stub {

    private SiteOutageExtractor classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new SiteOutageExtractor();
    }

    @ParameterizedTest
    @MethodSource("variousOutageTestCases")
    void validSiteOutages_matchingOutages_shouldReturnSiteOutages(final LocalDateTime cutoffTime, final List<SiteOutage> expectedSiteOutages) {

        final List<SiteOutage> actualSiteOutages = classUnderTest.validSiteOutages(getSiteInfo(), getOutages(), cutoffTime);

        assertThat(actualSiteOutages).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedSiteOutages);
    }

    private static Stream<Arguments> variousOutageTestCases() {
        return Stream.of(
                Arguments.of(BEGIN_TIME_STAMP, getExpectedSiteOutage()),
                Arguments.of(BEGIN_TIME_STAMP.minusMinutes(10), Stream.of(getExpectedSiteOutage(), getBorderTimeStampSiteOutage()).flatMap(Collection::stream).collect(Collectors.toList()))
        );
    }

    @Test
    void validSiteOutages_beginTimeIsGreater_noSiteOutageReturned() {

        final List<SiteOutage> actualSiteOutages = classUnderTest.validSiteOutages(getSiteInfo(), getOutages(), BEGIN_TIME_STAMP.plusYears(5));

        assertThat(actualSiteOutages).isEmpty();
    }
}