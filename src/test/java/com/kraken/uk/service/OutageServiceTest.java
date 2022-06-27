package com.kraken.uk.service;

import com.kraken.uk.domain.Outage;
import com.kraken.uk.exception.ApiCommunicationException;
import com.kraken.uk.repository.OutageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutageServiceTest {

    private static final String SITE_ID = "TEST_SITE_ID";
    private static final String ERROR_MESSAGE = "Something wrong";

    @InjectMocks
    private OutageService classUnderTest;
    @Mock
    private OutageRepository mockOutageRepository;
    private PodamFactory podamFactory;

    @BeforeEach
    void setUp() {
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void getReportedOutage_happyPath_shouldReturnOutages() {
        final List<Outage> outages = podamFactory.manufacturePojo(ArrayList.class, Outage.class);
        when(mockOutageRepository.getReportedOutage()).thenReturn(outages);

        final List<Outage> actualReportedOutage = classUnderTest.getReportedOutage();

        assertThat(actualReportedOutage).usingRecursiveComparison()
                                        .isEqualTo(outages);
        verify(mockOutageRepository, times(1)).getReportedOutage();
        verifyNoMoreInteractions(mockOutageRepository);
    }

    @Test
    void getReportedOutage_sadPath_exceptionThrown() {
        when(mockOutageRepository.getReportedOutage()).thenThrow(new ApiCommunicationException(ERROR_MESSAGE));

        assertThatThrownBy(() -> classUnderTest.getReportedOutage()).isInstanceOf(ApiCommunicationException.class)
                                                                    .hasMessage(ERROR_MESSAGE);

        verify(mockOutageRepository, times(1)).getReportedOutage();
        verifyNoMoreInteractions(mockOutageRepository);
    }


}