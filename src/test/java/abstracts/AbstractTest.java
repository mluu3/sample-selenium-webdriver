package test.java.abstracts;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.testng.Arquillian;
import org.openqa.selenium.WebDriver;
import test.java.ultil.BrowserUtils;

import java.util.logging.Logger;

public abstract class AbstractTest extends Arquillian {

    @Drone
    protected WebDriver browser;

    protected static final Logger log = Logger.getLogger(AbstractTest.class.getName());

    public String getRootUrl() {
        return "https://openweathermap.org/";
    }

    public void openUrl(String url) {
        String pageURL;

        if (url.contains(getRootUrl())) {
            pageURL = url.replaceAll("^/", "");
        } else {
            pageURL = getRootUrl() + url.replaceAll("^/", "");
        }

        System.out.println("Loading page ... " + pageURL);

        BrowserUtils.addMagicElementToDOM(browser);
        // Request Selenium to load a URL. If current URL is the one we want to load, page is NOT reloaded.
        browser.get(pageURL);

        // If Magic element is still present, refresh the page.
        for (int attempts = 0; attempts < 3; attempts++) {
            if (!BrowserUtils.isMagicElementPresentInDOM(browser)) {
                return;
            }

            browser.navigate().refresh();
        }

        log.warning("Page content might not be fully reloaded");
    }
}
