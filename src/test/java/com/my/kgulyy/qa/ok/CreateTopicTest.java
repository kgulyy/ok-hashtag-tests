package com.my.kgulyy.qa.ok;

import com.my.kgulyy.qa.ok.components.MainPage;
import com.my.kgulyy.qa.ok.pageobjects.Auth;
import com.my.kgulyy.qa.ok.pageobjects.PublicPage;
import com.my.kgulyy.qa.ok.pageobjects.Topic;
import com.my.kgulyy.qa.utils.DriverUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class CreateTopicTest {
    private static final String PAGE_NAME = "My Page";
    private static final String AUTHOR = "Феофан Лампер";
    private static final String TOPIC_TEXT = "1";

    private final WebDriver driver;
    private final PublicPage publicPage;

    public CreateTopicTest() {
        driver = DriverUtils.getWebDriver();
        publicPage = new PublicPage(driver);
    }

    @Before
    public void setUp() {
        final Auth auth = new Auth(driver);
        auth.signIn();
        final MainPage mainPage = new MainPage(driver);
        mainPage.openGroupsPage();
        publicPage.create(PAGE_NAME);
    }

    @Test
    public void createTopic() {
        final Topic topic = new Topic(driver);
        topic.create(TOPIC_TEXT);

        final String topicText = topic.getText();
        Assert.assertEquals(TOPIC_TEXT, topicText);

        final String groupName = topic.getGroupName();
        Assert.assertEquals(PAGE_NAME, groupName);

        final String author = topic.getAuthor();
        Assert.assertEquals(AUTHOR, author);
    }

    @After
    public void tearDown() {
        publicPage.openFeedPage();
        publicPage.remove();
        driver.quit();
    }
}
