package com.autotest.core.fragment;

import com.autotest.core.abstracts.AbstractFragment;
import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import static com.autotest.core.ultil.WaitUtils.waitForElementVisible;
import static org.openqa.selenium.By.className;

public class MenuNavigation extends AbstractFragment {

    @FindBy(css = ".menu-content>li")
    List<WebElement> items;

    public static MenuNavigation getInstance(SearchContext context) {
        return Graphene.createPageFragment(MenuNavigation.class,
                waitForElementVisible(className("nav-side-menu"), context));
    }

    public List<String> listItems() {
        return items.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public enum Navigator {

        ADMIN_RESOURCE("Admin resource", "ADMIN RESOURCE"),
        LESSON_RESOURCE("Lesson resource", "LESSON RESOURCE"),
        REPORTS("Reports", "REPORTS"),
        TEACHER_GRADEBOOK("Teacher Gradebook", "GRADEBOOK"),
        TWENTIETH_OR_EIGHTIETH_REPORT("20th / 80th Report", "20TH / 80TH REPORT"),
        END_OF_TOPIC_REPORT("End Of Topic Report", "END OF TOPIC REPORT"),
        MASTER_GRADEBOOK("Master Gradebook", "MASTER GRADEBOOK");
        private final String navigator;
        private final String titlePage;

        Navigator(String navigator, String titlePage) {
            this.navigator = navigator;
            this.titlePage = titlePage;
        }

        public String getNavigator() {
            return navigator;
        }

        public String getTitlePage() {
            return titlePage;
        }
    }
}
