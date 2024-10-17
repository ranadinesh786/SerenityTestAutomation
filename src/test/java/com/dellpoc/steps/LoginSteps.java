package com.dellpoc.steps;

import com.dellpoc.utils.EnvironmentUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.Serenity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    private WebDriver driver;

    @Given("I open the login page")
    public void i_open_the_login_page() {
        driver = new ChromeDriver();
        driver.get("https://example.com/login");
    }

    @When("I login with valid credentials")
    public void i_login_with_valid_credentials() {
        // Load credentials from environment properties
        String dbUsername = EnvironmentUtils.getProperty("db.username");
        String dbPassword = EnvironmentUtils.getProperty("db.password");

        // Use credentials in your Selenium tests
        // Example: Login to a web application
        driver.findElement(By.id("username")).sendKeys(dbUsername);
        driver.findElement(By.id("password")).sendKeys(dbPassword);
        driver.findElement(By.id("loginButton")).click();
    }

    @Then("I should see the dashboard")
    public void i_should_see_the_dashboard() {
        // Add assertions to verify the dashboard is displayed
        // Example: Assert that the dashboard element is present
        assertTrue(driver.findElement(By.id("dashboard")).isDisplayed());

        // Close the browser
        driver.quit();
    }
}
