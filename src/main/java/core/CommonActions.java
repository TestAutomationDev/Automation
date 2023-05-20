/**
 * File: CommonActions.java
 * Author: Waruna
 * Created: 5/19/2023
 * Description: Executes common web actions.
 */
package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CommonActions {

    private final WebDriver driver;

    public CommonActions(WebDriver driver) {
        this.driver = driver;
    }

    // Clicks on a web element identified by 'by'
    public void click(By by) {
        driver.findElement(by).click();
    }

    // Types the text into a web element identified by 'by'
    public void type(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    // Returns the text of a web element identified by 'by'
    public String getText(By by) {
        return driver.findElement(by).getText();
    }

    // Selects an option by index from a dropdown identified by 'by'
    public void selectByIndex(By by, int index) {
        new Select(driver.findElement(by)).selectByIndex(index);
    }

    // Selects an option by value from a dropdown identified by 'by'
    public void selectByValue(By by, String value) {
        new Select(driver.findElement(by)).selectByValue(value);
    }

    // Selects an option by visible text from a dropdown identified by 'by'
    public void selectByText(By by, String text) {
        new Select(driver.findElement(by)).selectByVisibleText(text);
    }
}