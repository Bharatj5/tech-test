package com.kraken.uk.common.stepdef;

import com.kraken.uk.common.utlis.ResultManager;
import io.cucumber.java8.En;
import io.cucumber.java8.StepDefinitionBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@Slf4j
public class commonRestStepDefs implements En {

    private final TestRestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ResultManager resultManager;

    @Autowired
    public commonRestStepDefs(final TestRestTemplate restTemplate, final ResultManager resultManager) {
        this.restTemplate = restTemplate;
        this.resultManager = resultManager;
        this.httpHeaders = new HttpHeaders();

        Before(resultManager::reset);

        stepDefinitions();

    }

    private void stepDefinitions() {

        When("^I make a \"([^\"]*)\" request on the path \"([^\"]*)\"$",
                (final String requestMethod, final String url) -> {
                    final HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
                    doRestCall(requestMethod, url, httpEntity);
                });

        Then("^I get the response code '(\\d+)'$",
                (final Integer responseCode) -> {
                    assertThat(resultManager.getStatusCode()).withFailMessage("Expected code matching with actual")
                                                             .isEqualTo(responseCode);
                });

        And("^I get a response body matching below json:$", (final String expectedJson) -> {
            final HttpEntity<String> actualJson = resultManager.getResponseEntity();
            final String actualJsonBody = actualJson.getBody();
            try {
                JSONAssert.assertEquals(expectedJson, actualJsonBody, false);
            } catch (final AssertionError error) {
                log.warn("response does not match. Expected :{}, Actual: {}", expectedJson, actualJsonBody);
                throw error;
            }
        });

        When("I wait {long} milliseconds", (StepDefinitionBody.A1<Long>) Thread::sleep);

    }

    private void doRestCall(final String requestMethod, final String url, final HttpEntity<String> httpEntity) {
        ResponseEntity<String> response = null;
        try {
            final HttpMethod httpMethod = HttpMethod.valueOf(requestMethod);
            response = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
        } catch (final HttpStatusCodeException e) {
            log.error("Request failed with exception: ", e);
            this.resultManager.setStatusCode(e.getRawStatusCode());
        } finally {
            this.resultManager.setStatusCode(response.getStatusCodeValue());
            this.resultManager.updateResponseEntity(response);
        }
    }

}
