package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GroupPage {
    private static final String PAGE_HEADER_CLASS_NAME = "mctc_name_tx";
    private static final String FEED_PAGE_LINK_XPATH = "//a[contains(@data-l,\"NavMenu_AltGroup_Main\")]";
    private static final String FORUM_PAGE_LINK_XPATH = "//a[contains(@data-l,\"NavMenu_AltGroup_Forum\")]";
    private static final String OTHER_ACTIONS_BUTTON_CLASS_NAME = "tico_simb_txt";
    private static final String REMOVE_BUTTON_XPATH = "//a[contains(@hrefattrs,\"RemoveAltGroup\")]";
    private static final String SUBMIT_REMOVE_BUTTON_XPATH = "//input[@name=\"button_delete\"]";

    private final WebDriver driver;

    public GroupPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getGroupName() {
        final WebElement pageHeader = driver.findElement(By.className(PAGE_HEADER_CLASS_NAME));
        return pageHeader.getText();
    }

    public void openFeedPage() {
        final WebElement feedPageLink = driver.findElement(By.xpath(FEED_PAGE_LINK_XPATH));
        feedPageLink.click();
    }

    public void openForumPage() {
        final WebElement forumPageLink = driver.findElement(By.xpath(FORUM_PAGE_LINK_XPATH));
        forumPageLink.click();
    }

    public void removeGroup() {
        final WebElement otherActionsButton = driver.findElement(By.className(OTHER_ACTIONS_BUTTON_CLASS_NAME));
        otherActionsButton.click();
        final WebElement removeButton = driver.findElement(By.xpath(REMOVE_BUTTON_XPATH));
        removeButton.click();
        final WebElement submitRemoveButton = driver.findElement(By.xpath(SUBMIT_REMOVE_BUTTON_XPATH));
        submitRemoveButton.click();
    }
}
