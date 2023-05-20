/**
 * File: WebDriverFactory.java
 * Author: Waruna
 * Created: 5/19/2023
 * Description: A factory class for creating, managing and cleaning up WebDriver instances for various browsers.
 */

package core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.logging.Logger;

public class WebDriverFactory {

    // ThreadLocal instance to support multithreading
    private static final ThreadLocal<WebDriver> threadLocalWebDriver = new ThreadLocal<>();

    // Logger instance for logging
    private static final Logger logger = Logger.getLogger(WebDriverFactory.class.getName());

    // Enum for browser types
    public enum Browser {
        CHROME, FIREFOX, EDGE, SAFARI, CHROME_HEADLESS, FIREFOX_HEADLESS, EDGE_HEADLESS
    }

    // Private constructor to prevent instantiation of this class
    private WebDriverFactory() {
    }

    /**
     * Method to set up and configure the WebDriver instance
     *
     * @param browser         Browser type (Enum)
     * @param url             URL to navigate to
     * @param implicitWait    Implicit wait time
     * @param maxPageLoadTime Max page load time
     */
    public static void setConfiguration(String browser, String url, long implicitWait, long maxPageLoadTime) {
        WebDriver driver = createWebDriver(browser);
        implicitlyWait(driver, implicitWait);
        setMaxPageLoadTime(driver, maxPageLoadTime);
        threadLocalWebDriver.set(driver);
        driver.get(url);
    }

    /**
     * Method to create and return a WebDriver instance based on the specified browser
     *
     * @param browser Browser type (Enum)
     * @return WebDriver instance
     */
    private static WebDriver createWebDriver(String browser) {
        WebDriver driver;
        Browser currentBrowser = Browser.valueOf(browser.toUpperCase());

        // Switch case to handle different browser types
        switch (currentBrowser) {
            case CHROME:
                driver = new ChromeDriver();
                break;
            case CHROME_HEADLESS:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--disable-gpu");
                driver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case FIREFOX_HEADLESS:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            case EDGE_HEADLESS:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless");
                driver = new EdgeDriver(edgeOptions);
                break;
            case SAFARI:
                driver = new SafariDriver();
                break;
            default:
                logger.severe("Invalid browser specified: " + browser);
                throw new IllegalArgumentException("Browser not found. Please provide a valid browser.");
        }
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Method to set implicit wait time for the WebDriver instance
     *
     * @param driver        WebDriver instance
     * @param timeInSeconds Implicit wait time in seconds
     */
    private static void implicitlyWait(WebDriver driver, long timeInSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
    }

    /**
     * Method to set maximum page load time for the WebDriver instance
     *
     * @param driver        WebDriver instance
     * @param timeInSeconds Maximum page load time in seconds
     */
    private static void setMaxPageLoadTime(WebDriver driver, long timeInSeconds) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeInSeconds));
    }

    /**
     * Method to get the WebDriver instance for current thread
     *
     * @return WebDriver instance
     */
    public static WebDriver getWebDriver() {
        return threadLocalWebDriver.get();
    }

    /**
     * Method to remove and quit the WebDriver instance
     */
    public static void removeWebDriver() {
        WebDriver driver = threadLocalWebDriver.get();
        if (driver != null) {
            driver.quit();
        }
        threadLocalWebDriver.remove();
    }

    // Add shutdown hook to ensure all WebDriver instances are closed when JVM shuts down
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (threadLocalWebDriver.get() != null) {
                removeWebDriver();
            }
        }));
    }
}
