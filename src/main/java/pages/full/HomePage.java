package pages.full;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.modals.ModalFormPage;

public class HomePage extends BasePage {

    @FindBy(css = ".cta-list > a:nth-of-type(2)")
    private WebElement getStartedButton;


    // Instance of ModalFormPage as it is a component used in this page.
    private final ModalFormPage modalFormPage;

    public HomePage(WebDriver driver) {
        super(driver);
        this.modalFormPage = new ModalFormPage(driver);
    }

    public void clickOnGetStartedButton(){
        click(getStartedButton);
    }

    public void getTextOnModalForm(){
        modalFormPage.getTitle();
    }

    public void clickOnCreateAccountButton(){
        modalFormPage.openCreateAccountForm();
    }

}
