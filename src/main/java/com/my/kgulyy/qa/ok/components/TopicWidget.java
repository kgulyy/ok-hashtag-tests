package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TopicWidget {
    private static final String TOPIC_TEXT_XPATH = "//div[contains(@class,\"media-text_cnt_tx\")]";
    private static final String TOPIC_GROUP_NAME_XPATH = "//span[@class=\"shortcut-wrap\"]/a[contains(@hrefattrs,\"VisitProfile\")]";
    private static final String TOPIC_AUTHOR_XPATH = "//span[@class=\"shortcut-wrap\"]/a[contains(@hrefattrs,\"userMain\")]";

    private final WebDriver driver;

    public TopicWidget(WebDriver driver) {
        this.driver = driver;
    }

    public String getText() {
        final WebElement topicTextField = driver.findElement(By.xpath(TOPIC_TEXT_XPATH));
        return topicTextField.getText();
    }

    public String getGroupName() {
        final WebElement topicGroupNameField = driver.findElement(By.xpath(TOPIC_GROUP_NAME_XPATH));
        return topicGroupNameField.getText();
    }

    public String getAuthor() {
        final WebElement topicAuthorField = driver.findElement(By.xpath(TOPIC_AUTHOR_XPATH));
        return topicAuthorField.getText();
    }
}
