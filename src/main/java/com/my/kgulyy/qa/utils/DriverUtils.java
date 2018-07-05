package com.my.kgulyy.qa.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverUtils {
    private static final long TIME_TO_IMPLICIT_WAIT = 1;

    private DriverUtils() {
    }

    public static WebDriver getWebDriver() {
        String webDriverType = System.getenv("webdriver.type");
        if (webDriverType == null) {
            webDriverType = "local";
        }
        final WebDriver driver;
        switch (webDriverType) {
            case "remote":
                driver = getRemoteWebDriver();
                break;
            case "local":
            default:
                driver = getLocalWebDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(TIME_TO_IMPLICIT_WAIT, TimeUnit.SECONDS);

        return driver;
    }

    private static WebDriver getLocalWebDriver() {
        final String webDriverPath = System.getenv("webdriver.path");
        System.setProperty("webdriver.chrome.driver", webDriverPath);

        return new ChromeDriver();
    }

    private static WebDriver getRemoteWebDriver() {
        final String remoteUrlStr = System.getenv("webdriver.remote.url");
        final URL remoteURL = StringUtils.getUrlFromString(remoteUrlStr);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("67.0");
        capabilities.setCapability("enableVNC", true);

        return new RemoteWebDriver(remoteURL, capabilities);
    }
}
