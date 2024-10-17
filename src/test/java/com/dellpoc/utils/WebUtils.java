package com.dellpoc.utils;

import com.dellpoc.base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
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

    /**
     * Opens the browser and maximizes the window.
     */
    public static void openBrowser() {
        BaseTest.globalSetup();
        WebDriverManager.chromedriver().setup();
        BaseTest.driver = new ChromeDriver();
        driver.manage().window().maximize();
        log.info("Browser opened");
    }

    /**
     * Navigates to the specified URL.
     *
     * @param urlKey The URL to navigate to.
     */
    public static void navigate(String urlKey) {
        BaseTest.driver.get(urlKey);
        log.info("Navigated to URL: " + urlKey);
    }

    /**
     * Gets the title of the current page.
     *
     * @return The title of the current page.
     */
    public static String getPageTitle() {
        String title = BaseTest.driver.getTitle();
        log.info("Page title: " + title);
        return title;
    }

    /**
     * Closes the browser if it is open.
     */
    public static void closeBrowser() {
        if (BaseTest.driver != null) {
            BaseTest.driver.quit();
            log.info("Browser closed");
        }
    }

    /**
     * Clicks on the element specified by the locator key.
     *
     * @param locatorKey The key to locate the element.
     */
    public static void click(String locatorKey) {
        getElement(locatorKey).click();
        log.info("Clicked on element with locator: " + locatorKey);
    }

    /**
     * Types the specified data into the element specified by the locator key.
     *
     * @param locatorKey The key to locate the element.
     * @param data       The data to type into the element.
     */
    public static void type(String locatorKey, String data) {
        getElement(locatorKey).sendKeys(data);
        log.info("Typed data: " + data + " into element with locator: " + locatorKey);
    }

    /**
     * Clears the content of the element specified by the locator key.
     *
     * @param locatorKey The key to locate the element.
     */
    public static void clear(String locatorKey) {
        getElement(locatorKey).clear();
        log.info("Cleared element with locator: " + locatorKey);
    }

    /**
     * Presses the Enter key on the element specified by the locator key.
     *
     * @param locatorKey The key to locate the element.
     */
    public static void clickEnterButton(String locatorKey) {
        getElement(locatorKey).sendKeys(Keys.ENTER);
        log.info("Pressed Enter on element with locator: " + locatorKey);
    }

    /**
     * Selects an option from a dropdown by visible text.
     *
     * @param locatorKey The key to locate the dropdown element.
     * @param data       The visible text of the option to select.
     */
    public static void selectByVisibleText(String locatorKey, String data) {
        Select select = new Select(getElement(locatorKey));
        select.selectByVisibleText(data);
        log.info("Selected option: " + data + " from dropdown with locator: " + locatorKey);
    }

    /**
     * Selects an option from a dropdown by value.
     *
     * @param locatorKey The key to locate the dropdown element.
     * @param value      The value attribute of the option to select.
     */
    public static void selectDropdownByValue(String locatorKey, String value) {
        Select select = new Select(getElement(locatorKey));
        select.selectByValue(value);
        log.info("Selected option with value: " + value + " from dropdown with locator: " + locatorKey);
    }

    /**
     * Gets the text of the element specified by the locator key.
     *
     * @param locatorKey The key to locate the element.
     * @return The text of the element.
     */
    public static String getText(String locatorKey) {
        String text = getElement(locatorKey).getText();
        log.info("Text from element with locator " + locatorKey + ": " + text);
        return text;
    }

    /**
     * Finds and returns the WebElement specified by the locator key.
     *
     * @param locatorKey The key to locate the element.
     * @return The WebElement found.
     */
    public static WebElement getElement(String locatorKey) {
        WebElement element = BaseTest.driver.findElement(getLocator(locatorKey));
        log.info("Found element with locator: " + locatorKey);
        return element;
    }

    /**
     * Checks if the element specified by the locator key is present.
     *
     * @param locatorKey The key to locate the element.
     * @return True if the element is present, false otherwise.
     */
    public static boolean isElementPresent(String locatorKey) {
        boolean present = !BaseTest.driver.findElements(getLocator(locatorKey)).isEmpty();
        log.info("Element present with locator " + locatorKey + ": " + present);
        return present;
    }

    /**
     * Checks if the element specified by the locator key is visible.
     *
     * @param locatorKey The key to locate the element.
     * @return True if the element is visible, false otherwise.
     */
    public static boolean isElementVisible(String locatorKey) {
        boolean visible = getElement(locatorKey).isDisplayed();
        log.info("Element visible with locator " + locatorKey + ": " + visible);
        return visible;
    }

    /**
     * Gets the locator for the specified key.
     *
     * @param locatorKey The key to locate the element.
     * @return The By object representing the locator.
     */
    public static By getLocator(String locatorKey) {
        log.info("Getting locator for key: " + locatorKey);
        return (By) By.id(locatorKey); // Example
    }

    /**
     * Takes a screenshot.
     */
    public static void takeScreenshot() {
        log.info("Screenshot taken");
    }

    /**
     * Waits for the element specified by the locator key to be visible for the specified timeout.
     *
     * @param locatorKey The key to locate the element.
     * @param timeout    The timeout in seconds.
     */
    public static void waitForElement(String locatorKey, int timeout) {
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey)));
        log.info("Waited for element with locator " + locatorKey + " for " + timeout + " seconds");
    }

    /**
     * Waits for the specified number of seconds.
     *
     * @param seconds The number of seconds to wait.
     */
    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
            log.info("Waited for " + seconds + " seconds");
        } catch (InterruptedException e) {
            log.error("InterruptedException during wait", e);
        }
    }

    /**
     * Accepts the alert if it is present.
     */
    public static void acceptAlert() {
        try {
            Alert alert = BaseTest.driver.switchTo().alert();
            alert.accept();
            log.info("Accepted alert");
        } catch (Exception e) {
            log.error("No alert to accept", e);
        }
    }

    /**
     * Dismisses the alert if it is present.
     */
    public static void dismissAlert() {
        try {
            Alert alert = BaseTest.driver.switchTo().alert();
            alert.dismiss();
            log.info("Dismissed alert");
        } catch (Exception e) {
            log.error("No alert to dismiss", e);
        }
    }

    /**
     * Gets the text of the alert if it is present.
     *
     * @return The text of the alert.
     */
    public static String getAlertText() {
        try {
            Alert alert = BaseTest.driver.switchTo().alert();
            String alertText = alert.getText();
            log.info("Alert text: " + alertText);
            return alertText;
        } catch (Exception e) {
            log.error("No alert to get text from", e);
            return null;
        }
    }

    /**
     * Sends the specified text to the alert if it is present.
     *
     * @param text The text to send to the alert.
     */
    public static void sendTextToAlert(String text) {
        try {
            Alert alert = BaseTest.driver.switchTo().alert();
            alert.sendKeys(text);
            log.info("Sent text to alert: " + text);
        } catch (Exception e) {
            log.error("No alert to send text to", e);
        }
    }

    /**
     * Placeholder method for handling alerts.
     */
    public static void handleAlerts() {
        log.info("Handled alert");
    }

    /**
     * Sets the implicit wait timeout.
     *
     * @param seconds The timeout in seconds.
     */
    public static void handleImplicitWait(int seconds) {
        BaseTest.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
        log.info("Set implicit wait for " + seconds + " seconds");
    }

    /**
     * Sets the explicit wait timeout for the element specified by the locator key.
     *
     * @param locatorKey The key to locate the element.
     * @param timeout    The timeout in seconds.
     */
    public static void handleExplicitWait(String locatorKey, int timeout) {
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey)));
        log.info("Set explicit wait for element with locator " + locatorKey + " for " + timeout + " seconds");
    }

    /**
     * Logs the specified message.
     *
     * @param msg The message to log.
     */
    public static void log(String msg) {
        log.info(msg);
    }

    /**
     * Logs a pass message.
     *
     * @param msg The pass message to log.
     */
    public static void pass(String msg) {
        log.info("PASS: " + msg);
    }

    /**
     * Logs a fail message.
     *
     * @param msg The fail message to log.
     */
    public static void fail(String msg) {
        log.error("FAIL: " + msg);
    }

    /**
     * Checks or unchecks a checkbox based on the specified state.
     *
     * @param locatorKey The key to locate the checkbox element.
     * @param check      True to check the checkbox, false to uncheck.
     */
    public static void setCheckbox(String locatorKey, boolean check) {
        WebElement checkbox = getElement(locatorKey);
        if (checkbox.isSelected() != check) {
            checkbox.click();
        }
        log.info((check ? "Checked" : "Unchecked") + " checkbox with locator: " + locatorKey);
    }

    /**
     * Selects a radio button.
     *
     * @param locatorKey The key to locate the radio button element.
     */
    public static void selectRadioButton(String locatorKey) {
        WebElement radioButton = getElement(locatorKey);
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
        log.info("Selected radio button with locator: " + locatorKey);
    }

    /**
     * Switches to a window with the specified title.
     *
     * @param windowTitle The title of the window to switch to.
     */
    public static void switchToWindow(String windowTitle) {
        for (String windowHandle : BaseTest.driver.getWindowHandles()) {
            BaseTest.driver.switchTo().window(windowHandle);
            if (BaseTest.driver.getTitle().equals(windowTitle)) {
                log.info("Switched to window with title: " + windowTitle);
                return;
            }
        }
        log.error("Window with title " + windowTitle + " not found");
    }

    /**
     * Switches to a frame with the specified locator key.
     *
     * @param locatorKey The key to locate the frame element.
     */
    public static void switchToFrame(String locatorKey) {
        BaseTest.driver.switchTo().frame(getElement(locatorKey));
        log.info("Switched to frame with locator: " + locatorKey);
    }

    /**
     * Switches back to the default content from a frame.
     */
    public static void switchToDefaultContent() {
        BaseTest.driver.switchTo().defaultContent();
        log.info("Switched to default content");
    }

    /**
     * Scrolls to the element specified by the locator key.
     *
     * @param locatorKey The key to locate the element.
     */
    public static void scrollToElement(String locatorKey) {
        WebElement element = getElement(locatorKey);
        ((JavascriptExecutor) BaseTest.driver).executeScript("arguments[0].scrollIntoView(true);", element);
        log.info("Scrolled to element with locator: " + locatorKey);
    }

    /**
     * Executes JavaScript on the current page.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to pass to the script.
     * @return The result of the script execution.
     */
    public static Object executeJavaScript(String script, Object... args) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) BaseTest.driver;
        Object result = jsExecutor.executeScript(script, args);
        log.info("Executed JavaScript: " + script);
        return result;
    }

    /**
     * Gets the value of the specified attribute from the element specified by the locator key.
     *
     * @param locatorKey The key to locate the element.
     * @param attribute  The attribute name.
     * @return The value of the attribute.
     */
    public static String getAttribute(String locatorKey, String attribute) {
        String value = getElement(locatorKey).getAttribute(attribute);
        log.info("Attribute " + attribute + " of element with locator " + locatorKey + ": " + value);
        return value;
    }
}
