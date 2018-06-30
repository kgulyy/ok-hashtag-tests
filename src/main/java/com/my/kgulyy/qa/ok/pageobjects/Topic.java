package com.my.kgulyy.qa.ok.pageobjects;

import com.my.kgulyy.qa.ok.components.PopupForCreateTopic;
import com.my.kgulyy.qa.ok.components.TopicWidget;
import org.openqa.selenium.WebDriver;

public class Topic {
    private static final String DEFAULT_TOPIC_TEXT = "My Topic";

    private final WebDriver driver;
    private final TopicWidget topicWidget;

    public Topic(WebDriver driver) {
        this.driver = driver;
        topicWidget = new TopicWidget(driver);
    }

    @SuppressWarnings("unused")
    public void create() {
        create(DEFAULT_TOPIC_TEXT);
    }

    public void create(String text) {
        final PopupForCreateTopic popup = new PopupForCreateTopic(driver);
        popup.open();
        popup.setText(text);
        popup.submit();
    }

    public String getText() {
        return topicWidget.getText();
    }

    public String getGroupName() {
        return topicWidget.getGroupName();
    }

    public String getAuthor() {
        return topicWidget.getAuthor();
    }
}
