package com.kraken.uk.service;

import com.kraken.uk.domain.SiteInfo;
import com.kraken.uk.exception.ApiCommunicationException;
import com.kraken.uk.repository.SiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SiteServiceTest {

    private static final String SITE_ID = "TEST_SITE_ID";

    @InjectMocks
    private SiteService classUnderTest;
    @Mock
    private SiteRepository mockSiteRepository;
    private PodamFactory podamFactory;

    @BeforeEach
    void setUp() {
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void getSiteInfo_happyPath_shouldReturnSiteInfo() {
        final SiteInfo expectedSiteInfo = podamFactory.manufacturePojo(SiteInfo.class);
        when(mockSiteRepository.getSiteInfo(SITE_ID)).thenReturn(expectedSiteInfo);

        final SiteInfo actualSiteInfo = classUnderTest.getSiteInfo(SITE_ID);

        assertThat(actualSiteInfo).isEqualTo(expectedSiteInfo);
        verify(mockSiteRepository, times(1)).getSiteInfo(SITE_ID);
        verifyNoMoreInteractions(mockSiteRepository);
    }

    @Test
    void getSiteInfo_sadPath_shouldReturnSiteInfo() {
        final String errorMessage = "Something wrong";
        when(mockSiteRepository.getSiteInfo(SITE_ID)).thenThrow(new ApiCommunicationException(errorMessage));

        assertThatThrownBy(() -> classUnderTest.getSiteInfo(SITE_ID)).isInstanceOf(ApiCommunicationException.class)
                                                                     .hasMessage(errorMessage);

        verify(mockSiteRepository, times(1)).getSiteInfo(SITE_ID);
        verifyNoMoreInteractions(mockSiteRepository);
    }
}