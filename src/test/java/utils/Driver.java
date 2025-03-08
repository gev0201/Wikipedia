package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {

    private static final Logger logger = LoggerFactory.getLogger(Driver.class);

    // Thread instance for thread-safe parallel execution
    private static final ThreadLocal<AppiumDriver> threadLocalDriver = new ThreadLocal<>();

    // Thread to store the platform for each thread
    private static final ThreadLocal<String> threadLocalPlatform = new ThreadLocal<>();

    // Platform for current Thread
    public static void setPlatform(String platform) {
        logger.info("Setting platform for thread {}", platform);
        threadLocalPlatform.set(platform.toLowerCase());
    }

    // Get Platform
    public static String getPlatform() {
        String platform = threadLocalPlatform.get();
        if (platform == null) {
            logger.error("Platform has not been set for the current thread.");
            throw new IllegalStateException("Platform has not been set for the current thread.");
        }
        return platform;
    }

    // Init Appium Driver
    public static void initDriver(String udid, String port) throws MalformedURLException {
        if (threadLocalDriver.get() != null) {
            return;
        }

        String platform = threadLocalPlatform.get();
        if (platform == null) {
            logger.error("The platform should be set > use - setPlatform()");
            throw new IllegalStateException("The platform should be set > use - setPlatform()");
        }
        logger.info("Initializing driver for {}, udid: {}, port: {}", platform, udid, port);
        DesiredCapabilities caps = new DesiredCapabilities();
        URL url = new URL("http://127.0.0.1:" + port);

        if (platform.equals("android")) {
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:deviceName", "GevorgGAndroid");
            caps.setCapability("appium:platformVersion", "14");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:udid", udid);
            caps.setCapability("appium:appPackage", "org.wikipedia");
            caps.setCapability("appium:appActivity", "org.wikipedia.main.MainActivity");
            try {
                threadLocalDriver.set(new AndroidDriver(url, caps));
            } catch (SessionNotCreatedException e) {
                logger.error("Failed to create session for Android", e);
                throw new RuntimeException(e);
            }
        } else if (platform.equals("ios")) {
            caps.setCapability("platformName", "iOS");
            // All caps for IOS
            try {
                threadLocalDriver.set(new IOSDriver(url, caps));
            } catch (SessionNotCreatedException e) {
                logger.error("Failed to create session for IOS", e);
                throw new RuntimeException(e);
            }
        } else {
            logger.error("Unsupported platform provided");
            throw new IllegalArgumentException("Unsupported platform provided");
        }

        threadLocalDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
    }

    public static AppiumDriver getAppiumDriver() {
        AppiumDriver appiumDriver = threadLocalDriver.get();
        if (appiumDriver == null) {
            logger.error("AppiumDriver is not initialized. Call initDriver() first.");
            throw new IllegalStateException("AppiumDriver is not initialized. Call initDriver() first.");
        }
        return appiumDriver;
    }

    public static void quitAppiumDriver() {
        AppiumDriver appiumDriver = threadLocalDriver.get();
        if (appiumDriver != null && appiumDriver.getSessionId() != null) {
            appiumDriver.quit();
            threadLocalDriver.remove();
            threadLocalPlatform.remove();
        }
    }
}