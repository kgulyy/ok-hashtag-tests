package com.my.kgulyy.qa.ok.pageobjects;

import com.my.kgulyy.qa.ok.components.PopupForCreateTopic;
import com.my.kgulyy.qa.ok.components.TopicHashtags;
import com.my.kgulyy.qa.ok.components.TopicWidget;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class Topic {
    private static final String DEFAULT_TOPIC_TEXT = "1";

    private final WebDriver driver;
    private final TopicWidget topicWidget;
    private final TopicHashtags topicHashtags;

    public Topic(WebDriver driver) {
        this.driver = driver;
        topicWidget = new TopicWidget(driver);
        topicHashtags = new TopicHashtags(driver);
    }

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

    public void addTag(String tag) {
        topicHashtags.openTagsInput();
        topicHashtags.setTag(tag);
        topicHashtags.submit();
    }

    public void addAllTags(List<String> tags) {
        topicHashtags.openTagsInput();
        for (String tag : tags) {
            topicHashtags.setTag(tag);
        }
        topicHashtags.submit();
    }

    public void removeTag(String tag) {
        topicHashtags.openTagsInput();
        topicHashtags.removeTag(tag);
        topicHashtags.submit();
    }

    public void removeAllTags(List<String> tags) {
        topicHashtags.openTagsInput();
        for (String tag : tags) {
            topicHashtags.removeTag(tag);
        }
        topicHashtags.submit();
    }

    public void editTag(String tag, String newTag) {
        topicHashtags.editTags();
        topicHashtags.removeTag(tag);
        topicHashtags.setTag(newTag);
        topicHashtags.submit();
    }

    public void editHashtag(String tag, String newTag) {
        topicHashtags.openTagsInput();
        topicHashtags.removeTag(tag);
        topicHashtags.setTag(newTag);
        topicHashtags.submit();
    }

    public void editAllTags(List<String> tags, List<String> newTags) {
        topicHashtags.editTags();
        for (String tag : tags) {
            topicHashtags.removeTag(tag);
        }
        for (String tag : newTags) {
            topicHashtags.setTag(tag);
        }
        topicHashtags.submit();
    }

    public boolean isExistTempTag(String tag) {
        return topicHashtags.isExistTempTag(tag);
    }

    public boolean isExistHashtag(String hashtag) {
        return topicHashtags.isExistHashtag(hashtag);
    }

    public boolean noOneHashtags() {
        return topicHashtags.noOneHashtags();
    }

    public String getTagErrorMessage() {
        return topicHashtags.getErrorMessageText();
    }

    public int getRemainingTagLength() {
        return topicHashtags.getRemainingTagLength();
    }

    public int getNumberOfTempTags() {
        return topicHashtags.getNumberOfTempTags();
    }

    public int getNumberOfHashtags() {
        return topicHashtags.getNumberOfHashtags();
    }
}
