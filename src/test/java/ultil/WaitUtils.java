package test.java.ultil;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import test.java.abstracts.AbstractFragment;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;

public final class WaitUtils {

    private static final Logger log = Logger.getLogger(WaitUtils.class.getName());
    private static final int TIMEOUT_WAIT_OLD_CLIENT_LOADED = 5 * 60; // minutes

    private WaitUtils() {
    }


    public static void waitForManagerPageLoaded(SearchContext searchContext) {
        waitForElementVisible(By.className("layout"),
                searchContext, TIMEOUT_WAIT_OLD_CLIENT_LOADED);
    }

    public static WebElement waitForElementVisible(By byElement, SearchContext searchContext) {
        Graphene.waitGui().until().element(searchContext, byElement).is().visible();
        return searchContext.findElement(byElement);
    }

    public static WebElement waitForElementVisible(By byElement, SearchContext searchContext, int timeout) {
        Graphene.waitGui().withTimeout(timeout, TimeUnit.SECONDS).until().element(searchContext, byElement).is().visible();
        return searchContext.findElement(byElement);
    }

    public static WebElement waitForElementVisible(WebElement element) {
        Graphene.waitGui().until().element(element).is().visible();
        return element;
    }

    public static WebElement waitForElementVisible(WebElement element, int timeout) {
        Graphene.waitGui().withTimeout(timeout, TimeUnit.SECONDS).until().element(element).is().visible();
        return element;
    }

    public static Select waitForElementVisible(Select select) {
        Graphene.waitGui().until().element(select.getFirstSelectedOption()).is().visible();
        return select;
    }

    public static <T extends AbstractFragment> T waitForFragmentVisible(T fragment) {
        Graphene.waitGui().until().element(fragment.getRoot()).is().visible();
        return fragment;
    }

    public static void waitForElementNotVisible(WebElement element) {
        Graphene.waitGui().until().element(element).is().not().visible();
    }

    public static void waitForElementNotVisible(WebElement element, int timeout) {
        Graphene.waitGui().withTimeout(timeout, TimeUnit.SECONDS).until().element(element).is().not().visible();
    }

    public static void waitForElementNotVisible(By byElement) {
        Graphene.waitGui().until().element(byElement).is().not().visible();
    }

    public static void waitForElementNotVisible(By byElement, SearchContext searchContext) {
        Graphene.waitGui().until().element(searchContext, byElement).is().not().visible();
    }

    public static void waitForElementNotVisible(Select select) {
        Graphene.waitGui().until().element(select.getFirstSelectedOption()).is().not().visible();
    }

    public static void waitForFragmentNotVisible(AbstractFragment fragment) {
        Graphene.waitGui().until().element(fragment.getRoot()).is().not().visible();
    }

    public static WebElement waitForElementEnabled(final WebElement element) {
        // check for regularly disabled input, checks 'disabled' attribute, this will return true for the most elements
        // since they don't contain 'disabled' attribute
        Graphene.waitGui().until().element(element).is().enabled();

        // check for other elements styled as button, input, etc. that are disabled programmatically and styled with css
        Function<WebDriver, Boolean> elementEnabled = browser -> !element.getAttribute("class").contains("disabled");
        Graphene.waitGui().until(elementEnabled);

        return element;
    }

    public static WebElement waitForElementPresent(By byElement, SearchContext searchContext, int timeout) {
        Graphene.waitGui().withTimeout(timeout, TimeUnit.SECONDS).until().element(searchContext, byElement).is().present();
        return searchContext.findElement(byElement);
    }

    public static WebElement waitForElementPresent(By byElement, SearchContext searchContext) {
        Graphene.waitGui().until().element(searchContext, byElement).is().present();
        return searchContext.findElement(byElement);
    }

    public static WebElement waitForElementPresent(WebElement element) {
        Graphene.waitGui().until().element(element).is().present();
        return element;
    }

    public static Select waitForElementPresent(Select select) {
        Graphene.waitGui().until().element(select.getFirstSelectedOption()).is().present();
        return select;
    }

    public static void waitForElementNotPresent(By byElement) {
        Graphene.waitGui().until().element(byElement).is().not().present();
    }

    public static void waitForElementNotPresent(By byElement, SearchContext searchContext) {
        Graphene.waitGui().until().element(searchContext, byElement).is().not().present();
    }

    public static void waitForElementNotPresent(By byElement, int timeout) {
        Graphene.waitGui().withTimeout(timeout, TimeUnit.SECONDS).until().element(byElement).is().not().present();
    }

    public static void waitForElementNotPresent(WebElement element, int timeout) {
        Graphene.waitGui().withTimeout(timeout, TimeUnit.SECONDS).until().element(element).is().not().present();
    }

    public static void waitForElementNotPresent(WebElement element) {
        Graphene.waitGui().until().element(element).is().not().present();
    }

    public static void waitForElementNotPresent(Select select) {
        Graphene.waitGui().until().element(select.getFirstSelectedOption()).is().not().present();
    }

    public static void waitForCollectionIsEmpty(final Collection<?> items) {
        Function<WebDriver, Boolean> collectionEmpty = browser -> items.isEmpty();
        Graphene.waitGui().until(collectionEmpty);
    }

    public static void waitForElementAttributeContainValue(WebElement element, String attribute, String value) {
        Graphene.waitGui().until().element(element).attribute(attribute).contains(value);
    }

    public static void waitForElementAttributeNotContainValue(WebElement element, String attribute, String value) {
        Graphene.waitGui().until().element(element).attribute(attribute).not().contains(value);
    }

    public static <T extends Collection<?>> T waitForCollectionIsNotEmpty(final T items) {
        Function<WebDriver, Boolean> collectionNotEmpty = browser -> !items.isEmpty();
        Graphene.waitGui().until(collectionNotEmpty);

        return items;
    }

    public static void waitForStringInUrl(final String url) {
        Function<WebDriver, Boolean> containsString = driver -> driver.getCurrentUrl().contains(url);
        Graphene.waitGui().until(containsString);
    }

    public static void waitForStringMissingInUrl(final String url) {
        Function<WebDriver, Boolean> missingString = driver -> !driver.getCurrentUrl().contains(url);
        Graphene.waitGui().until(missingString);
    }

    public static void waitForExportReport(final File destination, final long minimalSize) {
        System.out.println("waiting for export " + destination.getName());

        Function<WebDriver, Boolean> isExportCompleted = browser -> destination.length() >= minimalSize;
        Graphene.waitGui().pollingEvery(5, TimeUnit.SECONDS)
                .withTimeout(5, TimeUnit.MINUTES)
                .until(isExportCompleted);
    }
}
