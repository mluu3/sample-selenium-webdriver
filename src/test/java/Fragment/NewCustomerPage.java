package test.java.Fragment;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.name;
import static test.java.Ultil.ElementUtils.isElementVisible;
import static test.java.Ultil.WaitUtils.waitForElementVisible;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NewCustomerPage extends AbstractFragment {

    @FindBy(name = "name")
    WebElement nameInput;

    @FindBy(name = "rad1")
    List<WebElement> gender;

    @FindBy(id = "dob")
    WebElement dateOfBirth;

    @FindBy(name = "addr")
    WebElement addressInput;

    @FindBy(name = "city")
    WebElement city;

    @FindBy(name = "state")
    WebElement state;

    @FindBy(name = "pinno")
    WebElement pin;

    @FindBy(name = "telephoneno")
    WebElement telephoneNumber;

    @FindBy(name = "emailid")
    WebElement emailId;

    @FindBy(name = "password")
    WebElement passwordInput;

    @FindBy(name = "sub")
    WebElement submit;

    @FindBy(name = "res")
    WebElement reset;

    public static final NewCustomerPage getInstance(SearchContext context) {
        return Graphene.createPageFragment(NewCustomerPage.class,
                waitForElementVisible(className("layout"), context));
    }

    public NewCustomerPage setName(String name) {
        waitForElementVisible(nameInput).clear();
        nameInput.sendKeys(name);
        return this;
    }

    public NewCustomerPage setGender(Gender sex) {
        gender.stream()
                .filter(e -> e.getAttribute("value").equals(sex.getShortLetter()))
                .findFirst()
                .get()
                .click();
        return this;
    }

    public NewCustomerPage setDateOfBirth(String date) {
        dateOfBirth.sendKeys(date);
        return this;
    }

    public NewCustomerPage setAddress(String address) {
        waitForElementVisible(addressInput).clear();
        addressInput.sendKeys(address);
        return this;
    }

    public NewCustomerPage setCity(String cityName) {
        waitForElementVisible(city).clear();
        city.sendKeys(cityName);
        return this;
    }

    public NewCustomerPage setState(String stateName) {
        waitForElementVisible(state).clear();
        state.sendKeys(stateName);
        return this;
    }

    public NewCustomerPage setPin(int pinNubmer) {
        waitForElementVisible(pin).clear();
        pin.sendKeys(Integer.toString(pinNubmer));
        return this;
    }

    public NewCustomerPage setPhone(int phoneNumber) {
        waitForElementVisible(telephoneNumber).clear();
        telephoneNumber.sendKeys(Integer.toString(phoneNumber));
        return this;
    }

    public NewCustomerPage setEmail(String email) {
        waitForElementVisible(emailId).clear();
        emailId.sendKeys(email);
        return this;
    }

    public NewCustomerPage setPassword(String password) {
        waitForElementVisible(passwordInput).clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public void submit() {
        waitForElementVisible(submit).click();
        Graphene.waitGui().until(browser -> !isElementVisible(name("sub"), browser));
    }

    public NewCustomerPage reset() {
        waitForElementVisible(reset).click();
        return this;
    }

    public ConfirmationCustomerPage getComfirmationCustomerPage() {
        return ConfirmationCustomerPage.getInstance(browser);
    }

    public enum Gender {
        MALE("m"),
        FEMALE("f");

        private final String sex;

        private Gender(String sex) {
            this.sex = sex;
        }

        public String getShortLetter() {
            return sex;
        }
    }
}
