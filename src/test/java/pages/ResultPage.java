package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Driver;

public class ResultPage {

    private static final Logger logger = LoggerFactory.getLogger(ResultPage.class);
    private final AppiumDriver appiumDriver;

    // ResultPage Locators
    private By articleTitle;
    private By articlePageLastText;

    // Class constants
    private static final String PAGE_LAST_TEXT = "View article in browser";

    public ResultPage() {
        this.appiumDriver = Driver.getAppiumDriver();
        String platform = Driver.getPlatform();
        if (platform.equalsIgnoreCase("android")) {
            articleTitle = By.xpath("//android.widget.TextView[@text='Mountains of Armenia']");
            articlePageLastText = By.xpath("//android.widget.TextView[@text='View article in browser']");
        } else if (platform.equalsIgnoreCase("ios")) {
            // Here should be IOS locators
        }
    }

    public String getArticleTitle() {
        logger.info("Getting article title...");
        return appiumDriver.findElement(articleTitle).getText();
    }

    public void scrollToBottom() {
        logger.info("Scrolling to bottom...");
        appiumDriver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                        "new UiSelector().textContains(\"" + PAGE_LAST_TEXT + "\"))"));
    }

    public boolean isPageLastTextVisible() {
        logger.info("Checking if page last text is visible...");
        return appiumDriver.findElement(articlePageLastText).isDisplayed();
    }
}
