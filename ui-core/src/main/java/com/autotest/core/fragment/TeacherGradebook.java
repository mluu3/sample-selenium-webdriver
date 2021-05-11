package com.autotest.core.fragment;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.autotest.core.abstracts.AbstractFragment;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.openqa.selenium.By.*;
import static com.autotest.core.fragment.TeacherGradebook.Tab.SEARCH_ALL;
import static com.autotest.core.fragment.TeacherGradebook.Tab.TO_SUBMIT;
import static com.autotest.core.ultil.WaitUtils.waitForElementVisible;

public class TeacherGradebook extends AbstractFragment {

    @FindBy(className = "tabs")
    WebElement tabs;

    private static final String GRADEBOOK = "GRADEBOOK";
    private static final String DEADLINE = "DEADLINE";

    public static TeacherGradebook getInstance(SearchContext context) {
        Graphene.waitGui().until().element(context, cssSelector(".row.ng-scope .content h1")).text()
                .equalToIgnoreCase(MenuNavigation.Navigator.TEACHER_GRADEBOOK.getTitlePage());
        return Graphene.createPageFragment(TeacherGradebook.class,
                waitForElementVisible(cssSelector(".row.ng-scope .content"), context));
    }

    public SubmitTab selectSubmitTab() {
        selectTab(TO_SUBMIT);
        return new SubmitTab();
    }

    public SearchTab selectSearchTab() {
        selectTab(SEARCH_ALL);
        return new SearchTab();
    }

    public class SubmitTab {

        public String getGradebook(int number) {
            return getGradebooks().get(number -1).get(GRADEBOOK);
        }

        public String getDeadline(int number) {
            return getGradebooks().get(number -1).get(DEADLINE);
        }

        public int countGradebook() {
            return getGradebooks().size();
        }

        private List<HashMap<String, String>> getGradebooks() {
            return root.findElements(cssSelector("#needsubmit tbody tr")).stream().map(row -> {
                List<String> column = row.findElements(tagName("td")).stream().map(WebElement::getText).collect(Collectors.toList());
                HashMap<String, String> record = new HashMap<>();
                record.put(GRADEBOOK, column.get(1));
                record.put(DEADLINE, column.get(2));
                return record;
            }).collect(Collectors.toList());
        }
    }

    public class SearchTab {

    }

    private TeacherGradebook selectTab(Tab tab) {
        String titleTab = tab.getTitle();
        if (!waitForElementVisible(className("active"), tabs).getText().equals(titleTab)) {
            waitForElementVisible(xpath(format("//*[contains(text(),'%s')]", titleTab)), tabs).click();
            Graphene.waitGui().until(browser -> waitForElementVisible(className("active"), tabs).getText().equals(titleTab));
        }
        return this;
    }

    public enum Tab {

        TO_SUBMIT("To submit"),
        SEARCH_ALL("Search all");

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
