package com.my.kgulyy.qa.ok.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PopupForCreateGroup {
    private static final String CREATE_GROUP_XPATH = "//div[@class='create-group']/a";
    private static final String CREATE_PUBLIC_PAGE_XPATH = "//a[contains(@data-l,'PAGE')]";
    private static final String NAME_FIELD_ID = "field_name";
    private static final String SUBCATEGORY_FIELD_XPATH = "//select[@id='field_pageMixedCategory']/option[@value='subcatVal12001']";
    private static final String SUBMIT_ID = "hook_FormButton_button_create";

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

    public void setSubcategory() {
        final WebElement subcategoryField = driver.findElement(By.xpath(SUBCATEGORY_FIELD_XPATH));
        subcategoryField.click();
    }

    public void submit() {
        final WebElement submitButton = driver.findElement(By.id(SUBMIT_ID));
        submitButton.click();
    }
}
