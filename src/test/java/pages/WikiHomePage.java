package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Driver;

public class WikiHomePage {

    private static final Logger log = LoggerFactory.getLogger(WikiHomePage.class);
    private final AppiumDriver appiumDriver;

    // WikiHomePage locators
    private By title;
    private By explore;
    private By searchInput;
    private By skipStartPage;

    public WikiHomePage() {
        this.appiumDriver = Driver.getAppiumDriver();
        String platform = Driver.getPlatform();
        if (platform.equalsIgnoreCase("android")) {
            title = By.id("org.wikipedia:id/main_toolbar_wordmark");
            explore = By.id("org.wikipedia:id/nav_tab_explore");
            searchInput = By.id("org.wikipedia:id/search_container");
            skipStartPage = By.id("org.wikipedia:id/fragment_onboarding_skip_button");
        } else if (platform.equalsIgnoreCase("ios")) {
            // Here should be IOS locators
        }
    }

    public void skipWikiStartUpPage() {
        try {
            appiumDriver.findElement(skipStartPage).click();
        } catch (NoSuchElementException e) {
            log.info("No skip button found > Continue...");
        }
    }

    public boolean isHomeTitleDisplayed() {
        return appiumDriver.findElement(title).isDisplayed();
    }

    public boolean isExploreTabDisplayed() {
        return appiumDriver.findElement(explore).isDisplayed();
    }

    public boolean isSearchElementDisplayed() {
        return appiumDriver.findElement(searchInput).isDisplayed();
    }

    public SearchPage clickOnSearch() {
        appiumDriver.findElement(searchInput).click();
        return new SearchPage();
    }
}
