package test.java.Fragment;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.name;
import static test.java.Ultil.WaitUtils.waitForElementVisible;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;

public class ConfirmationCustomerPage extends AbstractFragment {

    public static ConfirmationCustomerPage getInstance(SearchContext context) {
        return Graphene.createPageFragment(ConfirmationCustomerPage.class,
                waitForElementVisible(name("customer"), context));
    }

    public String getStatus() {
        return getText(0);
    }

    public int getCustomerID() {
        return Integer.valueOf(getText(3).substring(12));
    }

    public String getCustomerName() {
        return getText(4);
    }

    public String getGender() {
        return getText(5);
    }

    public String getBirthday() {
        return getText(6);
    }

    public String getAddress() {
        return getText(7);
    }

    public String getCity() {
        return getText(8);
    }

    public String getState() {
        return getText(9);
    }

    public String getPin() {
        return getText(10);
    }

    public String getPhoneNumber() {
        return getText(11);
    }

    public String getEmail() {
        return getText(12);
    }

    private String getText(int index) {
        return getRoot().findElements(cssSelector("tbody tr")).get(index).getText();
    }
}
