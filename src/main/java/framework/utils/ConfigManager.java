package framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager loads config.properties and provides type-safe getters with
 * defaults.
 */
public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in resources!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    private static String getOrDefault(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    public static String getBrowser() {
        return getOrDefault("browser", "chrome");
    }

    public static String getBrowserSize() {
        return getOrDefault("browserSize", "1920x1080");
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getOrDefault("pageLoadTimeout", "20000"));
    }

    public static int getElementTimeout() {
        return Integer.parseInt(getOrDefault("elementTimeout", "10000"));
    }

    public static String getBaseUrl() {
        return getOrDefault("base.url", "http://localhost");
    }

    public static int getTimeout() {
        return Integer.parseInt(getOrDefault("timeout", "5000"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getOrDefault("headless", "false"));
    }

    public static String getRemoteGridUrl() {
        return getOrDefault("remoteGridUrl", "");
    }
}
