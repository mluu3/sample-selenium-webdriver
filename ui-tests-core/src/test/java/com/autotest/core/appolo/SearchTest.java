package com.autotest.core.appolo;

import com.autotest.core.abstracts.AbstractUITest;
import com.autotest.core.fragment.Dialog;
import com.autotest.core.fragment.MenuNavigation;
import com.autotest.core.fragment.MenuNavigation.Navigator;
import com.autotest.core.fragment.TeacherGradebook;
import com.autotest.core.fragment.TeacherGradebook.SubmitTab;
import org.testng.annotations.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.autotest.core.ultil.Screenshots.takeScreenshot;
import static org.testng.Assert.assertEquals;

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
        assertEquals(dialog.getTitle(), "You have 1 gradebook1111 waiting");
        dialog.confirm();
        SubmitTab submitTab = teacherGradebook.selectSubmitTab();
        assertEquals(submitTab.getGradebook(1), "HCM22104-C0008Diamond-0159 - Gradebook");
        assertEquals(submitTab.getDeadline(1), "22/04/2021");
        assertEquals(submitTab.countGradebook(), 1);
        takeScreenshot(browser, "abc", getClass());
    }
}
