package com.dellpoc.runners;

import com.dellpoc.base.BaseTest;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.dellpoc.steps", "com.dellpoc.stepdefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        dryRun = false,
        monochrome = true
)
public class SimpleRun extends BaseTest {
}
