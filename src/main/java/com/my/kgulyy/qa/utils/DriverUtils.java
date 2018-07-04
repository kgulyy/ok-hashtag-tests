package com.my.kgulyy.qa.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class DriverUtils {
    private static final long TIME_TO_IMPLICIT_WAIT = 1;

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
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("67.0");
        capabilities.setCapability("enableVNC", true);

        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(
                    URI.create("http://178.128.36.229:4444/wd/hub").toURL(),
                    capabilities
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }
}
