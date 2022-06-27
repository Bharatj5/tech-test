package com.kraken.uk.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiCommunicationExceptionTest {

    @Test
    void apiCommunicationException_exceptionOccurred_exceptionCreated() {
        assertThat(new ApiCommunicationException("exception Message")).isNotNull();
    }
}