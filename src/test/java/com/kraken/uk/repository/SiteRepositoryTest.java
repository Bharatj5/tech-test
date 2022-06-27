package com.kraken.uk.repository;

import com.kraken.uk.domain.SiteInfo;
import com.kraken.uk.exception.ApiCommunicationException;
import com.kraken.uk.properties.MicroservicesProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SiteRepositoryTest {

    private static final String TEST_API_URL = "http://test.api.krakenflex.systems/v1/outages";
    private static final String SITE_ID = "TEST_SITE_ID";

    @InjectMocks
    private SiteRepository classUnderTest;
    @Mock
    private RestTemplate mockExternalRestTemplate;
    @Mock
    private MicroservicesProperties mockMicroservicesProperties;
    private PodamFactory podamFactory;

    @BeforeEach
    void setUp() {
        podamFactory = new PodamFactoryImpl();
    }


    @Test
    void getSiteInfo_happyPath_siteInfoReturned() {
        final SiteInfo siteInfo = podamFactory.manufacturePojo(SiteInfo.class);
        when(mockMicroservicesProperties.getSiteInfoUrl()).thenReturn(TEST_API_URL);
        when(mockExternalRestTemplate.getForObject(TEST_API_URL, SiteInfo.class, SITE_ID)).thenReturn(siteInfo);

        final SiteInfo actualSiteInfo = classUnderTest.getSiteInfo(SITE_ID);

        assertThat(actualSiteInfo).isEqualTo(siteInfo);
        verify(mockMicroservicesProperties, times(1)).getSiteInfoUrl();
        verify(mockExternalRestTemplate, times(1)).getForObject(TEST_API_URL, SiteInfo.class, SITE_ID);
        verifyNoMoreInteractions(mockMicroservicesProperties, mockExternalRestTemplate);
    }

    @Test
    void getSiteInfo_sadPath_exceptionThrown() {
        final String errorMessage = "Something wrong";
        when(mockMicroservicesProperties.getSiteInfoUrl()).thenReturn(TEST_API_URL);
        when(mockExternalRestTemplate.getForObject(TEST_API_URL, SiteInfo.class, SITE_ID)).thenThrow(new ApiCommunicationException(errorMessage));

        assertThatThrownBy(() -> classUnderTest.getSiteInfo(SITE_ID)).isInstanceOf(ApiCommunicationException.class)
                                                                     .hasMessage(errorMessage);

        verify(mockMicroservicesProperties, times(1)).getSiteInfoUrl();
        verify(mockExternalRestTemplate, times(1)).getForObject(TEST_API_URL, SiteInfo.class, SITE_ID);
        verifyNoMoreInteractions(mockMicroservicesProperties, mockExternalRestTemplate);
    }
}