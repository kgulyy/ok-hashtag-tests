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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TopicHashtagTest {
    private static final String TAG_WITH_SPEC_SYMBOL_ERROR = "В ключевых словах содержатся запрещенные символы";
    private static final String TAGS_ARE_ENOUGH_WARNING = "Ключевых слов достаточно, спасибо";
    private static final String MIN_LENGTH_OF_TAG_WARNING = "Минимальная длина ключевого слова 2 символа";

    private final WebDriver driver;
    private final PublicPage publicPage;
    private final Topic topic;

    public TopicHashtagTest() {
        driver = DriverUtils.getRemoteWebDriver();
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

    @Test
    public void addRemoveTwoDifferentTags() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final List<String> tags = Arrays.asList("first", "second");
        topic.addAllTags(tags);

        checkAllTempTags(tags);
        driver.navigate().refresh();
        checkAllHashtags(tags);

        topic.removeAllTags(tags);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTwoSameTags() {
        final List<String> tags = Arrays.asList("same", "same");
        topic.addAllTags(tags);

        final String addedTag = tags.get(0);
        checkTempTag(addedTag);
        driver.navigate().refresh();
        checkHashtag(addedTag);
    }

    @Test
    public void addRemoveMaximTags() {
        boolean noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);

        final List<String> tags = Arrays.asList("11", "22", "33", "44", "55", "66", "77");
        topic.addAllTags(tags);

        checkAllTempTags(tags);
        driver.navigate().refresh();
        checkAllHashtags(tags);

        topic.removeAllTags(tags);

        driver.navigate().refresh();
        noOneHashtags = topic.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTooMuchTags() {
        final List<String> tags = Arrays.asList("11", "22", "33", "44", "55", "66", "77", "88");
        topic.addAllTags(tags);

        final String errorMessage = topic.getTagErrorMessage();
        assertEquals(TAGS_ARE_ENOUGH_WARNING, errorMessage);
    }

    @Test
    public void addEditOneTag() {
        final String tag = "tag";
        topic.addTag(tag);
        checkTempTag(tag);

        final String newTag = "new_tag";
        topic.editTag(tag, newTag);

        checkTempTag(newTag);
        driver.navigate().refresh();
        checkHashtag(newTag);
    }

    @Test
    public void addTwoTagsEditFirstEditSecond() {
        final String firstTag = "first";
        final String secondTag = "second";
        final List<String> tags = Arrays.asList(firstTag, secondTag);
        topic.addAllTags(tags);

        final String newFirstTag = "new_first";
        topic.editTag(firstTag, newFirstTag);

        final List<String> tagsWithNewFirst = Arrays.asList(newFirstTag, secondTag);
        checkAllTempTags(tagsWithNewFirst);
        driver.navigate().refresh();
        checkAllHashtags(tagsWithNewFirst);

        final String newSecondTag = "new_second";
        topic.editHashtag(secondTag, newSecondTag);

        final List<String> newTags = Arrays.asList(newFirstTag, newSecondTag);
        checkAllTempTags(newTags);
        driver.navigate().refresh();
        checkAllHashtags(newTags);
    }

    @Test
    public void addSeveralTagsEditAll() {
        final List<String> tags = Arrays.asList("11", "22", "33", "44");
        topic.addAllTags(tags);

        final List<String> newTags = Arrays.asList("00", "100");
        topic.editAllTags(tags, newTags);

        checkAllTempTags(newTags);
        driver.navigate().refresh();
        checkAllHashtags(newTags);
    }

    private void checkTempTag(String tag) {
        final int numberOfTags = topic.getNumberOfTempTags();
        assertEquals(1, numberOfTags);
        final boolean isExistTag = topic.isExistTempTag(tag);
        assertTrue(isExistTag);
    }

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

    private void checkAllHashtags(List<String> hashtags) {
        final int numberOfHashtags = topic.getNumberOfHashtags();
        assertEquals(hashtags.size(), numberOfHashtags);
        for (String hashtag : hashtags) {
            final boolean isExistHashtag = topic.isExistHashtag(hashtag);
            assertTrue(isExistHashtag);
        }
    }
}
