package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.internal.collections.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Driver;

import java.util.List;

public class SearchPage {

    private static final Logger logger = LoggerFactory.getLogger(SearchPage.class);
    private final AppiumDriver appiumDriver;

    // SearchPage Locators
    private By searchInput;
    private By searchResults;

    public SearchPage() {
        this.appiumDriver = Driver.getAppiumDriver();
        String platform = Driver.getPlatform();
        if (platform.equalsIgnoreCase("android")) {
            searchInput = By.id("org.wikipedia:id/search_src_text");
            searchResults = By.className("android.widget.TextView");
        } else if (platform.equalsIgnoreCase("ios")) {
            // Here should be IOS locators
        }
    }

    public SearchPage enterSearchKeyword(String query) {
        logger.info("Entering search keyword: {}", query);
        appiumDriver.findElement(searchInput).sendKeys(query);
        return this;
    }

    public Pair<Boolean, WebElement> isResultDisplayed(String keyword) {
        logger.info("Checking if search result for keyword: {} is displayed", keyword);
        List<WebElement> allResults = appiumDriver.findElements(searchResults);
        for (WebElement result : allResults) {
            if (result.getText().equalsIgnoreCase(keyword)) {
                return Pair.of(true, result);
            }
        }
        return Pair.of(false, null);
    }

    public ResultPage selectResult(WebElement searchResult) {
        logger.info("Selecting search result: {}", searchResult.getText());
        searchResult.click();
        return new ResultPage();
    }

    public void waitForPageStability(int timeout, int checkInterval) throws InterruptedException {
        logger.info("Waiting for page stability for {} seconds", timeout);
        String oldPageSource = "";
        String newPageSource = appiumDriver.getPageSource();
        long startTime = System.currentTimeMillis();

        while (!newPageSource.equals(oldPageSource) && (System.currentTimeMillis() - startTime) < (timeout * 1000)) {
            oldPageSource = newPageSource;
            Thread.sleep(checkInterval);
            newPageSource = appiumDriver.getPageSource();
        }
    }
}
