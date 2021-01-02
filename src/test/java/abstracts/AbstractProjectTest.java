package test.java.abstracts;

import static test.java.ultil.BrowserUtils.getCurrentBrowserAgent;

import org.json.JSONException;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.java.ultil.RestClient;
import test.java.ultil.RestClient.RestProfile;

import java.util.Objects;

public abstract class AbstractProjectTest extends AbstractUITest {

    private RestClient restClient;

    @Test(groups = {"createProject"})
    @Parameters({"windowSize"})
    public void init(@Optional("maximize") String windowSize) throws JSONException {
        System.out.println("Current browser agent is: " + getCurrentBrowserAgent(browser).toUpperCase());

        // override default value of properties
        initProperties();

        // adjust window size to run on mobile mode
        if (!windowSize.equals("maximize")) adjustWindowSize(windowSize);
    }

    protected void initProperties() {
        // should be implemented later in abstract test or test classes
    }

    //This is a basic test therefore don't need security and user as public
    protected RestClient getRestClient() {
        // if rest client is not created yet or the user of current rest client and the current admin user
        // is not the same, need to create new rest client
        if (Objects.isNull(restClient)) {
            log.info("Creating new rest client for current user: " + "luuthanhquocminh@gmail.com");
            restClient = new RestClient(
                    new RestProfile(
                            "api.openweathermap.org", "luuthanhquocminh@gmail.com", "Minh2708"));
        }
        return restClient;
    }

    private void adjustWindowSize(String windowSize) {
        // if having wide use, we should consider dimension properties
        // drone definition: http://arquillian.org/arquillian-extension-drone/#webdriver-configuration (see property named dimensions)
        // e.g.: https://github.com/arquillian/arquillian-extension-drone/blob/master/drone-webdriver/src/test/resources/arquillian.xml#L31
        String[] dimensions = windowSize.split(",");
        if (dimensions.length == 2) {
            try {
                setWindowSize(Integer.valueOf(dimensions[0]), Integer.valueOf(dimensions[1]));
            } catch (NumberFormatException e) {
                throw new IllegalStateException("ERROR: Invalid window size given: " + windowSize);
            }
        } else {
            throw new IllegalStateException("ERROR: Invalid window size given: " + windowSize);
        }
    }

    private void setWindowSize(final int width, final int height) {
        log.info("resizing window to " + width + "x" + height);
        browser.manage().window().setSize(new Dimension(width, height));
    }
}
