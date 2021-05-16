package com.autotest.core.abstracts;

import org.json.JSONException;
import org.openqa.selenium.By;
import com.autotest.core.fragment.LoginPage;
import com.autotest.core.fragment.TeacherGradebook;

import static org.openqa.selenium.By.className;
import static com.autotest.core.ultil.WaitUtils.waitForElementNotPresent;
import static com.autotest.core.ultil.WaitUtils.waitForElementPresent;

public abstract class AbstractUITest extends AbstractTest {

    private static final String ROOT_URL = "https://api.edugix.com/";
    private static final String TEACHER_GRADEBOOK_URL = ROOT_URL + "teacher/dashboard.html#/gradebook";

    protected LoginPage initLoginPage() throws JSONException {
        openUrl(getRootUrl());
        return LoginPage.getInstance(browser);
    }

    protected TeacherGradebook initTeacherGradebook() throws JSONException {
        openUrl(TEACHER_GRADEBOOK_URL);
//        waitForPageReloading();
        return TeacherGradebook.getInstance(browser);
    }

    protected void waitForPageReloading() {
        By loadingOverlay = className("loadingoverlay");
        try {
            waitForElementPresent(loadingOverlay, browser, 1);
            waitForElementNotPresent(loadingOverlay);
        } catch (Exception e) {
            //Nothing
        }
    }

    public String getRootUrl() {
        return ROOT_URL;
    }

    public void openUrl(String url) {
        String pageURL;

        if (url.contains(getRootUrl())) {
            pageURL = url.replaceAll("^/", "");
        } else {
            pageURL = getRootUrl() + url.replaceAll("^/", "");
        }

        System.out.println("Loading page ... " + pageURL);
        browser.get(pageURL);
    }
}
