package com.autotest.core.appolo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumTest {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("deviceName", "Samsung Galaxy S5");
//        capabilities.setCapability("platformVersion", "4.4.4");
//        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("app", "Browser");
//
//        WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
//        capabilities.setCapability("BROWSER_NAME", "Android");
//        capabilities.setCapability("VERSION", "4.4.2");
        capabilities.setCapability("deviceName", "iPhone 12");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "14.4");
//            capabilities.setCapability("app", "com.apple.calculator");
        capabilities.setCapability("bundleId", "com.apple.Maps");
        capabilities.setCapability("noRest", true);

        WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
//        driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Continue\"]")).click();
//
//        driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Don’t Allow\"]")).click();
        driver.findElement(By.xpath("//XCUIElementTypeSearchField[@name=\"Search for a place or address\"]")).sendKeys("Minh Test");

        Thread.sleep(5000);
        driver.quit();
    }
}
