package com.my.kgulyy.qa.ok.pageobjects;

import com.my.kgulyy.qa.ok.components.GroupPage;
import com.my.kgulyy.qa.ok.components.PopupForCreateGroup;
import org.openqa.selenium.WebDriver;

public class Group {
    private static final String DEFAULT_GROUP_NAME = "My Group";

    private final WebDriver driver;
    private final GroupPage groupPage;

    public Group(WebDriver driver) {
        this.driver = driver;
        groupPage = new GroupPage(driver);
    }

    public void create() {
        create(DEFAULT_GROUP_NAME);
    }

    public void create(String name) {
        final PopupForCreateGroup popup = new PopupForCreateGroup(driver);
        popup.open();
        popup.createPublicPage();
        popup.setName(name);
        popup.setSubcategory();
        popup.submit();
    }

    public void openFeedPage() {
        groupPage.openFeedPage();
    }

    public String getName() {
        return groupPage.getGroupName();
    }

    public void remove() {
        groupPage.removeGroup();
    }
}
