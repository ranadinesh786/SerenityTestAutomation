package com.dellpoc.steps;

import com.dellpoc.utils.DBUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBSteps {

    private ResultSet resultSet;

    @Given("I connect to the Oracle database with URL {string}, user {string}, and password {string}")
    public void connectToDB(String url, String user, String password) throws SQLException {
        DBUtils.connectToDB(url, user, password);
    }

    @When("I execute the query {string}")
    public void executeQuery(String query) throws SQLException {
        resultSet = DBUtils.executeQuery(query);
    }

    @Then("the result should contain a column {string} with value {string}")
    public void verifyResult(String column, String value) throws SQLException {
        boolean found = false;
        while (resultSet.next()) {
            if (resultSet.getString(column).equals(value)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected value not found in the result set", found);
    }

    @Then("I close the database connection")
    public void closeConnection() throws SQLException {
        DBUtils.closeConnection();
    }
}
