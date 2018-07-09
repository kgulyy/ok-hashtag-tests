package com.my.kgulyy.qa.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public final class DriverUtils {
    private static final long TIME_TO_IMPLICIT_WAIT = 1;

    private DriverUtils() {
    }

    public static WebDriver getWebDriver() {
        final String webDriverType = System.getenv("WEBDRIVER_TYPE");
        final WebDriver driver;
        if (webDriverType != null && webDriverType.equals("remote")) {
            driver = getRemoteWebDriver();
        } else {
            driver = getLocalWebDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(TIME_TO_IMPLICIT_WAIT, TimeUnit.SECONDS);

        return driver;
    }

    private static WebDriver getLocalWebDriver() {
        final String browserName = System.getenv("BROWSER_NAME");
        final String webDriverPath = System.getenv("WEBDRIVER_PATH");
        if (browserName != null && browserName.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", webDriverPath);
            return new FirefoxDriver();
        }
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        return new ChromeDriver();
    }

    private static WebDriver getRemoteWebDriver() {
        final String browserName = System.getenv("BROWSER_NAME");
        final String browserVersion = System.getenv("BROWSER_VERSION");
        final String remoteUrlStr = System.getenv("WEBDRIVER_REMOTE_URL");
        final URL remoteURL = StringUtils.getUrlFromString(remoteUrlStr);

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        if (browserName != null && browserVersion != null) {
            capabilities.setBrowserName(browserName);
            capabilities.setVersion(browserVersion);
        } else {
            capabilities.setBrowserName("chrome");
            capabilities.setVersion("67.0");
        }
        capabilities.setCapability("enableVNC", true);

        return new RemoteWebDriver(remoteURL, capabilities);
    }
}
