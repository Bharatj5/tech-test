package com.kraken.uk.repository;

import com.kraken.uk.domain.SiteOutage;
import com.kraken.uk.domain.SiteOutagePostResponse;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SiteOutageRepositoryTest {

    private static final String TEST_API_URL = "http://test.api.krakenflex.systems/v1/outages";
    @InjectMocks
    private SiteOutageRepository classUnderTest;
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
    void postOutages_happyPath_outagesPosted() {
        //Given
        final String siteId = "ninja-site";
        final List<SiteOutage> siteOutages = podamFactory.manufacturePojo(ArrayList.class, SiteOutage.class);
        final SiteOutagePostResponse siteOutagePostResponse = new SiteOutagePostResponse();
        when(mockMicroservicesProperties.getSiteOutageUrl()).thenReturn(TEST_API_URL);
        when(mockExternalRestTemplate.postForObject(TEST_API_URL, siteOutages, SiteOutagePostResponse.class, siteId)).thenReturn(siteOutagePostResponse);

        //When
        classUnderTest.postOutages(siteId, siteOutages);

        //Then
        verify(mockMicroservicesProperties, times(1)).getSiteOutageUrl();
        verify(mockExternalRestTemplate, times(1)).postForObject(TEST_API_URL, siteOutages, SiteOutagePostResponse.class, siteId);
        verifyNoMoreInteractions(mockMicroservicesProperties, mockExternalRestTemplate);
    }
}