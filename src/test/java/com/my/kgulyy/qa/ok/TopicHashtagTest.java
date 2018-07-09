package com.my.kgulyy.qa.ok;

import com.my.kgulyy.qa.ok.components.MainPage;
import com.my.kgulyy.qa.ok.pageobjects.Auth;
import com.my.kgulyy.qa.ok.pageobjects.Group;
import com.my.kgulyy.qa.ok.pageobjects.Topic;
import com.my.kgulyy.qa.utils.DriverUtils;
import com.my.kgulyy.qa.utils.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TopicHashtagTest {
    private static final String TAG_WITH_SPEC_SYMBOL_ERROR = "В ключевых словах содержатся запрещенные символы";
    private static final String TAGS_ARE_ENOUGH_WARNING = "Ключевых слов достаточно, спасибо";
    private static final String MIN_LENGTH_OF_TAG_WARNING = "Минимальная длина ключевого слова 2 символа";

    private static final WebDriver DRIVER = DriverUtils.getWebDriver();
    private static final Group GROUP = new Group(DRIVER);
    private static final Topic TOPIC = new Topic(DRIVER);

    @BeforeClass
    public static void setUp() {
        final Auth auth = new Auth(DRIVER);
        auth.signIn();

        final MainPage mainPage = new MainPage(DRIVER);
        mainPage.openGroupsPage();

        GROUP.create();
        TOPIC.create();
    }

    @AfterClass
    public static void tearDown() {
        GROUP.openFeedPage();
        GROUP.remove();
        DRIVER.quit();
    }

    @After
    public void cleanUp() {
        DRIVER.navigate().refresh();
    }

    @Test
    public void addRemoveOneTag() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tag = "hashtag";
        TOPIC.addTag(tag);

        checkTempTag(tag);
        DRIVER.navigate().refresh();
        checkHashtag(tag);

        TOPIC.removeTag(tag);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addEmptyTag() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final String emptyTag = "";
        TOPIC.addTag(emptyTag);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTooShortTag() {
        final String tooShortTag = "1";
        TOPIC.setTag(tooShortTag);

        final String errorMessage = TOPIC.getTagErrorMessage();
        assertEquals(MIN_LENGTH_OF_TAG_WARNING, errorMessage);
    }

    @Test
    public void addRemoveShortTag() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final String shortTag = "ab";
        TOPIC.addTag(shortTag);

        checkTempTag(shortTag);
        DRIVER.navigate().refresh();
        checkHashtag(shortTag);

        TOPIC.removeTag(shortTag);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addRemoveMaxLengthTag() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final int tagLength = 25;
        final String longTag = StringUtils.createStringFromSymbol(tagLength, 'x');
        TOPIC.addTag(longTag);

        checkTempTag(longTag);
        DRIVER.navigate().refresh();
        checkHashtag(longTag);

        TOPIC.removeTag(longTag);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTooLongTag() {
        final boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final int tagLength = 26;
        final String tooLongTag = StringUtils.createStringFromSymbol(tagLength, 'x');
        TOPIC.addTag(tooLongTag);

        final int remainingTagLength = TOPIC.getRemainingTagLength();
        assertEquals(-1, remainingTagLength);
    }

    @Test
    public void addRemoveTagWithOneSpace() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tagWithSpace = "hash tag";
        TOPIC.addTag(tagWithSpace);

        checkTempTag(tagWithSpace);
        DRIVER.navigate().refresh();
        checkHashtag("HashTag");

        TOPIC.removeTag(tagWithSpace);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addRemoveTagWithMultipleSpace() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tagWithSpaces = "  my   hash  tag ";
        TOPIC.addTag(tagWithSpaces);

        final String trimmedTagWithSpaces = tagWithSpaces.trim();
        checkTempTag(trimmedTagWithSpaces);
        DRIVER.navigate().refresh();
        checkHashtag("MyHashTag");

        TOPIC.removeTag(trimmedTagWithSpaces);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTagWithHashSign() {
        final String wrongTag = "#tag";
        TOPIC.setTag(wrongTag);

        final String errorMessage = TOPIC.getTagErrorMessage();
        assertEquals(TAG_WITH_SPEC_SYMBOL_ERROR, errorMessage);
    }

    @Test
    public void addTagWithPlusSign() {
        final String wrongTag = "tag+";
        TOPIC.setTag(wrongTag);

        final String errorMessage = TOPIC.getTagErrorMessage();
        assertEquals(TAG_WITH_SPEC_SYMBOL_ERROR, errorMessage);
    }

    @Test
    public void addTagWithSpecSymbols() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tagWithSpecSymbols = ".@!/my-hash_tag():'&?";
        TOPIC.addTag(tagWithSpecSymbols);

        checkTempTag(tagWithSpecSymbols);
        DRIVER.navigate().refresh();
        checkHashtag("MyHash_tag");

        TOPIC.removeTag(tagWithSpecSymbols);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addRemoveTagWithDigits() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tagWithDigits = "1234567890";
        TOPIC.addTag(tagWithDigits);

        checkTempTag(tagWithDigits);
        DRIVER.navigate().refresh();
        checkHashtag(tagWithDigits);

        TOPIC.removeTag(tagWithDigits);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addRemoveTagWithDifferentCase() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final String tagWithDifferentCase = "DiFfErEnT cAsE";
        TOPIC.addTag(tagWithDifferentCase);

        final String tagWithLowerCase = tagWithDifferentCase.toLowerCase();
        checkTempTag(tagWithLowerCase);
        DRIVER.navigate().refresh();
        checkHashtag("DifferentCase");

        TOPIC.removeTag(tagWithLowerCase);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addRemoveTwoDifferentTags() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final List<String> tags = Arrays.asList("first", "second");
        TOPIC.addAllTags(tags);

        checkAllTempTags(tags);
        DRIVER.navigate().refresh();
        checkAllHashtags(tags);

        TOPIC.removeAllTags(tags);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTwoSameTags() {
        final List<String> tags = Arrays.asList("same", "same");
        TOPIC.addAllTags(tags);

        final String addedTag = tags.get(0);
        checkTempTag(addedTag);
        DRIVER.navigate().refresh();
        checkHashtag(addedTag);

        TOPIC.removeTag(addedTag);
    }

    @Test
    public void addRemoveMaximTags() {
        boolean noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);

        final List<String> tags = Arrays.asList("11", "22", "33", "44", "55", "66", "77");
        TOPIC.addAllTags(tags);

        checkAllTempTags(tags);
        DRIVER.navigate().refresh();
        checkAllHashtags(tags);

        TOPIC.removeAllTags(tags);

        DRIVER.navigate().refresh();
        noOneHashtags = TOPIC.noOneHashtags();
        assertTrue(noOneHashtags);
    }

    @Test
    public void addTooMuchTags() {
        final List<String> tags = Stream
                .of("11", "22", "33", "44", "55", "66", "77", "88")
                .collect(Collectors.toCollection(ArrayList::new));

        TOPIC.setAllTags(tags);

        final String errorMessage = TOPIC.getTagErrorMessage();
        assertEquals(TAGS_ARE_ENOUGH_WARNING, errorMessage);

        DRIVER.navigate().refresh();
        tags.remove(tags.size() - 1);
        TOPIC.removeAllTags(tags);
    }

    @Test
    public void addEditOneTag() {
        final String tag = "tag";
        TOPIC.addTag(tag);
        checkTempTag(tag);

        final String newTag = "new_tag";
        TOPIC.editTag(tag, newTag);

        checkTempTag(newTag);
        DRIVER.navigate().refresh();
        checkHashtag(newTag);

        TOPIC.removeTag(newTag);
    }

    @Test
    public void addTwoTagsEditFirstEditSecond() {
        final String firstTag = "first";
        final String secondTag = "second";
        final List<String> tags = Arrays.asList(firstTag, secondTag);
        TOPIC.addAllTags(tags);

        final String newFirstTag = "new_first";
        TOPIC.editTag(firstTag, newFirstTag);

        final List<String> tagsWithNewFirst = Arrays.asList(newFirstTag, secondTag);
        checkAllTempTags(tagsWithNewFirst);
        DRIVER.navigate().refresh();
        checkAllHashtags(tagsWithNewFirst);

        final String newSecondTag = "new_second";
        TOPIC.editHashtag(secondTag, newSecondTag);

        final List<String> newTags = Arrays.asList(newFirstTag, newSecondTag);
        checkAllTempTags(newTags);
        DRIVER.navigate().refresh();
        checkAllHashtags(newTags);

        TOPIC.removeAllTags(newTags);
    }

    @Test
    public void addSeveralTagsEditAll() {
        final List<String> tags = Arrays.asList("11", "22", "33", "44");
        TOPIC.addAllTags(tags);

        final List<String> newTags = Arrays.asList("00", "100");
        TOPIC.editAllTags(tags, newTags);

        checkAllTempTags(newTags);
        DRIVER.navigate().refresh();
        checkAllHashtags(newTags);

        TOPIC.removeAllTags(newTags);
    }

    private void checkTempTag(String tag) {
        final int numberOfTags = TOPIC.getNumberOfTempTags();
        assertEquals(1, numberOfTags);
        final boolean isExistTag = TOPIC.isExistTempTag(tag);
        assertTrue(isExistTag);
    }

    private void checkAllTempTags(List<String> tags) {
        final int numberOfTags = TOPIC.getNumberOfTempTags();
        assertEquals(tags.size(), numberOfTags);
        for (String tag : tags) {
            final boolean isExistTag = TOPIC.isExistTempTag(tag);
            assertTrue(isExistTag);
        }
    }

    private void checkHashtag(String hashtag) {
        final int numberOfHashtags = TOPIC.getNumberOfHashtags();
        assertEquals(1, numberOfHashtags);
        final boolean isExistHashtag = TOPIC.isExistHashtag(hashtag);
        assertTrue(isExistHashtag);
    }

    private void checkAllHashtags(List<String> hashtags) {
        final int numberOfHashtags = TOPIC.getNumberOfHashtags();
        assertEquals(hashtags.size(), numberOfHashtags);
        for (String hashtag : hashtags) {
            final boolean isExistHashtag = TOPIC.isExistHashtag(hashtag);
            assertTrue(isExistHashtag);
        }
    }
}
