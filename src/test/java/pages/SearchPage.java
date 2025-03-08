package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.internal.collections.Pair;
import utils.Driver;

import java.util.List;

public class SearchPage {

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
        appiumDriver.findElement(searchInput).sendKeys(query);
        return this;
    }

    public Pair<Boolean, WebElement> isResultDisplayed(String keyword) {
        List<WebElement> allResults = appiumDriver.findElements(searchResults);
        for (WebElement result : allResults) {
            if (result.getText().equalsIgnoreCase(keyword)) {
                return Pair.of(true, result);
            }
        }
        return Pair.of(false, null);
    }

    public ResultPage selectResult(WebElement searchResult) {
        searchResult.click();
        return new ResultPage();
    }

    public void waitForPageStability(int timeout, int checkInterval) throws InterruptedException {
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
