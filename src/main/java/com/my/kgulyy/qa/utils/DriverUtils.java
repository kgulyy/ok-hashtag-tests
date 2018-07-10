package com.my.kgulyy.qa.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public final class DriverUtils {
    private static final long TIME_TO_IMPLICIT_WAIT = 1;
    private static final long TIMEOUT_IN_SECONDS = 4;
    private static final long SLEEP_IN_MILLISECONDS = 100;

    private DriverUtils() {
    }

    public static WebDriver getWebDriver() {
        final String webDriverType = System.getenv("WEBDRIVER_TYPE");
        final WebDriver driver;
        if (webDriverType == null || webDriverType.equals("remove")) {
            driver = getRemoteWebDriver();
        } else {
            driver = getLocalWebDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(TIME_TO_IMPLICIT_WAIT, TimeUnit.SECONDS);

        return driver;
    }

    private static WebDriver getLocalWebDriver() {
        final String webDriverPath = System.getenv("WEBDRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        return new ChromeDriver();
    }

    private static WebDriver getRemoteWebDriver() {
        String browserName = System.getenv("BROWSER_NAME");
        if (browserName == null) {
            browserName = "chrome";
        }
        final String browserVersion = System.getenv("BROWSER_VERSION");
        final String remoteUrlStr = System.getenv("WEBDRIVER_REMOTE_URL");
        final URL remoteURL = StringUtils.getUrlFromString(remoteUrlStr);

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browserName);
        if (browserVersion != null) {
            capabilities.setVersion(browserVersion);
        }
        capabilities.setCapability("enableVNC", true);

        return new RemoteWebDriver(remoteURL, capabilities);
    }

    public static WebDriverWait getWebDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, TIMEOUT_IN_SECONDS, SLEEP_IN_MILLISECONDS);
    }
}
