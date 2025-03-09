package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadConfig {

    private static final Logger logger = LoggerFactory.getLogger(ReadConfig.class);

    public static String getConfigValue(String key) {
        logger.info("Reading configuration value for key: {}", key);
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(fis);
            return properties.getProperty(key);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage());
        }
    }
}
