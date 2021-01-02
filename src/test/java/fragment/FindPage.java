package test.java.fragment;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;
import static test.java.ultil.ElementUtils.isElementVisible;
import static test.java.ultil.WaitUtils.waitForElementVisible;
import static test.java.ultil.WaitUtils.waitForFragmentVisible;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import test.java.abstracts.AbstractFragment;

import java.util.List;

public class FindPage extends AbstractFragment {

    @FindBy(id = "searchform")
    SearchForm searchForm;

    @FindBy(id = "forecast-list")
    WebElement forecastList;

    public static FindPage getInstance(SearchContext context) {
        return Graphene.createPageFragment(FindPage.class,
                    waitForElementVisible(xpath("//main[.//div[contains(@class, 'jumbotron-green')]]"), context));
    }

    public SearchForm getSearchForm() {
        return waitForFragmentVisible(searchForm);
    }

    public String getAlertWarning() {
        return waitForElementVisible(className("alert-warning"), getRoot()).getText().replace("×\n", "");
    }

    public boolean isAlertWarningVisible() {
        return isElementVisible(className("alert-warning"), getRoot());
    }

    public int getRecordSize() {
        return getRecordTable().size();
    }

    public boolean isRecordTableVisible() {
        return isElementVisible(tagName("table"), root);
    }

    public Record getRecordByTitle(String title) {
        return Graphene.createPageFragment(Record.class, getRecordTable().stream()
                .filter(e -> e.findElement(cssSelector("b a")).getText().equals(title))
                .findAny()
                .get());
    }

    private List<WebElement> getRecordTable() {
        return waitForElementVisible(tagName("table"), root).findElements(tagName("tr"));
    }

    public class Record extends AbstractFragment {

        @FindBy(css = "td:first-child img")
        WebElement weatherSymbol;

        @FindBy(css = "td:last-child")
        WebElement detailInformation;

        public String getImageUrl() {
            return waitForElementVisible(weatherSymbol).getAttribute("src");
        }

        public String getLocation() {
            return waitForElementVisible(detailInformation.findElement(cssSelector("b a"))).getText();
        }

        public boolean hasWeather() {
            return waitForElementVisible(detailInformation.findElement(cssSelector("b i"))).isDisplayed();
        }

        public String getPosition() {
            return waitForElementVisible(detailInformation.findElement(cssSelector("p a"))).getText();
        }

        public String getBadge() {
            return waitForElementVisible(detailInformation.findElement(className("badge-info"))).getText();
        }
    }
}
