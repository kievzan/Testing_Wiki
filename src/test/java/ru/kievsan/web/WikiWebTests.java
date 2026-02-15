package ru.kievsan.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kievsan.pages.WikiWebPage;
import ru.kievsan.utils.DriverFactory;

public class WikiWebTests {

    private WebDriver driver;
    private WikiWebPage wikiWebPage;
    private static final String BASE_URL = "https://ru.wikipedia.org/";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        wikiWebPage = new WikiWebPage(driver);
    }

    @Test
    public void testMainPageLoadAndElementsDisplay() {
        Assert.assertTrue(wikiWebPage.isMainContentDisplayed(), "Main Wikipedia page content is not displayed.");
    }

    @Test
    public void testSearchFunctionality() {
        String searchQuery = "Россия";
        String expectedArticleTitle = "Россия";

        wikiWebPage.searchFor(searchQuery);

        String heading = wikiWebPage.getFirstHeadingText();

        Assert.assertEquals(heading, expectedArticleTitle, "Search failed. Expected heading: '" + expectedArticleTitle + "', but got: " + heading);
    }

    @Test
    public void testRandomPageNavigation() {
        wikiWebPage.isMainContentDisplayed();
        String originalUrl = driver.getCurrentUrl();

        wikiWebPage.clickRandomPageLink();

        Assert.assertNotEquals(driver.getCurrentUrl(), originalUrl, "Transition to a random page did not occur (URL did not change).");
    }

    @Test
    public void testSearchInputInteractivity() {
        wikiWebPage.isMainContentDisplayed();

        Assert.assertTrue(wikiWebPage.isSearchInputDisplayed(), "Search input element is not displayed or enabled for interaction.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}