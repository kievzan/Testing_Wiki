package ru.kievsan.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kievsan.pages.WikiMobApp;
import ru.kievsan.utils.DriverFactory;

public class WikiMobTests {

    private AndroidDriver driver;
    private WikiMobApp appPage;

    @BeforeMethod
    public void setup() throws Exception {
        driver = DriverFactory.createAndroidDriver();
        appPage = new WikiMobApp(driver);
        Thread.sleep(3000);
    }

    @Test(priority = 1)
    public void testMainScreenSearchIsDisplayed() {
        boolean isDisplayed = appPage.isSearchContainerDisplayed();
        System.out.println("The search field is displayed: " + isDisplayed);
        Assert.assertTrue(isDisplayed);
    }

    @Test(priority = 2)
    public void testSearchAndOpenArticle() throws InterruptedException {
        String framework = "Pytest";
        appPage.searchForArticle(framework);
        Thread.sleep(4000);

        String title = appPage.getArticleTitle();
        System.out.println("Article title: '" + title + "'");

        Assert.assertTrue(title != null && !title.isEmpty());
        Assert.assertTrue(title.toLowerCase().contains(framework.toLowerCase()),
                "The header should contain '" + framework + "'. Real: " + title);
    }

    @Test(priority = 3)
    public void testSearchAndNavigateBack() throws InterruptedException {
        appPage.searchForArticle("Selenium");
        Thread.sleep(4000);

        String title = appPage.getArticleTitle();
        System.out.println("The following article is open: " + title);

        appPage.navigateBack();
        Thread.sleep(2000);

        boolean isDisplayed = appPage.isSearchContainerDisplayed();
        Assert.assertTrue(isDisplayed,
                "After returning, the search field should be visible");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}