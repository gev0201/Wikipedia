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

    @BeforeClass
    @Parameters({"udid", "port"})
    public void setUp(String udid, String port) throws MalformedURLException {
        Driver.initDriver(udid, port);
    }

    @AfterClass
    public void tearDown() {
        Driver.quitAppiumDriver();
    }

    @Test
    public void testHomeScreenDisplayed() {
        WikiHomePage wikiHomePage = new WikiHomePage();
        wikiHomePage.skipWikiStartUpPage();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(wikiHomePage.isHomeTitleDisplayed(), "Wiki home title not found or page not loaded");
        softAssert.assertTrue(wikiHomePage.isExploreTabDisplayed(), "Explore tab not found or page not loaded");
        softAssert.assertTrue(wikiHomePage.isSearchElementDisplayed(), "Search element not found or page not loaded");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "testHomeScreenDisplayed")
    public void testSearchArticle() throws InterruptedException {
        WikiHomePage wikiHomePage = new WikiHomePage();
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
