package test.java.Ultil;

import org.jboss.arquillian.drone.api.annotation.Default;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserUtils {

    private static final String MAGIC = "MagicElementClassForPageReload";

    public static void switchToMainWindow(WebDriver browser) {
        browser.switchTo().defaultContent();
    }

    public static void addMagicElementToDOM(WebDriver browser) {
        // Use to avoid MAGIC element added in an embedded iframe and Selenium cannot identify it in default content
        switchToMainWindow(browser);

        String script = "var magic = document.createElement('div'); magic.className='" + MAGIC + "'; "
                + "document.getElementsByTagName('body')[0].appendChild(magic);";

        // When previous action causes page redirect, magic element can be put to DOM
        // and be immediately replaced with new DOM. Let's be safe and do silent retries.
        for (int attempts = 0; attempts < 3; attempts++) {
            ((JavascriptExecutor) browser).executeScript(script);

            if (ElementUtils.isElementPresent(By.className(MAGIC), browser)) {
                return;
            }
        }

        throw new NoSuchElementException("Cannot find magic element in DOM");
    }

    public static boolean isMagicElementPresentInDOM(WebDriver browser) {
        return browser.findElements(By.className(MAGIC)).size() > 0;
    }

    public static String getCurrentBrowserAgent(WebDriver browser) {
        Capabilities capabilities = ((RemoteWebDriver) browser).getCapabilities();

        return capabilities.getBrowserName() + " " + capabilities.getVersion() + " - "
                + capabilities.getCapability("platform");
    }

    public static WebDriver getBrowserContext() {
        return GrapheneContext.getContextFor(Default.class).getWebDriver(WebDriver.class);
    }

    public static Object runScript(WebDriver driver, String script, Object... args) {
        if (driver instanceof JavascriptExecutor) {
            return ((JavascriptExecutor) driver).executeScript(script, args);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
    }
}
