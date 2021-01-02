package test.java.abstracts;

import test.java.fragment.OpenWeatherMapPage;

public abstract class AbstractUITest extends AbstractTest {

    public OpenWeatherMapPage initHomePage() {
        openUrl("");
        return OpenWeatherMapPage.getInstance(browser);
    }
}
