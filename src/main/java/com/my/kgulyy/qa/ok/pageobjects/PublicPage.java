package com.my.kgulyy.qa.ok.pageobjects;

import com.my.kgulyy.qa.ok.components.GroupPage;
import com.my.kgulyy.qa.ok.components.PopupForCreateGroup;
import org.openqa.selenium.WebDriver;

public class PublicPage {
    private static final String DEFAULT_PAGE_NAME = "My Page";

    private final WebDriver driver;
    private final GroupPage groupPage;

    public PublicPage(WebDriver driver) {
        this.driver = driver;
        groupPage = new GroupPage(driver);
    }

    public void create() {
        create(DEFAULT_PAGE_NAME);
    }

    public void create(String name) {
        final PopupForCreateGroup popup = new PopupForCreateGroup(driver);
        popup.open();
        popup.createPublicPage();
        popup.setName(name);
        popup.submit();
    }

    @SuppressWarnings("unused")
    public void openFeedPage() {
        groupPage.openFeedPage();
    }

    @SuppressWarnings("unused")
    public void openForumPage() {
        groupPage.openForumPage();
    }

    public String getName() {
        return groupPage.getGroupName();
    }

    public void remove() {
        groupPage.removeGroup();
    }
}
