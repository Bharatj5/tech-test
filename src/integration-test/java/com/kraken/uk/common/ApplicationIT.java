package com.kraken.uk.common;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        tags = "not @wip",
        features = {"src/integration-test/resources/features"},
        plugin = {"pretty"},
        extraGlue = {"com.kraken.uk.common.stepdef"}
)
@RunWith(Cucumber.class)
public class ApplicationIT {

}
