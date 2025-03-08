package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.Driver;

public class ResultPage {

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
        return appiumDriver.findElement(articleTitle).getText();
    }

    public void scrollToBottom() {
        appiumDriver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                        "new UiSelector().textContains(\"" + PAGE_LAST_TEXT + "\"))"));
    }

    public boolean isPageLastTextVisible() {
        return appiumDriver.findElement(articlePageLastText).isDisplayed();
    }
}
