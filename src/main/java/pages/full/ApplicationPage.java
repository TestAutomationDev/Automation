/**
 * File: ApplicationPage.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: Application page object
 */
package pages.full;

import dataproviders.CountryDataProvider;
import objects.Country;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


public class ApplicationPage extends BasePage {

    private final CountryDataProvider countryDataProvider = new CountryDataProvider();
    private Country currentCountry;


    public ApplicationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#applicationForm .start-page .start-left h1")
    private WebElement pageHeaderText;

    @FindBy(css = "#applicationForm .start-page .start-right h3")
    private WebElement formHeaderText;

    @FindBy(name = "emailAddress")
    private WebElement emailAddressField;
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "password2")
    private WebElement confirmPasswordField;

    @FindBy(id = "passwordViewShow")
    private WebElement passwordShowButton;

    @FindBy(id = "countryResidentialResidence")
    private WebElement countryResidentialResidence;


    @FindBy(css = "input[name='gdprConfigProductUpdates'][value='T']")
    private WebElement gdprConfigProductUpdatesTrue;

    @FindBy(css = "input[name='gdprConfigProductUpdates'][value='F']")
    private WebElement gdprConfigProductUpdatesFalse;

    @FindBy(id = "legalCountryWarningLocation")
    private WebElement legalCountryWarningLocation;


    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "date")
    private WebElement dateOfBirthField;

    @FindBy(id = "question0")
    private WebElement securityQuestionOneField;
    @FindBy(id = "answer0")
    private WebElement securityQuestionOneAnswerField;

    @FindBy(id = "question1")
    private WebElement securityQuestionTwoField;

    @FindBy(id = "answer1")
    private WebElement securityQuestionTwoAnswerField;


    @FindBy(id = "question2")
    private WebElement securityQuestionThreeField;


    @FindBy(id = "answer2")
    private WebElement securityQuestionThreeAnswerField;

    @FindBy(css = "a[href='#paAgreementContent']")
    private WebElement agreementContentLink;

    @FindBy(id = "paAgreement_positive")
    private WebElement agreementAgreeButton;

    @FindBy(id = "paAgreement_negative")
    private WebElement agreementDisagreeButton;

    @FindBy(id = "accountCreationButton")
    private WebElement accountCreationButton;

    @FindBy(css = "#legalCountryWarningLocation .btn-icon")
    private WebElement gdprFaqIcon;

    @FindBy(id = "countryResidentialResidence_chosen")
    private WebElement countryResidentialResidence_chosen;

    @FindBy(css = "#countryResidentialResidence_chosen > div > div > input[type=text]")
    private WebElement countryResidentialResidenceAutoComplete;

    @FindBy(css = "#countryResidentialResidence_chosen .active-result:nth-child(1)")
    private WebElement countryResidentialResidenceValue;

    @FindBy(css = "#countryResidentialResidence_chosen  .chosen-search>input")
    private WebElement countryResidentialResidenceChosenSearchInput;

    @FindBy(css = "div.alert.alert-danger")
    private WebElement invalidEmailAddressError;


    @FindBy(id = "unCheck4")
    private WebElement userNameError1;

    @FindBy(id = "unCheck6")
    private WebElement userNameError2;

    @FindBy(id = "unCheck7")
    private WebElement userNameError3;

    //Password error messages
    @FindBy(id = "pwCheck0")
    private WebElement passwordCheck1;

    @FindBy(id = "pwCheck1")
    private WebElement passwordCheck2;

    @FindBy(id = "pwCheck2")
    private WebElement passwordCheck3;

    @FindBy(id = "pwCheck3")
    private WebElement passwordCheck4;

    @FindBy(id = "pwCheck5")
    private WebElement confirmPasswordError;

    @FindBy(css = "#firstName + .alert.alert-danger")
    private WebElement firstNameError;

    @FindBy(css = "#lastName + .alert.alert-danger")
    private WebElement lastNameError;

    @FindBy(css = "#date + .alert.alert-danger")
    private WebElement dateOfBirth;

    @FindBy(css = "#question0+ .alert.alert-danger")
    private WebElement securityQuestionOneError;

    @FindBy(css = "#question1+ .alert.alert-danger")
    private WebElement securityQuestionTwoError;

    @FindBy(css = "#question2+ .alert.alert-danger")
    private WebElement securityQuestionThreeError;

    @FindBy(css = "#answer0 + .alert.alert-danger")
    private WebElement securityQuestionAnswerOneError;

    @FindBy(css = "#answer1 + .alert.alert-danger")
    private WebElement securityQuestionAnswerTwoError;

    @FindBy(css = "#answer2 + .alert.alert-danger")
    private WebElement securityQuestionAnswerThreeError;

    @FindBy(css = "#applicationForm > section > div > div.row > div.col-12.col-lg-6.start-right > div.alert.alert-danger")
    private WebElement termsAndConditionsError;

    @FindBy(css = " #applicationForm > section > div > div:nth-child(1) > div > h2 > strong")
    private WebElement confirmYourEmailMessage;


    public void enterEmailAddress(String emailAddress) {
        enterData(emailAddressField, emailAddress);
    }

    public void enterUsername(String username) {
        enterData(usernameField, username);
    }

    public void enterPassword(String password) {
        enterData(passwordField, password);
    }

    public void enterConfirmPassword(String password) {
        enterData(confirmPasswordField, password);
    }

    public void selectCountry(String countryName) {

        Actions actions = new Actions(driver);
        actions.click(countryResidentialResidence_chosen).perform();
        waitForVisibilityAndClickable(countryResidentialResidenceChosenSearchInput);
        actions.click(countryResidentialResidenceChosenSearchInput).perform();
        this.currentCountry = countryDataProvider.getCountryDataByName(countryName);
        actions.pause(5000);
        actions.sendKeys(countryResidentialResidenceChosenSearchInput, currentCountry.getName()).perform();
        actions.sendKeys(Keys.RETURN).perform();

    }

    public void selectRandomCountry() {
        Actions actions = new Actions(driver);
        actions.click(countryResidentialResidence_chosen).perform();
        actions.click(countryResidentialResidenceChosenSearchInput).perform();
        this.currentCountry = countryDataProvider.getRandomCountryData();
        actions.sendKeys(countryResidentialResidenceChosenSearchInput, currentCountry.getName()).perform();
    }

    public void handleMarketingPreferences(Boolean preferToContactByOffers) {
        if (legalCountryWarningLocation.isDisplayed()) {
            Actions actions = new Actions(driver);
            WebElement elementToClick;

            if (preferToContactByOffers) {
                elementToClick = gdprConfigProductUpdatesTrue;
            } else {
                elementToClick = gdprConfigProductUpdatesFalse;
            }

            actions.moveToElement(elementToClick).click().perform();
        }
    }

    public void enterFirstName(String firstName) {
        enterData(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        enterData(lastNameField, lastName);
    }

    public void enterDateOfBirth(String dateOfBirth) {
        enterData(dateOfBirthField, dateOfBirth);
    }


    public void clearSecurityQuestionOne() {
        click(securityQuestionOneField);
        pressUpKeyUntilVisible(securityQuestionOneField);
        selectDropdownByIndex(securityQuestionOneField, 0);
    }

    public void clearSecurityQuestionTwo() {
        click(securityQuestionTwoField);
        pressUpKeyUntilVisible(securityQuestionOneField);
        selectDropdownByIndex(securityQuestionTwoField, 0);
    }

    public void clearSecurityQuestionThree() {
        click(securityQuestionThreeField);
        pressUpKeyUntilVisible(securityQuestionOneField);
        selectDropdownByIndex(securityQuestionThreeField, 0);
    }

    public void selectSecurityQuestionOne(String question) {
        click(securityQuestionOneField);
        selectDropdownByValue(securityQuestionOneField, question);
    }

    public void selectSecurityQuestionTwo(String question) {
        click(securityQuestionTwoField);
        selectDropdownByValue(securityQuestionTwoField, question);
    }

    public void selectSecurityQuestionThree(String question) {
        click(securityQuestionThreeField);
        selectDropdownByValue(securityQuestionThreeField, question);
    }

    public void enterSecurityQuestionOneAnswer(String answer) {
        enterData(securityQuestionOneAnswerField, answer);
    }

    public void enterSecurityQuestionTwoAnswer(String answer) {
        enterData(securityQuestionTwoAnswerField, answer);
    }

    public void enterSecurityQuestionThreeAnswer(String answer) {
        enterData(securityQuestionThreeAnswerField, answer);
    }

    public void subscriptionAgreementContentLink() {
        click(agreementContentLink);
    }

    public void clickOnAgreeButton() {
        click(agreementAgreeButton);
    }

    public void clickOnDisAgreeButton() {

        click(agreementDisagreeButton);
    }


    public void clickOnAccountCreationButton() {
        waitForVisibilityAndClickable(accountCreationButton);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", accountCreationButton);
    }

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
    }

    public String getEmailAddressError() {
        return getText(invalidEmailAddressError);
    }

    public String getUserNameError1() {
        return getText(userNameError1);
    }

    public String getUserNameError2() {
        return getText(userNameError2);
    }

    public String getUserNameError3() {
        return getText(userNameError3);
    }

    public String getPasswordError1() {
        return getText(passwordCheck1);
    }

    public String getPasswordError2() {
        return getText(passwordCheck2);
    }

    public String getPasswordError3() {
        return getText(passwordCheck3);
    }

    public String getPasswordError4() {
        return getText(passwordCheck4);
    }

    public String getConfirmPasswordError() {
        return getText(confirmPasswordError);
    }

    public String getInitialCountryResidential() {
        return getText(countryResidentialResidence_chosen);
    }

    public String getFirstNameError() {
        return getText(firstNameError);
    }

    public String getLastNameError() {
        return getText(lastNameError);
    }

    public String getDateOfBirthError() {
        return getText(dateOfBirth);
    }

    public String getSecurityQuestionOneError() {
        return getText(securityQuestionOneError);
    }

    public String getSecurityQuestionOneAnswerError() {
        return getText(securityQuestionAnswerOneError);
    }

    public String getSecurityQuestionTwoError() {
        return getText(securityQuestionTwoError);
    }

    public String getSecurityQuestionTwoAnswerError() {
        return getText(securityQuestionAnswerTwoError);
    }

    public String getSecurityQuestionThreeError() {
        return getText(securityQuestionThreeError);
    }

    public String getSecurityQuestionThreeAnswerError() {
        return getText(securityQuestionAnswerThreeError);
    }

    public String getTermsAndConditionsError() {
        return getText(termsAndConditionsError);
    }

    public String getConfirmYourEmailMessage() {
        return getText(confirmYourEmailMessage);
    }

    public void pressUpKeyUntilVisible(WebElement dropdown) {
        Actions actions = new Actions(driver);

        if (!dropdown.isDisplayed()) {
            while (!dropdown.isDisplayed()) {
                actions.sendKeys(Keys.ARROW_UP).perform();
            }
        }
    }
}
