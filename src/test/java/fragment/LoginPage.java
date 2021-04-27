package test.java.fragment;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import test.java.abstracts.AbstractFragment;

import static java.lang.String.format;
import static org.openqa.selenium.By.*;
import static test.java.ultil.WaitUtils.*;

public class LoginPage extends AbstractFragment {

    @FindBy(id = "tbx-username")
    WebElement userInput;

    @FindBy(id = "tbx-password")
    WebElement passwordInput;

    @FindBy(id = "btn-login")
    WebElement btnLogin;

    public static LoginPage getInstance(SearchContext context) {
        return Graphene.createPageFragment(LoginPage.class,
                waitForElementVisible(className("login-box"), context));
    }

    public LoginPage login(String user, String password) {
        waitForElementVisible(userInput).clear();
        userInput.sendKeys(user);
        waitForElementVisible(passwordInput).clear();
        passwordInput.sendKeys(password);
        waitForElementVisible(btnLogin).click();
        waitForElementNotVisible(passwordInput);
        return this;
    }

    public void selectCenter(String center) {
        WebElement dropdown =  waitForElementVisible(className("form-control"), root);
        dropdown.click();
        waitForElementVisible(xpath(format("//*[contains(text(),'%s')]", center)), dropdown).click();
        waitForElementVisible(btnLogin).click();
        waitForFragmentNotVisible(this);
    }
}
