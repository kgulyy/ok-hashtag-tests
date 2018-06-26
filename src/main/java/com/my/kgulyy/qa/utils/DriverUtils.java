package com.my.kgulyy.qa.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class DriverUtils {
    private static final long TIME_TO_IMPLICIT_WAIT = 1;

    public static WebDriver getWebDriver() {
        final String webDriverPath = System.getenv("WEBDRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        final WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(TIME_TO_IMPLICIT_WAIT, TimeUnit.SECONDS);

        return driver;
    }
}
