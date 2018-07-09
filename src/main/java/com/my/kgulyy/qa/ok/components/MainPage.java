package com.my.kgulyy.qa.ok.components;

import com.my.kgulyy.qa.utils.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private static final String USERNAME_XPATH = "//a[contains(@data-l,'userPage')]/span";
    private static final String GROUPS_MENU_ITEM_XPATH = "//a[contains(@data-l,'userAltGroup')]";
    private static final String GROUPS_BLOCK_XPATH = "//div[@id='hook_Block_MyGroupsNavBlock']/div";
    private static final String NUMBER_OF_GROUPS_XPATH = "//a[starts-with(text(), 'Все мои группы')]";

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getUsername() {
        final WebElement username = driver.findElement(By.xpath(USERNAME_XPATH));
        return username.getText();
    }

    public void openGroupsPage() {
        final WebElement groupsMenuItem = driver.findElement(By.xpath(GROUPS_MENU_ITEM_XPATH));
        groupsMenuItem.click();
    }

    public int getNumberOfGroups() {
        if (areThereAnyGroups()) {
            final WebElement numberOfGroupsLink = driver.findElement(By.xpath(NUMBER_OF_GROUPS_XPATH));
            final String numberOfGroupsStr = numberOfGroupsLink.getText();
            return StringUtils.getDigitsFromString(numberOfGroupsStr);
        }
        return 0;
    }

    private boolean areThereAnyGroups() {
        try {
            driver.findElement(By.xpath(GROUPS_BLOCK_XPATH));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
