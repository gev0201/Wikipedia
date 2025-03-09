package utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AppiumStarter {

    private static final Logger logger = LoggerFactory.getLogger(AppiumStarter.class);

    // Thread to safely manage Appium server for each thread
    private static final ThreadLocal<AppiumDriverLocalService> threadLocalServer = new ThreadLocal<>();

    public static void startAppiumServer(int port) {
        if (threadLocalServer.get() != null) {
            logger.warn("Appium server is running on port: {}", port);
            return;
        }

        logger.info("Starting Appium server on port: {}", port);
        AppiumDriverLocalService appiumServer = new AppiumServiceBuilder().withAppiumJS(new File(ReadConfig.getConfigValue("mainJSurl"))).withIPAddress("127.0.0.1").usingPort(port).withArgument(GeneralServerFlag.LOG_LEVEL, "info").build();

        appiumServer.start();
        threadLocalServer.set(appiumServer);
    }

    public static void stopAppiumServer() {
        AppiumDriverLocalService appiumServer = threadLocalServer.get();
        if (appiumServer != null && appiumServer.isRunning()) {
            logger.info("Stopping Appium server for current thread");
            appiumServer.stop();
            threadLocalServer.remove();
        } else {
            logger.warn("No running Appium server found for thread.");
        }
    }
}
