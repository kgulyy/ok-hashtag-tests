package com.my.kgulyy.qa.ok.pageobjects;

import com.my.kgulyy.qa.ok.components.AuthForm;
import org.openqa.selenium.WebDriver;

public class Auth {
    private final WebDriver driver;

    public Auth(WebDriver driver) {
        this.driver = driver;
    }

    public void signIn() {
        final AuthForm authForm = new AuthForm(driver);
        authForm.open();

        final String login = System.getenv("login");
        authForm.setLogin(login);

        final String password = System.getenv("password");
        authForm.setPassword(password);

        authForm.submit();
    }
}
