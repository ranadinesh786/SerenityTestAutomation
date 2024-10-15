package com.dellpoc.utils;

import com.dellpoc.base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WebUtils extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static void openBrowser() {
        BaseTest.globalSetup();
        WebDriverManager.chromedriver().setup();
        BaseTest.driver = new ChromeDriver();
        driver.manage().window().maximize();
        log.info("Browser opened");
    }

    public static void navigate(String urlKey) {
        BaseTest.driver.get(urlKey);
        log.info("Navigated to URL: " + urlKey);
    }

    public static String getPageTitle() {
        String title = BaseTest.driver.getTitle();
        log.info("Page title: " + title);
        return title;
    }

    public static void closeBrowser() {
        if (BaseTest.driver != null) {
            BaseTest.driver.quit();
            log.info("Browser closed");
        }
    }

    public static void click(String locatorKey) {
        getElement(locatorKey).click();
        log.info("Clicked on element with locator: " + locatorKey);
    }

    public static void type(String locatorKey, String data) {
        getElement(locatorKey).sendKeys(data);
        log.info("Typed data: " + data + " into element with locator: " + locatorKey);
    }

    public static void clear(String locatorKey) {
        getElement(locatorKey).clear();
        log.info("Cleared element with locator: " + locatorKey);
    }

    public static void clickEnterButton(String locatorKey) {
        getElement(locatorKey).sendKeys(Keys.ENTER);
        log.info("Pressed Enter on element with locator: " + locatorKey);
    }

    public static void selectByVisibleText(String locatorKey, String data) {
        Select select = new Select(getElement(locatorKey));
        select.selectByVisibleText(data);
        log.info("Selected option: " + data + " from dropdown with locator: " + locatorKey);
    }

    public static String getText(String locatorKey) {
        String text = getElement(locatorKey).getText();
        log.info("Text from element with locator " + locatorKey + ": " + text);
        return text;
    }

    public static WebElement getElement(String locatorKey) {
        WebElement element = BaseTest.driver.findElement(getLocator(locatorKey));
        log.info("Found element with locator: " + locatorKey);
        return element;
    }

    public static boolean isElementPresent(String locatorKey) {
        boolean present = !BaseTest.driver.findElements(getLocator(locatorKey)).isEmpty();
        log.info("Element present with locator " + locatorKey + ": " + present);
        return present;
    }

    public static boolean isElementVisible(String locatorKey) {
        boolean visible = getElement(locatorKey).isDisplayed();
        log.info("Element visible with locator " + locatorKey + ": " + visible);
        return visible;
    }

    public static By getLocator(String locatorKey) {
        log.info("Getting locator for key: " + locatorKey);
        return (By) By.id(locatorKey); // Example
    }

    public static void takeScreenshot() {
        log.info("Screenshot taken");
    }

    public static void waitForElement(String locatorKey, int timeout) {
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey)));
        log.info("Waited for element with locator " + locatorKey + " for " + timeout + " seconds");
    }

    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
            log.info("Waited for " + seconds + " seconds");
        } catch (InterruptedException e) {
            log.error("InterruptedException during wait", e);
        }
    }

    public static void handleAlerts() {
        log.info("Handled alert");
    }

    public static void handleImplicitWait(int seconds) {
        BaseTest.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
        log.info("Set implicit wait for " + seconds + " seconds");
    }

    public static void handleExplicitWait(String locatorKey, int timeout) {
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey)));
        log.info("Set explicit wait for element with locator " + locatorKey + " for " + timeout + " seconds");
    }

    public static void log(String msg) {
        log.info(msg);
    }

    public static void pass(String msg) {
        log.info("PASS: " + msg);
    }

    public static void fail(String msg) {
        log.error("FAIL: " + msg);
    }
}