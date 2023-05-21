/**
 * File: BasePage.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: Base page class for all pages
 */
package pages.full;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        waitForVisibilityAndClickable(element);
        element.click();
    }

    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public boolean isElementDisplayed(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.isDisplayed();
    }

    public void selectDropdownByValue(WebElement dropdown, String value) {
        waitForVisibilityAndClickable(dropdown);
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

}
