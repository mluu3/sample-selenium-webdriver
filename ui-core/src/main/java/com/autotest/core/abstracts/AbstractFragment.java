package com.autotest.core.abstracts;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.logging.Logger;

import static java.util.Objects.isNull;

public abstract class AbstractFragment {

    @Root
    protected WebElement root;

    @Drone
    protected WebDriver browser;

    private Actions actions;

    protected static final Logger log = Logger.getLogger(AbstractFragment.class.getName());

    public WebElement getRoot() {
        return root;
    }

    protected Actions getActions() {
        if (isNull(actions)) {
            actions = new Actions(browser);
        }
        return actions;
    }
}
