package com.my.kgulyy.qa.ok;

import com.my.kgulyy.qa.ok.components.MainPage;
import com.my.kgulyy.qa.ok.pageobjects.Auth;
import com.my.kgulyy.qa.ok.pageobjects.PublicPage;
import com.my.kgulyy.qa.ok.pageobjects.Topic;
import com.my.kgulyy.qa.utils.DriverUtils;
import com.my.kgulyy.qa.utils.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.Assert.*;

public class TopicHashtagTest {
    private static final String TAG_WITH_SPEC_SYMBOL_ERROR = "В ключевых словах содержатся запрещенные символы";
    @SuppressWarnings("unused")
    private static final String TAGS_ARE_ENOUGH_WARNING = "Ключевых слов достаточно, спасибо";
    private static final String MIN_LENGTH_OF_TAG_WARNING = "Минимальная длина ключевого слова 2 символа";

    private final WebDriver driver;
    private final PublicPage publicPage;
    private final Topic topic;

    public TopicHashtagTest() {
        driver = DriverUtils.getWebDriver();
        publicPage = new PublicPage(driver);
        topic = new Topic(driver);
    }

    @Before
    public void setUp() {
        final Auth auth = new Auth(driver);
        auth.signIn();

        final MainPage mainPage = new MainPage(driver);
        mainPage.openGroupsPage();

        publicPage.create();
        publicPage.openForumPage();

        topic.create();
    }

    @After
    public void tearDown() {
        publicPage.openFeedPage();
        publicPage.remove();
        driver.quit();
    }

    @Test
    public void addRemoveOneTag() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tag = "hashtag";
        topic.addTag(tag);

        checkTempTag(tag);
        driver.navigate().refresh();
        checkHashtag(tag);

        topic.removeTag(tag);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addEmptyTag() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final String emptyTag = "";
        topic.addTag(emptyTag);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTooShortTag() {
        final String tooShortTag = "1";
        topic.addTag(tooShortTag);

        final String errorMessage = topic.getTagErrorMessage();
        assertEquals(MIN_LENGTH_OF_TAG_WARNING, errorMessage);
    }

    @Test
    public void addRemoveShortTag() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final String shortTag = "ab";
        topic.addTag(shortTag);

        checkTempTag(shortTag);
        driver.navigate().refresh();
        checkHashtag(shortTag);

        topic.removeTag(shortTag);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addRemoveMaxLengthTag() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final int tagLength = 25;
        final String longTag = StringUtils.createStringFromSymbol(tagLength, 'x');
        topic.addTag(longTag);

        checkTempTag(longTag);
        driver.navigate().refresh();
        checkHashtag(longTag);

        topic.removeTag(longTag);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTooLongTag() {
        final boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final int tagLength = 26;
        final String tooLongTag = StringUtils.createStringFromSymbol(tagLength, 'x');
        topic.addTag(tooLongTag);

        final int remainingTagLength = topic.getRemainingTagLength();
        assertEquals(-1, remainingTagLength);
    }

    @Test
    public void addRemoveTagWithOneSpace() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tagWithSpace = "hash tag";
        topic.addTag(tagWithSpace);

        checkTempTag(tagWithSpace);
        driver.navigate().refresh();
        checkHashtag("HashTag");

        topic.removeTag(tagWithSpace);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addRemoveTagWithMultipleSpace() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tagWithSpaces = "  my   hash  tag ";
        topic.addTag(tagWithSpaces);

        final String trimmedTagWithSpaces = tagWithSpaces.trim();
        checkTempTag(trimmedTagWithSpaces);
        driver.navigate().refresh();
        checkHashtag("MyHashTag");

        topic.removeTag(trimmedTagWithSpaces);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTagWithHashSign() {
        final String wrongTag = "#tag";
        topic.addTag(wrongTag);

        final String errorMessage = topic.getTagErrorMessage();
        assertEquals(TAG_WITH_SPEC_SYMBOL_ERROR, errorMessage);
    }

    @Test
    public void addTagWithPlusSign() {
        final String wrongTag = "tag+";
        topic.addTag(wrongTag);

        final String errorMessage = topic.getTagErrorMessage();
        assertEquals(TAG_WITH_SPEC_SYMBOL_ERROR, errorMessage);
    }

    @Test
    public void addTagWithSpecSymbols() {
        final String tagWithSpecSymbols = ".@!/my-hash_tag()\":\'&?";
        topic.addTag(tagWithSpecSymbols);

        driver.navigate().refresh();
        checkHashtag("MyHash_tag");
    }

    @Test
    public void addRemoveTagWithDigits() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tagWithDigits = "1234567890";
        topic.addTag(tagWithDigits);

        checkTempTag(tagWithDigits);
        driver.navigate().refresh();
        checkHashtag(tagWithDigits);

        topic.removeTag(tagWithDigits);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addRemoveTagWithDifferentCase() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tagWithDifferentCase = "DiFfErEnT cAsE";
        topic.addTag(tagWithDifferentCase);

        final String tagWithLowerCase = tagWithDifferentCase.toLowerCase();
        checkTempTag(tagWithLowerCase);
        driver.navigate().refresh();
        checkHashtag("DifferentCase");

        topic.removeTag(tagWithLowerCase);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    private void checkTempTag(String tag) {
        final int numberOfTags = topic.getNumberOfTempTags();
        assertEquals(1, numberOfTags);
        final boolean isExistTag = topic.isExistTempTag(tag);
        assertTrue(isExistTag);
    }

    @SuppressWarnings("unused")
    private void checkAllTempTags(List<String> tags) {
        final int numberOfTags = topic.getNumberOfTempTags();
        assertEquals(tags.size(), numberOfTags);
        for (String tag : tags) {
            final boolean isExistTag = topic.isExistTempTag(tag);
            assertTrue(isExistTag);
        }
    }

    private void checkHashtag(String hashtag) {
        final int numberOfHashtags = topic.getNumberOfHashtags();
        assertEquals(1, numberOfHashtags);
        final boolean isExistHashtag = topic.isExistHashtag(hashtag);
        assertTrue(isExistHashtag);
    }

    @SuppressWarnings("unused")
    private void checkAllHashtags(List<String> hashtags) {
        final int numberOfHashtags = topic.getNumberOfHashtags();
        assertEquals(hashtags.size(), numberOfHashtags);
        for (String hashtag : hashtags) {
            final boolean isExistHashtag = topic.isExistHashtag(hashtag);
            assertTrue(isExistHashtag);
        }
    }
}
