package com.dellpoc.steps;

import com.dellpoc.utils.WebUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GoogleSteps {

    @Given("I open the browser")
    public void iOpenTheBrowser() {
        WebUtils.openBrowser();
    }

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        WebUtils.navigate(url);
    }

    @Then("I get the page title")
    public void iGetThePageTitle() {
        WebUtils.getPageTitle();
    }

    @Then("I close the browser")
    public void iCloseTheBrowser() {
        WebUtils.closeBrowser();
    }
}