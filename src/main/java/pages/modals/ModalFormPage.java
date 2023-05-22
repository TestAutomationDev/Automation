/**
 * File: ModalFormPage.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: Base page class for all pages
 */
package pages.modals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.full.BasePage;

public class ModalFormPage extends BasePage {

    @FindBy(id = "modal-lead-form")
    private WebElement modalLeadForm;

    @FindBy(css = "#modal-lead-form div:nth-child(2) > a")
    private WebElement createAccountButton;

    public ModalFormPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnCreateAccountButton() {
        click(createAccountButton);
    }

    public void getTitle() {
        System.out.println("modalLeadForm = " + modalLeadForm.getText());
    }
}
