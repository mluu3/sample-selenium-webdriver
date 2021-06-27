package com.autotest.core.abstracts;

import com.autotest.core.ultil.RestClient;
import com.autotest.core.ultil.testng.listener.AuxiliaryFailureScreenshotListener;
import com.autotest.core.ultil.testng.listener.ConsoleStatusListener;
import com.autotest.core.ultil.testng.listener.FailureLoggingListener;
import com.autotest.core.ultil.testng.listener.VideoRecorderListener;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.testng.Arquillian;
import org.json.JSONException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.autotest.core.ultil.BrowserUtils.getCurrentBrowserAgent;

@Listeners({ConsoleStatusListener.class, FailureLoggingListener.class, AuxiliaryFailureScreenshotListener.class, VideoRecorderListener.class})
public abstract class AbstractTest extends Arquillian {

    @Drone
    protected WebDriver browser;

    protected static final Logger log = Logger.getLogger(AbstractTest.class.getName());

    protected RestClient restClient;

    //================== API ======================

    protected RestClient getRestClient() {
        // if rest client is not created yet or the user of current rest client and the current admin user
        // is not the same, need to create new rest client
        if (Objects.isNull(restClient)) {
            log.info("Creating new rest client for current user: " + "user");
            restClient = new RestClient(
                    new RestClient.RestProfile(
                            "domain", "user", "password"));
        }
        return restClient;

    }

    //================== Web ======================



    //================== Window Size ======================

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
    //================== Window Size ======================
}
