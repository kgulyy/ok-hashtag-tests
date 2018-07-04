package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TopicHashtags {
    private static final String ADD_TAGS_BUTTON_XPATH = "//a[contains(@href,\"AddTopicTag\")]";
    private static final String EDIT_TAG_BUTTON_XPATH = "//a[contains(@href,\"EditTopicTag\")]";

    private static final String REMOVE_TAG_BUTTON_TEMPLATE_XPATH = "//div[@class=\"tag\"]//span[../../span=\"%s\"]";
    private static final String SUBMIT_BUTTON_XPATH = "//span[contains(@class,\"tag-box_button\")]";

    private static final String TAG_XPATH = "//a[contains(@hrefattrs,\"_TopicTag\")]";
    private static final String TAG_TEMPLATE_XPATH = "//a[contains(@hrefattrs,\"_TopicTag\") and text()=\"%s\"]";
    private static final String TAGS_INPUT_XPATH = "//input[@name=\"st.newTag\"]";

    private static final String HASHTAG_XPATH = "//a[contains(@class,\"__hashtag\")]";
    private static final String HASHTAG_TEMPLATE_XPATH = "//a[contains(@class,\"__hashtag\") and text()=\"#%s\"]";

    private static final String ERROR_MESSAGE_XPATH = "//span[@class=\"input-e\"]";
    private static final String TAG_LENGTH_COUNTER_XPATH = "//span[contains(@class,\"txt-counter\")]";

    private static final long TIMEOUT_IN_SECONDS = 10;
    private static final long SLEEP_IN_MILLISECONDS = 100;

    private final WebDriver driver;

    public TopicHashtags(WebDriver driver) {
        this.driver = driver;
    }

    public void openTagsInput() {
        final WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS, SLEEP_IN_MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_TAGS_BUTTON_XPATH)));
        final WebElement addTagButton = driver.findElement(By.xpath(ADD_TAGS_BUTTON_XPATH));
        addTagButton.click();
    }

    public void setTag(String tag) {
        final WebElement tagsInput = driver.findElement(By.xpath(TAGS_INPUT_XPATH));
        tagsInput.sendKeys(tag);
        tagsInput.sendKeys(Keys.ENTER);
    }

    public void removeTag(String tag) {
        final String removeTagButtonXPath = String.format(REMOVE_TAG_BUTTON_TEMPLATE_XPATH, tag);
        final WebElement removeTagButton = driver.findElement(By.xpath(removeTagButtonXPath));
        removeTagButton.click();
    }

    public void editTags() {
        final WebElement editTagButton = driver.findElement(By.xpath(EDIT_TAG_BUTTON_XPATH));
        editTagButton.click();
    }

    public boolean isExistTempTag(String tag) {
        try {
            final String tagElementXPath = String.format(TAG_TEMPLATE_XPATH, tag);
            driver.findElement(By.xpath(tagElementXPath));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean noOneHashtags() {
        try {
            driver.findElement(By.xpath(HASHTAG_XPATH));
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

    public boolean isExistHashtag(String hashtag) {
        try {
            final String hashtagElementXPath = String.format(HASHTAG_TEMPLATE_XPATH, hashtag);
            driver.findElement(By.xpath(hashtagElementXPath));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void submit() {
        final WebElement submitButton = driver.findElement(By.xpath(SUBMIT_BUTTON_XPATH));
        submitButton.click();
    }

    public String getErrorMessageText() {
        final WebElement errorMessage = driver.findElement(By.xpath(ERROR_MESSAGE_XPATH));
        return errorMessage.getText();
    }

    public int getRemainingTagLength() {
        final WebElement tagLengthCounter = driver.findElement(By.xpath(TAG_LENGTH_COUNTER_XPATH));
        final String remainingTagLengthStr = tagLengthCounter.getText();
        return Integer.parseInt(remainingTagLengthStr);
    }

    public int getNumberOfTempTags() {
        final List<WebElement> listOfTag = driver.findElements(By.xpath(TAG_XPATH));
        return listOfTag.size();
    }

    public int getNumberOfHashtags() {
        final List<WebElement> listOfHashtag = driver.findElements(By.xpath(HASHTAG_XPATH));
        return listOfHashtag.size();
    }
}
