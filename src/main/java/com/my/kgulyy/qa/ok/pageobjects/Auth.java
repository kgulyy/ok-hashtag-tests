package com.my.kgulyy.qa.ok.pageobjects;

import com.my.kgulyy.qa.ok.components.AuthForm;
import org.openqa.selenium.WebDriver;

public class Auth {
    private final WebDriver driver;

    public Auth(WebDriver driver) {
        this.driver = driver;
    }

    public void signIn() {
        String login = System.getenv("LOGIN");
        String password = System.getenv("PASSWORD");

        AuthForm authForm = new AuthForm(driver);
        authForm.setLogin(login);
        authForm.setPassword(password);
        authForm.submit();
    }
}
