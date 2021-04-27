package test.java.weather;

import org.testng.annotations.Test;
import test.java.abstracts.AbstractUITest;
import test.java.fragment.Dialog;
import test.java.fragment.MenuNavigation;
import test.java.fragment.MenuNavigation.Navigator;
import test.java.fragment.TeacherGradebook;
import test.java.fragment.TeacherGradebook.SubmitTab;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static test.java.ultil.Screenshots.takeScreenshot;

public class SearchTest extends AbstractUITest {

    @Test
    public void testRoleTeacher() {
        initLoginPage()
                .login("teacherpnt2@apollo.edu.vn", "apollo6543@1")
                .selectCenter("Pham Ngoc Thach");
        assertEquals(MenuNavigation.getInstance(browser).listItems(), Stream.of(Navigator.values())
                .map(Navigator::getNavigator).collect(Collectors.toList()));

        TeacherGradebook teacherGradebook = initTeacherGradebook();
        Dialog dialog = Dialog.getInstance(browser);
        waitForPageReloading();
        assertEquals(dialog.getTitle(), "You have 1 gradebook waiting");
        dialog.confirm();
        SubmitTab submitTab = teacherGradebook.selectSubmitTab();
        assertEquals(submitTab.getGradebook(1), "HCM22104-C0008Diamond-0159 - Gradebook");
        assertEquals(submitTab.getDeadline(1), "22/04/2021");
        assertEquals(submitTab.countGradebook(), 1);
        takeScreenshot(browser, "abc", getClass());
    }
}
