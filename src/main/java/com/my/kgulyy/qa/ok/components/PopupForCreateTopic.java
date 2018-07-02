package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PopupForCreateTopic {
//    private static final String CREATE_TOPIC_FIELD_XPATH = "//a[contains(@data-l, \"OpenPostingPopup\")]";
    private static final String CREATE_TOPIC_FIELD_XPATH = "//div[@class=\"input_placeholder\"]";
//    private static final String TOPIC_TEXT_FIELD_XPATH = "//div[@data-module=\"postingForm/mediaText\"]";
    private static final String TOPIC_TEXT_FIELD_XPATH = "//div[contains(@class, \"ok-posting-handler\")]";
    private static final String SUBMIT_BUTTON_CSS = "div.posting_submit.button-pro";
    private static final String POPUP_ID = "mtLayerMain";

    private static final long TIMEOUT_IN_SECONDS = 30;
    private static final long SLEEP_IN_MILLISECONDS = 100;

    private final WebDriver driver;

    public PopupForCreateTopic(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        final WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS, SLEEP_IN_MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CREATE_TOPIC_FIELD_XPATH)));
        final WebElement createTopicField = driver.findElement(By.xpath(CREATE_TOPIC_FIELD_XPATH));
        createTopicField.click();
    }

    public void setText(String text) {
        final WebElement textField = driver.findElement(By.xpath(TOPIC_TEXT_FIELD_XPATH));
        textField.sendKeys(text);
    }

    public void submit() {
        final WebElement submitButton = driver.findElement(By.cssSelector(SUBMIT_BUTTON_CSS));
        submitButton.click();
        waitClosing();
    }

    private void waitClosing() {
        final WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS, SLEEP_IN_MILLISECONDS);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(POPUP_ID)));
    }
}
