package tests;

import core.TestLifecycleManager;
import core.WebDriverFactory;
import dataproviders.MockUserDataProvider;
import dataproviders.SecurityQuestionProvider;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.full.ApplicationPage;
import pages.full.HomePage;
import pages.modals.ModalFormPage;
import utils.PropertyReader;

public class HomePageTest extends TestLifecycleManager {

    private HomePage homePage;
    private ModalFormPage modelFormPage;

    @DataProvider(name = "userDataProvider")
    public Object[][] userDataProvider() {
        String username = MockUserDataProvider.generateValidUsername();
        String password = MockUserDataProvider.generateValidPassword(username);
        String email = MockUserDataProvider.generateValidEmail();
        return new Object[][] { { username, password, email } };
    }

    //Test 1 - Complete a PortfolioAnalyst Standalone Application
    @Test(dataProvider = "userDataProvider", description = "User Complete a PortfolioAnalyst Standalone Application")
    public void testStandaloneApplicationFormCompletion(String username, String password, String email) {
        WebDriver driver = WebDriverFactory.getWebDriver();
        homePage = new HomePage(driver);

        homePage.clickOnGetStartedButton();

        homePage.getModelForm().clickOnCreateAccountButton();
        Assert.assertEquals(homePage.getCurrentUrl(), PropertyReader.getPropertyByKey("standaloneApplicationUrl"));

        ApplicationPage applicationPage = new ApplicationPage(driver);

        applicationPage.enterEmailAddress(email);
        applicationPage.enterUsername(username);

        applicationPage.enterPassword(PropertyReader.getPropertyByKey("assignmentUserPassword"));
        applicationPage.enterConfirmPassword(PropertyReader.getPropertyByKey("assignmentUserPassword"));

        applicationPage.enterFirstName("First Name");
        applicationPage.enterLastName("Last Name");
        applicationPage.enterDateOfBirth("01/01/1990");

        applicationPage.selectSecurityQuestionOne(SecurityQuestionProvider.getQuestion());
        applicationPage.enterSecurityQuestionOneAnswer("Security Question Answer 1");

        applicationPage.selectSecurityQuestionTwo(SecurityQuestionProvider.getQuestion());
        applicationPage.enterSecurityQuestionTwoAnswer("Security Question Answer 2");

        applicationPage.selectSecurityQuestionThree(SecurityQuestionProvider.getQuestion());
        applicationPage.enterSecurityQuestionThreeAnswer("Security Question Answer 3");

        applicationPage.subscriptionAgreementContentLink();
        applicationPage.clickOnAgreeButton();
        applicationPage.clickOnAccountCreationButton();

        Assert.assertEquals(applicationPage.getConfirmYourEmailMessage(), PropertyReader.getPropertyByKey("confirmYourEmailMessage"));
    }
}
