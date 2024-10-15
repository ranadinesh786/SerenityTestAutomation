package com.dellpoc.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BaseTest extends PageObject {
    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected static WebDriver driver;

    @BeforeAll
    public static void globalSetup() {
        closeAllBrowsers();
    }

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        log.info("Browser opened");
        Serenity.recordReportData().withTitle("Browser opened").andContents("Browser opened successfully");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed");
            Serenity.recordReportData().withTitle("Browser closed").andContents("Browser closed successfully");
        }
    }

    @AfterAll
    public static void globalTearDown() {
        closeAllBrowsers();
    }

    private static void closeAllBrowsers() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
            log.info("Closed all existing browser instances");
        } catch (IOException e) {
            log.error("Error closing browser instances", e);
        }
    }
}
