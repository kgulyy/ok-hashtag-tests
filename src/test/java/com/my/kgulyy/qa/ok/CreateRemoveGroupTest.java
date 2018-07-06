package com.my.kgulyy.qa.ok;

import com.my.kgulyy.qa.ok.components.MainPage;
import com.my.kgulyy.qa.ok.pageobjects.Auth;
import com.my.kgulyy.qa.ok.pageobjects.Group;
import com.my.kgulyy.qa.utils.DriverUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class CreateRemoveGroupTest {
    private static final String GROUP_NAME = "My Group";

    private final WebDriver driver = DriverUtils.getWebDriver();

    @Before
    public void setUp() {
        final Auth auth = new Auth(driver);
        auth.signIn();
    }

    @Test
    public void createRemoveGroup() {
        final MainPage mainPage = new MainPage(driver);
        mainPage.openGroupsPage();

        final int numberOfGroupsBefore = mainPage.getNumberOfGroups();

        final Group group = new Group(driver);
        group.create(GROUP_NAME);

        final String pageName = group.getName();
        Assert.assertEquals(GROUP_NAME, pageName);

        group.remove();

        final int numberOfGroupsAfter = mainPage.getNumberOfGroups();
        Assert.assertEquals(numberOfGroupsBefore, numberOfGroupsAfter);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
