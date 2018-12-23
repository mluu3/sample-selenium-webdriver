package test.java.Fragment;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.name;
import static test.java.Ultil.ElementUtils.isElementVisible;
import static test.java.Ultil.WaitUtils.waitForElementVisible;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class NewAccountPage extends AbstractFragment {

    @FindBy(name = "cusid")
    WebElement customerID;

    @FindBy(name = "selaccount")
    WebElement accountType;

    @FindBy(name = "inideposit")
    WebElement initialDeposit;

    @FindBy(name = "button2")
    WebElement submit;

    @FindBy(name = "reset")
    WebElement reset;

    public static final NewAccountPage getInstance(SearchContext context) {
        return Graphene.createPageFragment(NewAccountPage.class,
                waitForElementVisible(className("layout"), context));
    }

    public NewAccountPage setCustomerID(int id) {
        waitForElementVisible(customerID).clear();
        customerID.sendKeys(Integer.toString(id));
        return this;
    }

    public NewAccountPage setAccountType(AccountType type) {
        Select dropdown = new Select(waitForElementVisible(accountType));
        dropdown.selectByValue(type.getType());
        return this;
    }

    public NewAccountPage setDeposit(int deposit) {
        waitForElementVisible(initialDeposit).clear();
        initialDeposit.sendKeys(Integer.toString(deposit));
        return this;
    }

    public void submit() {
        waitForElementVisible(submit).click();
        Graphene.waitGui().until(browser -> !isElementVisible(name("button2"), browser));
    }

    public NewAccountPage reset() {
        waitForElementVisible(reset).click();
        return this;
    }

    public AccountCreateMessagePage getAccountCreateMessagePage() {
        return AccountCreateMessagePage.getInstance(browser);
    }

    public enum AccountType {
        SAVINGS("Savings"),
        CURRENT("Current");

        private final String type;

        private AccountType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
