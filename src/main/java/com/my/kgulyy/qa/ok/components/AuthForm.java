package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AuthForm {
    private static final String BASE_URL = "https://ok.ru/";

    private static final String LOGIN_ID = "field_email";
    private static final String PASSWORD_ID = "field_password";
    private static final String SUBMIT_XPATH = "//input[contains(@data-l,'sign_in')]";

    private final WebDriver driver;

    public AuthForm(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(BASE_URL);
    }

    public void setLogin(String login) {
        final WebElement loginField = driver.findElement(By.id(LOGIN_ID));
        loginField.sendKeys(login);
    }

    public void setPassword(String password) {
        final WebElement passwordField = driver.findElement(By.id(PASSWORD_ID));
        passwordField.sendKeys(password);
    }

    public void submit() {
        final WebElement loginButton = driver.findElement(By.xpath(SUBMIT_XPATH));
        loginButton.click();
    }
}
