package com.my.kgulyy.qa.ok;

import com.my.kgulyy.qa.ok.components.MainPage;
import com.my.kgulyy.qa.ok.pageobjects.Auth;
import com.my.kgulyy.qa.ok.pageobjects.Group;
import com.my.kgulyy.qa.ok.pageobjects.Topic;
import com.my.kgulyy.qa.utils.DriverUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class CreateTopicTest {
    private static final String GROUP_NAME = "My Group";
    private static final String AUTHOR = "Феофан Лампер";
    private static final String TOPIC_TEXT = "My Topic";

    private final WebDriver driver;
    private final Group group;

    public CreateTopicTest() {
        driver = DriverUtils.getWebDriver();
        group = new Group(driver);
    }

    @Before
    public void setUp() {
        final Auth auth = new Auth(driver);
        auth.signIn();
        final MainPage mainPage = new MainPage(driver);
        mainPage.openGroupsPage();
        group.create(GROUP_NAME);
    }

    @Test
    public void createTopic() {
        final Topic topic = new Topic(driver);
        topic.create(TOPIC_TEXT);

        final String topicText = topic.getText();
        Assert.assertEquals(TOPIC_TEXT, topicText);

        final String groupName = topic.getGroupName();
        Assert.assertEquals(GROUP_NAME, groupName);

        final String author = topic.getAuthor();
        Assert.assertEquals(AUTHOR, author);
    }

    @After
    public void tearDown() {
        group.openFeedPage();
        group.remove();
        driver.quit();
    }
}
