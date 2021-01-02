package test.java.weather;

import org.testng.annotations.Test;
import test.java.abstracts.AbstractProjectTest;

public class LoginTest extends AbstractProjectTest {


    @Test(dependsOnGroups = "createProject")
    public void loginTest() {
        initNewCustomerPage();
        System.out.print("Login Successfully");
    }
}
