package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.Driver;

public class ResultPage {

    private final AppiumDriver appiumDriver;

    // ResultPage Locators
    private final By articleTitle = By.xpath("//android.widget.TextView[@text='Mountains of Armenia']");
    private final By articlePageLastText = By.xpath("//android.widget.TextView[@text='View article in browser']");

    // Class constants
    private static final String PAGE_LAST_TEXT = "View article in browser";

    public ResultPage() {
        this.appiumDriver = Driver.getAppiumDriver();
    }

    public String getArticleTitle() {
        return appiumDriver.findElement(articleTitle).getText();
    }

    public ResultPage scrollToBottom() {
        appiumDriver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                        "new UiSelector().textContains(\"" + PAGE_LAST_TEXT + "\"))"));
        return this;
    }

    public boolean isPageLastTextVisible() {
        return appiumDriver.findElement(articlePageLastText).isDisplayed();
    }
}