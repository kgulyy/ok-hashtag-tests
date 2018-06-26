package com.my.kgulyy.qa.ok;

import com.my.kgulyy.qa.ok.components.MainPage;
import com.my.kgulyy.qa.ok.pageobjects.Auth;
import com.my.kgulyy.qa.utils.DriverUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class AuthTest {
    private static final String USERNAME = "Феофан Лампер";

    private static WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverUtils.getWebDriver();
    }

    @Test
    public void auth() {
        final Auth auth = new Auth(driver);
        auth.signIn();

        final MainPage mainPage = new MainPage(driver);
        final String username = mainPage.getUsername();
        Assert.assertEquals(USERNAME, username);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
