package test.java.Fragment;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.name;
import static test.java.Ultil.ElementUtils.isElementVisible;
import static test.java.Ultil.WaitUtils.waitForElementVisible;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DepositPage extends AbstractFragment {

    @FindBy(name = "accountno")
    WebElement accountNo;

    @FindBy(name = "ammount")
    WebElement ammountInput;

    @FindBy(name = "desc")
    WebElement descriptionInput;

    @FindBy(name = "AccSubmit")
    WebElement submit;

    @FindBy(name = "res")
    WebElement reset;

    public static final DepositPage getInstance(SearchContext context) {
        return Graphene.createPageFragment(DepositPage.class,
                waitForElementVisible(className("layout"), context));
    }

    public DepositPage setAccountID(int id) {
        waitForElementVisible(accountNo).clear();
        accountNo.sendKeys(Integer.toString(id));
        return this;
    }

    public DepositPage setAmount(int amount) {
        waitForElementVisible(ammountInput).clear();
        ammountInput.sendKeys(Integer.toString(amount));
        return this;
    }

    public DepositPage setDescription(String description) {
        waitForElementVisible(descriptionInput).clear();
        descriptionInput.sendKeys(description);
        return this;
    }

    public void submit() {
        waitForElementVisible(submit).click();
        Graphene.waitGui().until(browser -> !isElementVisible(name("AccSubmit"), browser));
    }

    public DepositPage reset() {
        waitForElementVisible(reset).click();
        return this;
    }

    public ConfirmationDepositPage getConfirmationDepositPage() {
        return ConfirmationDepositPage.getInstance(browser);
    }
}
