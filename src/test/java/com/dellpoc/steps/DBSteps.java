package com.dellpoc.steps;

import com.dellpoc.utils.DBUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.ResultSet;

import static org.junit.Assert.assertTrue;

public class DBSteps {
    private ResultSet resultSet;

    @Given("I connect to the database")
    public void connectToDatabase() throws Exception {
        DBUtils.connectToDatabase();
    }

    @When("I execute the query {string}")
    public void executeQuery(String query) throws Exception {
        resultSet = DBUtils.executeQuery(query);
    }

    @Then("the result should contain {string}")
    public void verifyResult(String expectedValue) throws Exception {
        boolean found = false;
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(expectedValue)) {
                found = true;
                break;
            }
        }
        assertTrue("Expected value not found in the result set", found);
        DBUtils.closeConnection();
    }
}
