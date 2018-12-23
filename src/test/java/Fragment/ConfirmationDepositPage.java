package test.java.Fragment;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.name;
import static test.java.Ultil.WaitUtils.waitForElementVisible;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;

public class ConfirmationDepositPage extends AbstractFragment {

    public static ConfirmationDepositPage getInstance(SearchContext context) {
        return Graphene.createPageFragment(ConfirmationDepositPage.class,
                waitForElementVisible(name("deposit"), context));
    }

    public String getStatus() {
        return getText(0);
    }

    public int getTransactionID() {
        return Integer.valueOf(getText(5).substring(15));
    }

    public int getAccountID() {
        return Integer.valueOf(getText(6).substring(11));
    }

    public int getAmountCredited() {
        return Integer.valueOf(getText(11).substring(16));
    }

    public String getTypeOfTransaction() {
        return getText(15);
    }

    public String getDescription() {
        return getText(19);
    }

    public String getCurrentBalance() {
        return getText(20);
    }

    private String getText(int index) {
        return getRoot().findElements(cssSelector("tbody tr")).get(index).getText();
    }
}
