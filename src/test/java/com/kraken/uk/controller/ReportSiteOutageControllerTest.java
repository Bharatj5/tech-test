package com.kraken.uk.controller;

import com.kraken.uk.dto.SiteOutagePostResponseDto;
import com.kraken.uk.exception.ApiCommunicationException;
import com.kraken.uk.service.SiteOutageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportSiteOutageControllerTest {

    private static final String SITE_ID = "ninja-site";
    @InjectMocks
    private ReportSiteOutageController classUnderTest;
    @Mock
    private SiteOutageService mockSiteOutageService;

    @Test
    void reportSiteOutage_happyPath_responseReturned() {
        final SiteOutagePostResponseDto response = getSuccessfulResponse();
        when(mockSiteOutageService.reportSiteOutage(SITE_ID)).thenReturn(response);

        final SiteOutagePostResponseDto actualResponse = classUnderTest.reportSiteOutage(SITE_ID);

        assertThat(actualResponse).isEqualTo(response);
        verify(mockSiteOutageService).reportSiteOutage(SITE_ID);
    }

    @Test
    void reportSiteOutage_sadPath_exceptionThrown() {
        when(mockSiteOutageService.reportSiteOutage(SITE_ID)).thenThrow(new ApiCommunicationException());

        assertThatThrownBy(() -> classUnderTest.reportSiteOutage(SITE_ID)).isInstanceOf(ApiCommunicationException.class);

        verify(mockSiteOutageService).reportSiteOutage(SITE_ID);
    }

    private SiteOutagePostResponseDto getSuccessfulResponse() {
        return SiteOutagePostResponseDto.of()
                                        .noOfOutageReported(5)
                                        .reportedAt(LocalDateTime.now())
                                        .build();
    }
}