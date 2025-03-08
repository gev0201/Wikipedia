package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.testng.internal.collections.Pair;
import pages.ResultPage;
import pages.WikiHomePage;
import pages.SearchPage;
import utils.Driver;

import java.net.MalformedURLException;

public class WikipediaTests {

    private WikiHomePage wikiHomePage;

    @BeforeClass
    @Parameters({"udid", "port", "platform"})
    public void setUp(String udid, String port, String platform) throws MalformedURLException {
        Driver.setPlatform(platform);
        Driver.initDriver(udid, port);
        wikiHomePage = new WikiHomePage();
    }

    @AfterClass
    public void tearDown() {
        Driver.quitAppiumDriver();
    }

    @Test
    public void testHomeScreenDisplayed() {
        wikiHomePage.skipWikiStartUpPage();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(wikiHomePage.isHomeTitleDisplayed(), "Wiki title not found or home page not loaded");
        softAssert.assertTrue(wikiHomePage.isExploreTabDisplayed(), "Explore tab not found");
        softAssert.assertTrue(wikiHomePage.isSearchElementDisplayed(), "Search element not found");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "testHomeScreenDisplayed")
    public void testSearchArticle() throws InterruptedException {
        SearchPage searchPage = wikiHomePage.clickOnSearch()
                .enterSearchKeyword("Mountains of Armenia");

        searchPage.waitForPageStability(3, 20);
        Pair<Boolean, WebElement> result = searchPage.isResultDisplayed("Mountains of Armenia");
        Assert.assertTrue(result.first(), "Search result for 'Mountains of Armenia' not found!");

        ResultPage resultPage = searchPage.selectResult(result.second());
        Assert.assertEquals(resultPage.getArticleTitle(), "Mountains of Armenia", "Article title does not match search query!");
    }

    @Test(dependsOnMethods = "testSearchArticle")
    public void testScrollPage() {
        ResultPage articlePage = new ResultPage();
        articlePage.scrollToBottom();
        Assert.assertTrue(articlePage.isPageLastTextVisible(), "References section not found after scrolling!");
    }
}
