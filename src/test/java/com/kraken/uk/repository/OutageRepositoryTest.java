package com.kraken.uk.repository;

import com.kraken.uk.domain.Outage;
import com.kraken.uk.exception.NotFoundException;
import com.kraken.uk.properties.MicroservicesProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutageRepositoryTest {

    private static final String TEST_API_URL = "http://test.api.krakenflex.systems/v1/outages";
    @InjectMocks
    private OutageRepository classUnderTest;
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
    void getReportedOutage_happyPath_returnedAllOutage() {
        //Given
        final List<Outage> outages = podamFactory.manufacturePojo(ArrayList.class, Outage.class);
        when(mockMicroservicesProperties.getOutageUri()).thenReturn(TEST_API_URL);
        when(mockExternalRestTemplate.getForObject(TEST_API_URL, Outage[].class)).thenReturn(outages.toArray(new Outage[0]));

        //When
        final List<Outage> actualResponse = classUnderTest.getReportedOutage();

        //Then
        assertThat(actualResponse).isNotEmpty();
        assertThat(actualResponse).usingRecursiveComparison()
                                  .isEqualTo(outages);
        verify(mockMicroservicesProperties, times(1)).getOutageUri();
        verify(mockExternalRestTemplate, times(1)).getForObject(TEST_API_URL, Outage[].class);
        verifyNoMoreInteractions(mockMicroservicesProperties, mockExternalRestTemplate);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void getReportedOutage_emptyResponse_customExceptionThrown(final Outage[] outages) {
        when(mockMicroservicesProperties.getOutageUri()).thenReturn(TEST_API_URL);
        when(mockExternalRestTemplate.getForObject(TEST_API_URL, Outage[].class)).thenReturn(outages);

        assertThatThrownBy(() -> classUnderTest.getReportedOutage()).isInstanceOf(NotFoundException.class);

        verify(mockMicroservicesProperties, times(1)).getOutageUri();
        verify(mockExternalRestTemplate, times(1)).getForObject(TEST_API_URL, Outage[].class);
        verifyNoMoreInteractions(mockMicroservicesProperties, mockExternalRestTemplate);
    }



}