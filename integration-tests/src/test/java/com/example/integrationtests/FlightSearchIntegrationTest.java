package com.example.integrationtests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/flight_search_integration.feature",
        glue = "com/example/integrationtests/glue")
public class FlightSearchIntegrationTest {
}
