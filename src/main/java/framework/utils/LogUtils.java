package framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for logging actions in the framework
 */
public class LogUtils {
    private static final Logger LOG = LoggerFactory.getLogger("ElementActions");

    /**
     * Log an action being performed on an element
     */
    public static void logAction(String elementInfo, String action, String... params) {
        if (params != null && params.length > 0) {
            LOG.info("🔹 {} - {}: {}", elementInfo, action, String.join(", ", params));
        } else {
            LOG.info("🔹 {} - {}", elementInfo, action);
        }
    }

    /**
     * Log a successful result of an action
     */
    public static void logSuccess(String elementInfo, String result) {
        LOG.info("✅ {} - Result: {}", elementInfo, result);
    }

    /**
     * Log a warning or potential issue
     */
    public static void logWarning(String elementInfo, String warning) {
        LOG.warn("⚠️ {} - Warning: {}", elementInfo, warning);
    }

    /**
     * Log an error that occurred during an action
     */
    public static void logError(String elementInfo, String error, Throwable exception) {
        LOG.error("❌ {} - Error: {}", elementInfo, error, exception);
    }
}
