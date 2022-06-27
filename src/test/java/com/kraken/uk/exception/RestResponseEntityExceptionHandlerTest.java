package com.kraken.uk.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class RestResponseEntityExceptionHandlerTest {

    private RestResponseEntityExceptionHandler classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new RestResponseEntityExceptionHandler();
    }

    @Test
    void handleServerErrors_happyPath_objectCreated() {
        assertThat(classUnderTest.handleServerErrors(new ApiCommunicationException("error")).getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleNotFound_happyPath_objectCreated() {
        assertThat(classUnderTest.handleNotFound(new NotFoundException()).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


}