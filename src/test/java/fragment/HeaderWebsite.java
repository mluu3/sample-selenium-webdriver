package test.java.fragment;

import static org.openqa.selenium.By.id;
import static test.java.ultil.WaitUtils.waitForElementVisible;
import static test.java.ultil.WaitUtils.waitForFragmentVisible;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import test.java.abstracts.AbstractFragment;

public class HeaderWebsite extends AbstractFragment {

    @FindBy(css = "logo")
    WebElement logo;

    @FindBy(id = "nav-search-form")
    SearchForm searchForm;

    public static final HeaderWebsite getInstance(SearchContext context) {
        return Graphene.createPageFragment(HeaderWebsite.class,
                waitForElementVisible(id("header-website"), context));
    }

    public SearchForm getSearchForm() {
        return waitForFragmentVisible(searchForm);
    }
}
