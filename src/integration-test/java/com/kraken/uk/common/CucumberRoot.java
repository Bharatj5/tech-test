package com.kraken.uk.common;

import com.kraken.uk.TechTestApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@CucumberContextConfiguration
@SpringBootTest(classes = TechTestApplication.class, webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 0, stubs = "classpath:/stubs")
@ActiveProfiles("integration")
@RequiredArgsConstructor
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
public class CucumberRoot {

    protected final TestRestTemplate restTemplate;

    @Before
    public void before() {
        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList(
                (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> execution.execute(request, body)));
    }

}
