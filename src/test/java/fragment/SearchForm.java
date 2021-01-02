package test.java.fragment;

import static org.openqa.selenium.By.cssSelector;
import static test.java.ultil.WaitUtils.waitForElementPresent;
import static test.java.ultil.WaitUtils.waitForElementVisible;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import test.java.abstracts.AbstractFragment;
import test.java.ultil.Sleeper;

public class SearchForm extends AbstractFragment {

    @FindBy(css = "input[type='text'], #search_str")
    WebElement searchInput;

    @FindBy(css = "input[type='submit'], button[type='submit']")
    WebElement submit;


    public static SearchForm getInstance(SearchContext context) {
        return Graphene.createPageFragment(SearchForm.class,
                waitForElementVisible(cssSelector(".search,#nav-search-form,#searchform"), context));
    }

    public void search(String city) {
        String url = browser.getCurrentUrl();
        waitForElementVisible(searchInput).clear();
        searchInput.sendKeys(city);
        searchInput.submit();
        //Special case with empty search no change url and will search as London UK
        //Bad behaviour, bcz don't know requirement therefore can't detect is behaviour or bug
        if (city.isEmpty()) {
            Sleeper.sleepTightInSeconds(2);
            return;
        }
        Graphene.waitGui().until(e ->
            FindPage.getInstance(browser).getSearchForm().getValue().equals(city) || !browser.getCurrentUrl().equals(url));
    }

    public String getPlaceHolder() {
        return waitForElementVisible(searchInput).getAttribute("placeholder");
    }

    public String getValue() {
        return waitForElementVisible(searchInput).getAttribute("value");
    }

    public boolean isHiddenSubmit() {
        return !waitForElementPresent(submit).isDisplayed();
    }

    public boolean isSearchInputVisible() {
        return waitForElementPresent(searchInput).isDisplayed();
    }

    public void submit() {
        waitForElementVisible(submit).click();
    }
}
