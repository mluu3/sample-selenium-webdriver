package test.java.abstracts;

import static test.java.Ultil.BrowserUtils.getCurrentBrowserAgent;

import org.json.JSONException;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public abstract  class AbstractProjectTest extends AbstractUITest {

    @Test(groups = {"createProject"})
    @Parameters({"windowSize"})
    public void init(@Optional("maximize") String windowSize) throws JSONException {
        System.out.println("Current browser agent is: " + getCurrentBrowserAgent(browser).toUpperCase());

        // override default value of properties
        initProperties();

        // adjust window size to run on mobile mode
        if (!windowSize.equals("maximize")) adjustWindowSize(windowSize);

        // sign in with admin user
        signIn("mngr169304", "YqUdeje");
    }

    protected void initProperties() {
        // should be implemented later in abstract test or test classes
    }

    private void adjustWindowSize(String windowSize) {
        // this is now using for ui-tests-dashboards only
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
