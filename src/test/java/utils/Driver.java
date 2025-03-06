package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver {

    private static AppiumDriver appiumDriver;
    // ThreadLocal instance for Thread safe parallel execution.
    private static final ThreadLocal<AppiumDriver> threadLocalDriver = new ThreadLocal<>();

    public static void initDriver(String udid, String port) throws MalformedURLException {
        if (threadLocalDriver.get() == null) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:deviceName", "GevorgGAndroid");
            caps.setCapability("appium:platformVersion", "14");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:udid", udid);
            caps.setCapability("appium:appPackage", "org.wikipedia");
            caps.setCapability("appium:appActivity", "org.wikipedia.main.MainActivity");

            URL url = new URL("http://127.0.0.1:" + port);
            appiumDriver = new AppiumDriver(url, caps);
            appiumDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            threadLocalDriver.set(appiumDriver);
        }
    }

    public static AppiumDriver getAppiumDriver() {
        return threadLocalDriver.get();
    }

    public static void quitAppiumDriver() {
        if (threadLocalDriver.get() != null) {
            threadLocalDriver.get().quit();
            threadLocalDriver.remove();
        }
    }
}