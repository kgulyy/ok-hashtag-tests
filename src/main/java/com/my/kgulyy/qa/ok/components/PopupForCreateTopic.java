package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PopupForCreateTopic {
    private static final String CREATE_TOPIC_FIELD_XPATH = "//a[contains(@data-l, \"OpenPostingPopup\")]";
    private static final String TOPIC_TEXT_FIELD_XPATH = "//div[@data-module=\"postingForm/mediaText\"]";
    private static final String SUBMIT_BUTTON_XPATH = "//div[@class=\"posting_submit button-pro\"]";

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
    }

    public void submit() {
        final WebElement submitButton = driver.findElement(By.xpath(SUBMIT_BUTTON_XPATH));
        submitButton.click();
    }
}
