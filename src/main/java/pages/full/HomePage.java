/**
 * File: HomePage.java
 * Author: Waruna
 * Created: 5/22/2023
 * Description: Home page object.
 */
package pages.full;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.modals.ModalFormPage;

public class HomePage extends BasePage {

    @FindBy(css = ".cta-list > a:nth-of-type(2)")
    private WebElement getStartedButton;

    private final ModalFormPage modalFormPage;

    public HomePage(WebDriver driver) {
        super(driver);
        this.modalFormPage = new ModalFormPage(driver);
    }

    public void clickOnGetStartedButton(){
        click(getStartedButton);
    }

    public ModalFormPage getModelForm(){
        return modalFormPage;
    }

    public void clickOnCreateAccountButton(){
        modalFormPage.clickOnCreateAccountButton();
    }

}
