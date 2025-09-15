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
                LogUtils.logError("ConfigManager", "config.properties not found in resources!", 
                        new RuntimeException("File not found"));
                throw new RuntimeException("config.properties not found in resources!");
            }
            properties.load(input);
            LogUtils.logSuccess("ConfigManager", "Successfully loaded config.properties");
        } catch (IOException e) {
            LogUtils.logError("ConfigManager", "Failed to load config.properties", e);
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        LogUtils.logAction("ConfigManager", "Getting property: " + key);
        String value = properties.getProperty(key);
        if (value != null) {
            LogUtils.logSuccess("ConfigManager", String.format("Got property %s = %s", key, value));
        } else {
            LogUtils.logWarning("ConfigManager", String.format("Property not found: %s", key));
        }
        return value;
    }

    private static String getOrDefault(String key, String defaultValue) {
        String value = get(key);
        if (value == null) {
            LogUtils.logAction("ConfigManager", 
                    String.format("Using default value for %s: %s", key, defaultValue));
        }
        return value != null ? value : defaultValue;
    }

    public static String getBrowser() {
        LogUtils.logAction("ConfigManager", "Getting browser configuration");
        String browser = getOrDefault("browser", "chrome");
        LogUtils.logSuccess("ConfigManager", "Browser: " + browser);
        return browser;
    }

    public static String getBrowserSize() {
        LogUtils.logAction("ConfigManager", "Getting browser size configuration");
        String size = getOrDefault("browserSize", "1920x1080");
        LogUtils.logSuccess("ConfigManager", "Browser size: " + size);
        return size;
    }

    public static int getPageLoadTimeout() {
        LogUtils.logAction("ConfigManager", "Getting page load timeout");
        try {
            int timeout = Integer.parseInt(getOrDefault("pageLoadTimeout", "20000"));
            LogUtils.logSuccess("ConfigManager", "Page load timeout: " + timeout + "ms");
            return timeout;
        } catch (NumberFormatException e) {
            LogUtils.logError("ConfigManager", "Invalid page load timeout value", e);
            LogUtils.logWarning("ConfigManager", "Using default value: 20000ms");
            return 20000;
        }
    }

    public static int getElementTimeout() {
        LogUtils.logAction("ConfigManager", "Getting element timeout");
        try {
            int timeout = Integer.parseInt(getOrDefault("elementTimeout", "10000"));
            LogUtils.logSuccess("ConfigManager", "Element timeout: " + timeout + "ms");
            return timeout;
        } catch (NumberFormatException e) {
            LogUtils.logError("ConfigManager", "Invalid element timeout value", e);
            LogUtils.logWarning("ConfigManager", "Using default value: 10000ms");
            return 10000;
        }
    }

    public static String getBaseUrl() {
        LogUtils.logAction("ConfigManager", "Getting base URL");
        String baseUrl = getOrDefault("base.url", "http://localhost");
        LogUtils.logSuccess("ConfigManager", "Base URL: " + baseUrl);
        return baseUrl;
    }
    
    public static String getAgodaUrl() {
        LogUtils.logAction("ConfigManager", "Getting Agoda URL");
        String agodaUrl = getOrDefault("agoda.url", "https://www.agoda.com/");
        LogUtils.logSuccess("ConfigManager", "Agoda URL: " + agodaUrl);
        return agodaUrl;
    }

    public static int getTimeout() {
        LogUtils.logAction("ConfigManager", "Getting general timeout");
        try {
            int timeout = Integer.parseInt(getOrDefault("timeout", "5000"));
            LogUtils.logSuccess("ConfigManager", "General timeout: " + timeout + "ms");
            return timeout;
        } catch (NumberFormatException e) {
            LogUtils.logError("ConfigManager", "Invalid timeout value", e);
            LogUtils.logWarning("ConfigManager", "Using default value: 5000ms");
            return 5000;
        }
    }

    public static boolean isHeadless() {
        LogUtils.logAction("ConfigManager", "Checking if headless mode is enabled");
        boolean headless = Boolean.parseBoolean(getOrDefault("headless", "false"));
        LogUtils.logSuccess("ConfigManager", "Headless mode: " + (headless ? "enabled" : "disabled"));
        return headless;
    }

    public static String getRemoteGridUrl() {
        LogUtils.logAction("ConfigManager", "Getting remote grid URL");
        String url = getOrDefault("remoteGridUrl", "");
        if (!url.isEmpty()) {
            LogUtils.logSuccess("ConfigManager", "Remote grid URL: " + url);
        } else {
            LogUtils.logSuccess("ConfigManager", "Remote grid URL not configured");
        }
        return url;
    }
}
