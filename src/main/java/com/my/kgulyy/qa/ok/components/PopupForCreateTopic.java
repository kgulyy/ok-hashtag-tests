package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PopupForCreateTopic {
    private static final String CREATE_TOPIC_FIELD_XPATH = "//a[contains(@data-l, \"OpenPostingPopup\")]";
    private static final String TOPIC_TEXT_FIELD_XPATH = "//div[@data-module=\"postingForm/mediaText\"]";
    private static final String TOPIC_TEXT_FIELD_BLOCK_TEMPLATE_XPATH = "//div[@data-module=\"postingForm/mediaText\"]/div[text()=\"%s\"]";
    private static final String SUBMIT_BUTTON_XPATH = "//div[@data-action='submit']";

    private static final long TIMEOUT_IN_SECONDS = 10;
    private static final long SLEEP_IN_MILLISECONDS = 100;

    private final WebDriver driver;

    public PopupForCreateTopic(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        final WebElement createTopicField = driver.findElement(By.xpath(CREATE_TOPIC_FIELD_XPATH));
        createTopicField.click();
    }

    public void setText(String text) {
        final WebElement textField = driver.findElement(By.xpath(TOPIC_TEXT_FIELD_XPATH));
        textField.sendKeys(text);
        waitingForTextInput(text);
    }

    private void waitingForTextInput(String text) {
        final String textFieldBlockXPath = String.format(TOPIC_TEXT_FIELD_BLOCK_TEMPLATE_XPATH, text);
        final WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS, SLEEP_IN_MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(textFieldBlockXPath)));
    }

    public void submit() {
        final WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS, SLEEP_IN_MILLISECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SUBMIT_BUTTON_XPATH)));
        final WebElement submitButton = driver.findElement(By.xpath(SUBMIT_BUTTON_XPATH));
        submitButton.click();
    }
}
