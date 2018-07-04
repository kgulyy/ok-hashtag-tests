package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PopupForCreateGroup {
    private static final String CREATE_GROUP_XPATH = "//div[@class=\"create-group\"]/a";
    private static final String CREATE_PUBLIC_PAGE_XPATH = "//a[contains(@data-l,\"PAGE\")]";
    private static final String NAME_FIELD_ID = "field_name";
    private static final String SUBMIT_ID = "hook_FormButton_button_create";
    private static final String POPUP_CLASS_NAME = "modal-new_center";

    private static final long TIMEOUT_IN_SECONDS = 10;
    private static final long SLEEP_IN_MILLISECONDS = 100;

    private final WebDriver driver;

    public PopupForCreateGroup(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        final WebElement createGroupButton = driver.findElement(By.xpath(CREATE_GROUP_XPATH));
        createGroupButton.click();
    }

    public void createPublicPage() {
        final WebElement createPublicPageButton = driver.findElement(By.xpath(CREATE_PUBLIC_PAGE_XPATH));
        createPublicPageButton.click();
    }

    public void setName(String groupName) {
        final WebElement nameField = driver.findElement(By.id(NAME_FIELD_ID));
        nameField.sendKeys(groupName);
    }

    public void submit() {
        final WebElement submitButton = driver.findElement(By.id(SUBMIT_ID));
        submitButton.click();
        waitClosing();
    }

    private void waitClosing() {
        final WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS, SLEEP_IN_MILLISECONDS);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(POPUP_CLASS_NAME)));
    }
}
