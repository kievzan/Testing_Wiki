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
    private static final String WIKI_URL = "https://ru.wikipedia.org/";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
        driver.get(WIKI_URL);
        wikiWebPage = new WikiWebPage(driver);
    }

    @Test
    public void testLoadingOfMainPageAndDisplayOfElements() {
        Assert.assertTrue(wikiWebPage.isMainContentDisplayed(),
                "The content of the Wikipedia homepage is not displayed.");
    }

    @Test
    public void testSearchFunctional() {
        String query = "Москва";
        String expectedTitle = "Москва";

        wikiWebPage.searchFor(query);

        String header = wikiWebPage.getFirstHeadingText();

        Assert.assertEquals(header, expectedTitle,
                "Search failed. Expected header: '" + expectedTitle +
                        "', real got: " + header);
    }

    @Test
    public void testRandomPageNavigation() {
        wikiWebPage.isMainContentDisplayed();
        String originalUrl = driver.getCurrentUrl();

        wikiWebPage.clickRandomPageLink();

        Assert.assertNotEquals(driver.getCurrentUrl(), originalUrl,
                "There was no transition to a random page (the URL has not changed).");
    }

    @Test
    public void testInteractivityOfDataEntryDuringSearch() {
        wikiWebPage.isMainContentDisplayed();
        Assert.assertTrue(wikiWebPage.isSearchInputDisplayed(),
                "Search input element is not displayed and is not enabled for interaction.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}