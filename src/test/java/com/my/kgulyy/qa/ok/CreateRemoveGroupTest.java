package com.my.kgulyy.qa.ok;

import com.my.kgulyy.qa.ok.components.MainPage;
import com.my.kgulyy.qa.ok.pageobjects.Auth;
import com.my.kgulyy.qa.ok.pageobjects.PublicPage;
import com.my.kgulyy.qa.utils.DriverUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class CreateRemoveGroupTest {
    private static final String PAGE_NAME = "My page";

    private final WebDriver driver = DriverUtils.getWebDriver();

    @Before
    public void setUp() {
        final Auth auth = new Auth(driver);
        auth.signIn();
    }

    @Test
    public void createRemovePublicPage() {
        final MainPage mainPage = new MainPage(driver);
        mainPage.openGroupsPage();

        final int numberOfGroupsBefore = mainPage.getNumberOfGroups();

        final PublicPage publicPage = new PublicPage(driver);
        publicPage.create(PAGE_NAME);

        final String pageName = publicPage.getName();
        Assert.assertEquals(PAGE_NAME, pageName);

        publicPage.remove();

        final int numberOfGroupsAfter = mainPage.getNumberOfGroups();
        Assert.assertEquals(numberOfGroupsBefore, numberOfGroupsAfter);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
