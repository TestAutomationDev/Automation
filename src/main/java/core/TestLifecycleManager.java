/**
 * File: TestLifecycleManager.java
 * Author: Waruna
 * Created: 5/19/2023
 * Description: Manages tests by setting up and tearing down WebDriver instances
 */
package core;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;


public class TestLifecycleManager {

    /**
     * Sets up the WebDriver with specified configuration before each test.
     *
     * @param browser         The browser to use.
     * @param url             The URL to navigate to.
     * @param implicitWait    The implicit wait time in seconds.
     * @param maxPageLoadTime The maximum page load time in seconds.
     */
    @Parameters({"browserName", "url", "implicitWait", "maxPageLoadTime"})
    @BeforeTest
    public void setup(String browser, String url, long implicitWait, long maxPageLoadTime) {
        WebDriverFactory.setConfiguration(browser, url, implicitWait, maxPageLoadTime);
    }

    /**
     * Tears down the WebDriver after each test.
     */
    @AfterTest
    public void tearDown() {
        WebDriver driver = WebDriverFactory.getWebDriver();
        if (driver != null) {
            driver.quit();
            WebDriverFactory.removeWebDriver();
        }
    }
}
