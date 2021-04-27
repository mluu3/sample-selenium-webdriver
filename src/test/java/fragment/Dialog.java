package test.java.fragment;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import test.java.abstracts.AbstractFragment;

import static org.openqa.selenium.By.className;
import static test.java.ultil.WaitUtils.*;

public class Dialog extends AbstractFragment {

    @FindBy(className = "swal-title")
    WebElement title;

    @FindBy(className = "swal-button--confirm")
    WebElement confirmButton;

    public static Dialog getInstance(SearchContext context) {
        return Graphene.createPageFragment(Dialog.class,
                waitForElementVisible(className("swal-modal"), context));
    }

    public String getTitle() {
        return waitForElementVisible(title).getText();
    }

    public void confirm() {
        waitForElementEnabled(confirmButton).click();
        waitForFragmentNotVisible(this);
    }
}
