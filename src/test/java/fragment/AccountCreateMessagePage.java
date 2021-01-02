package test.java.fragment;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.name;
import static test.java.ultil.WaitUtils.waitForElementVisible;
import static test.java.fragment.NewAccountPage.AccountType;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import test.java.abstracts.AbstractFragment;


public class AccountCreateMessagePage extends AbstractFragment {

    public static AccountCreateMessagePage getInstance(SearchContext context) {
        return Graphene.createPageFragment(AccountCreateMessagePage.class,
                waitForElementVisible(name("account"), context));
    }

    public String getStatus() {
        return getText(0);
    }

    public int getAccountID() {
        return Integer.valueOf(getText(3).substring(11));
    }

    public int getCustomerID() {
        return Integer.valueOf(getText(4).substring(12));
    }

    public String getCustomerName() {
        return getText(5);
    }

    public String getEmail() {
        return getText(6);
    }

    public AccountType getAccountType() {
        return AccountType.valueOf(getText(7).substring(13).toUpperCase());
    }

    public String getDateOfOpening() {
        return getText(8);
    }

    public String getCurrentAmount() {
        return getText(9);
    }

    private String getText(int index) {
        return getRoot().findElements(cssSelector("tbody tr")).get(index).getText();
    }
}
