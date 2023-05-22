package tests;

import core.TestLifecycleManager;
import core.WebDriverFactory;
import dataproviders.MockUserDataProvider;
import dataproviders.SecurityQuestionProvider;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pages.full.ApplicationPage;
import org.testng.annotations.Test;
import utils.PropertyReader;

public class ApplicationPageTest extends TestLifecycleManager {
    private ApplicationPage applicationPage;
    String username;
    String password;
    String email;

    @BeforeMethod(description = "Fill out the application form with valid data before each test.")
    public void setup() {

        WebDriver driver = WebDriverFactory.getWebDriver();
        applicationPage = new ApplicationPage(driver);
        username = MockUserDataProvider.generateValidUsername();
        password = MockUserDataProvider.generateValidPassword(username);
        email = MockUserDataProvider.generateValidEmail();

        applicationPage.enterEmailAddress(email);
        applicationPage.enterUsername(username);
        applicationPage.enterPassword(password);
        applicationPage.enterConfirmPassword(password);

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

    }

    @Test(priority = 1, description = "Validate that an error is shown when an invalid email address is entered.")
    public void testInvalidEmailError() {
        applicationPage.enterEmailAddress(MockUserDataProvider.generateInvalidEmail());
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getEmailAddressError(), PropertyReader.getPropertyByKey("invalidEmailError"));
    }

    @Test(priority = 2, description = "Validate that an error is shown when an invalid username is entered.")
    public void testInvalidUsernameError() {
        applicationPage.enterUsername(MockUserDataProvider.generateShortUsername());
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getUserNameError1(), PropertyReader.getPropertyByKey("userNameError1"));
    }

    @Test(priority = 3, description = "Validate that an error is shown when an invalid password is entered.")
    public void testInvalidPasswordError() {
        applicationPage.enterPassword(MockUserDataProvider.generateShortPassword());
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getPasswordError1(), PropertyReader.getPropertyByKey("passwordError1"));
    }

    @Test(priority = 4, description = "Validate that an error is shown when non-matching password confirmation is entered.")
    public void testNonMatchingPasswordConfirmationError() {
        applicationPage.enterPassword(MockUserDataProvider.generateValidPassword(username));
        applicationPage.enterConfirmPassword(MockUserDataProvider.generateMismatchingPassword(username));
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getConfirmPasswordError(), PropertyReader.getPropertyByKey("confirmPasswordError1"));
    }

    @Test(priority = 5, description = "Validate that the default country is United States.")
    public void testDefaultCountrySelection() {
        Assert.assertEquals(applicationPage.getInitialCountryResidential(), "United States");
    }

    @Test(priority = 6, description = "Validate that an error is shown when First Name is not entered.")
    public void testFirstNameRequired() {
        applicationPage.enterFirstName("");
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getFirstNameError(), PropertyReader.getPropertyByKey("Required"));
    }

    @Test(priority = 7, description = "Validate that an error is shown when Last Name is not entered.")
    public void testLastNameRequired() {
        applicationPage.enterLastName("");
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getLastNameError(), PropertyReader.getPropertyByKey("Required"));
    }

    @Test(priority = 8, description = "Validate that an error is shown when Date of Birth is not entered.")
    public void testDateOfBirthRequired() {
        applicationPage.enterDateOfBirth("");
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getDateOfBirthError(), PropertyReader.getPropertyByKey("Required"));
    }

    @Test(priority = 9, description = "Validate that an error is shown when Security Question 1 is not entered.")
    public void testSecurityQuestionOneRequired() {
        applicationPage.clearSecurityQuestionOne();
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getSecurityQuestionOneError(), PropertyReader.getPropertyByKey("Required"));
    }

    @Test(priority = 10, description = "Validate that an error is shown when Security Question 1 answer is not entered.")
    public void testSecurityQuestionOneAnswerRequired() {
        applicationPage.enterSecurityQuestionOneAnswer("");
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getSecurityQuestionOneAnswerError(), PropertyReader.getPropertyByKey("Required"));
    }

    @Test(priority = 11, description = "Validate that an error is shown when Security Question 2 is not entered.")
    public void testSecurityQuestionTwoRequired() {
        applicationPage.clearSecurityQuestionTwo();
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getSecurityQuestionTwoError(), PropertyReader.getPropertyByKey("Required"));
    }

    @Test(priority = 12, description = "Validate that an error is shown when Security Question 2 answer is not entered.")
    public void testSecurityQuestionTwoAnswerRequired() {
        applicationPage.enterSecurityQuestionTwoAnswer("");
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getSecurityQuestionTwoAnswerError(), PropertyReader.getPropertyByKey("Required"));
    }

    @Test(priority = 13, description = "Validate that an error is shown when Security Question 3 is not entered.")
    public void testSecurityQuestionThreeRequired() {
        applicationPage.clearSecurityQuestionThree();
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getSecurityQuestionThreeError(), PropertyReader.getPropertyByKey("Required"));
    }

    @Test(priority = 14, description = "Validate that an error is shown when Security Question 3 answer is not entered.")
    public void testSecurityQuestionThreeAnswerRequired() {
        applicationPage.enterSecurityQuestionThreeAnswer("");
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getSecurityQuestionThreeAnswerError(), PropertyReader.getPropertyByKey("Required"));
    }

    @Test(priority = 15, description = "Validate that an error is shown when terms and conditions are not accepted.")
    public void testAgreementRequiredToContinue() {
        applicationPage.clickOnDisAgreeButton();
        applicationPage.clickOnAccountCreationButton();
        Assert.assertEquals(applicationPage.getTermsAndConditionsError(), PropertyReader.getPropertyByKey("acceptAgreement"));
    }


}

