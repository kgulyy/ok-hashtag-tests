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
    private static final long TIMEOUT_IN_SECONDS = 3;
    private static final long SLEEP_IN_MILLISECONDS = 100;
    private static final String REMOTE_WEBDRIVER_TYPE = "remote";
    private static final String DEFAULT_BROWSER_NAME = "chrome";
    private static final String DEFAULT_BROWSER_VERSION = "67.0";
    private static final String ENABLE_VNC = "enableVNC";

    private DriverUtils() {
    }

    public static WebDriver getWebDriver() {
        final String webDriverType = System.getenv("WEBDRIVER_TYPE");
        final WebDriver driver;
        if (webDriverType == null || webDriverType.equals(REMOTE_WEBDRIVER_TYPE)) {
            driver = getRemoteWebDriver();
        } else {
            driver = getLocalWebDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(TIME_TO_IMPLICIT_WAIT, TimeUnit.SECONDS);

        return driver;
    }

    private static WebDriver getRemoteWebDriver() {
        String browserName = System.getenv("BROWSER_NAME");
        String browserVersion = System.getenv("BROWSER_VERSION");
        if (browserName == null && browserVersion == null) {
            browserName = DEFAULT_BROWSER_NAME;
            browserVersion = DEFAULT_BROWSER_VERSION;
        }
        final String remoteUrlStr = System.getenv("WEBDRIVER_REMOTE_URL");
        final URL remoteURL = StringUtils.getUrlFromString(remoteUrlStr);

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browserName);
        capabilities.setVersion(browserVersion);
        capabilities.setCapability(ENABLE_VNC, true);

        return new RemoteWebDriver(remoteURL, capabilities);
    }

    private static WebDriver getLocalWebDriver() {
        final String webDriverPath = System.getenv("WEBDRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        return new ChromeDriver();
    }

    public static WebDriverWait getWebDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, TIMEOUT_IN_SECONDS, SLEEP_IN_MILLISECONDS);
    }
}
