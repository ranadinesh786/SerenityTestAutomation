package com.dellpoc.steps;

import com.dellpoc.utils.APIUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;

public class APISteps {

    private Response response;

    @Given("I set the base URI to {string}")
    public void setBaseURI(String baseURI) {
        APIUtils.setBaseURI(baseURI);
    }

    @When("I send a GET request to {string}")
    public void sendGETRequest(String endpoint) {
        response = APIUtils.sendGetRequest(endpoint);
    }

    @Then("the status code should be {int}")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain {string} with value {string}")
    public void verifyResponse(String key, String value) {
        response.then().body(key, equalTo(value));
    }
}
