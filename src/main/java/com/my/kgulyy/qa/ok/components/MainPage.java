package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private static final String USERNAME_XPATH = "//a[contains(@data-l,\"userPage\")]/span";

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getUsername() {
        final WebElement username = driver.findElement(By.xpath(USERNAME_XPATH));
        return username.getText();
    }
}
