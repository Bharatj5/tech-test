package com.kraken.uk.service;

import com.kraken.uk.domain.Outage;
import com.kraken.uk.domain.SiteInfo;
import com.kraken.uk.domain.SiteOutage;
import com.kraken.uk.dto.SiteOutagePostResponseDto;
import com.kraken.uk.exception.ApiCommunicationException;
import com.kraken.uk.exception.NotFoundException;
import com.kraken.uk.repository.SiteOutageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.kraken.uk.service.SiteOutageService.DEFAULT_BEGIN_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SiteOutageServiceTest extends Stub {

    @InjectMocks
    private SiteOutageService classUnderTest;
    @Mock
    private OutageService mockOutageService;
    @Mock
    private SiteService mockSiteService;
    @Mock
    private SiteOutageExtractor mockSiteOutageExtractor;
    @Mock
    private SiteOutageRepository mockSiteOutageRepository;


    @Test
    void reportSiteOutage_happyPath_shouldPostOutages() {
        final List<Outage> outages = getOutages();
        final SiteInfo siteInfo = getSiteInfo();
        final List<SiteOutage> extractedOutage = getExpectedSiteOutage();
        when(mockOutageService.getReportedOutage()).thenReturn(outages);
        when(mockSiteService.getSiteInfo(SITE_ID)).thenReturn(siteInfo);
        when(mockSiteOutageExtractor.validSiteOutages(siteInfo, outages, DEFAULT_BEGIN_TIME)).thenReturn(extractedOutage);
        final SiteOutagePostResponseDto actualResponse = classUnderTest.reportSiteOutage(SITE_ID);

        assertThat(actualResponse.getNoOfOutageReported()).isEqualTo(2);
        assertThat(actualResponse.getReportedAt()).isNotNull();
        verify(mockOutageService).getReportedOutage();
        verify(mockSiteService).getSiteInfo(SITE_ID);
        verify(mockSiteOutageRepository).postOutages(SITE_ID, extractedOutage);
        verify(mockSiteOutageExtractor).validSiteOutages(siteInfo, outages, DEFAULT_BEGIN_TIME);
    }


    @Test
    void reportSiteOutage_noOutages_notFoundExceptionThrown() {
        final List<Outage> outages = getOutages();
        final SiteInfo siteInfo = getSiteInfo();
        siteInfo.setDevices(Collections.singletonList(siteInfo.getDevices().get(2))); // random device
        final List<SiteOutage> extractedOutage = new ArrayList<>();
        when(mockOutageService.getReportedOutage()).thenReturn(outages);
        when(mockSiteService.getSiteInfo(SITE_ID)).thenReturn(siteInfo);
        when(mockSiteOutageExtractor.validSiteOutages(siteInfo, outages, DEFAULT_BEGIN_TIME)).thenReturn(extractedOutage);


        assertThatThrownBy(() -> classUnderTest.reportSiteOutage(SITE_ID)).isInstanceOf(NotFoundException.class);


        verify(mockOutageService).getReportedOutage();
        verify(mockSiteService).getSiteInfo(SITE_ID);
        verify(mockSiteOutageExtractor).validSiteOutages(siteInfo, outages, DEFAULT_BEGIN_TIME);
        verifyNoInteractions(mockSiteOutageRepository);
    }


    @Test
    void reportSiteOutage_sadPath_exceptionThrown() {
        when(mockOutageService.getReportedOutage()).thenThrow(new ApiCommunicationException());

        assertThatThrownBy(() -> classUnderTest.reportSiteOutage(SITE_ID)).isInstanceOf(ApiCommunicationException.class);
        verify(mockOutageService).getReportedOutage();
        verifyNoInteractions(mockSiteService, mockSiteOutageExtractor);
    }


}