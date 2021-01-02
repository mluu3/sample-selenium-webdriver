package test.java.fragment;

import static org.openqa.selenium.By.xpath;
import static test.java.ultil.WaitUtils.waitForElementVisible;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import test.java.abstracts.AbstractFragment;

public class OpenWeatherMapPage extends AbstractFragment {

    @FindBy(name = "accountno")
    WebElement accountNo;

    public static OpenWeatherMapPage getInstance(SearchContext context) {
        return Graphene.createPageFragment(OpenWeatherMapPage.class,
                waitForElementVisible(xpath("//main[.//div[contains(@class, 'main-section')]]"), context));
    }

    public HeaderWebsite getHeaderWebsite() {
        return HeaderWebsite.getInstance(browser);
    }
}
