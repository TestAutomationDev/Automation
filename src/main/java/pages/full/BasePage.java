/**
 * File: BasePage.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: Base page class for all pages
 */
package pages.full;


import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropertyReader;


import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    private final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void enterData(WebElement element, String text) {
        waitForVisibilityAndClickable(element);
        element.clear();
        element.sendKeys(text);
    }

    public void click(WebElement element) {

        try {
            waitForVisibilityAndClickable(element);
            element.click();
        } catch (ElementClickInterceptedException e) {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
        }

    }

    public String getText(WebElement element) {
        scrollIntoView(element);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public void selectDropdownByValue(WebElement dropdown, String value) {
        Select select = new Select(dropdown);
        select.selectByValue(value);
    }

    public void selectDropdownByIndex(WebElement dropdown, int index) {
        waitForVisibilityAndClickable(dropdown);
        Select select = new Select(dropdown);
        select.selectByIndex(index);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected void waitForVisibilityAndClickable(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }


}
