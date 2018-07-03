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

    @SuppressWarnings("unused")
    public static WebDriver getLocalWebDriver() {
        final String webDriverPath = System.getenv("WEBDRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        final WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(TIME_TO_IMPLICIT_WAIT, TimeUnit.SECONDS);

        return driver;
    }

    public static WebDriver getRemoteWebDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("chrome_67.0");
        capabilities.setCapability("enableVNC", true);

        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(
                    URI.create("http://178.128.36.229:4444/wd/hub").toURL(),
                    capabilities
            );
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(TIME_TO_IMPLICIT_WAIT, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }
}
