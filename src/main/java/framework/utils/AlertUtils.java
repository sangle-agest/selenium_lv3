package framework.utils;

import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Utility class for handling alerts and popups
 */
public class AlertUtils {
    
    /**
     * Accept alert
     */
    public static void acceptAlert() {
        LogUtils.logAction("Alert", "Accepting alert");
        try {
            switchTo().alert().accept();
            LogUtils.logSuccess("Alert", "Alert accepted successfully");
        } catch (Exception e) {
            LogUtils.logError("Alert", "Failed to accept alert", e);
            throw e;
        }
    }

    /**
     * Dismiss alert
     */
    public static void dismissAlert() {
        LogUtils.logAction("Alert", "Dismissing alert");
        try {
            switchTo().alert().dismiss();
            LogUtils.logSuccess("Alert", "Alert dismissed successfully");
        } catch (Exception e) {
            LogUtils.logError("Alert", "Failed to dismiss alert", e);
            throw e;
        }
    }

    /**
     * Get alert text
     */
    public static String getAlertText() {
        LogUtils.logAction("Alert", "Getting alert text");
        try {
            String text = switchTo().alert().getText();
            LogUtils.logSuccess("Alert", "Alert text: " + text);
            return text;
        } catch (Exception e) {
            LogUtils.logError("Alert", "Failed to get alert text", e);
            throw e;
        }
    }

    /**
     * Send keys to alert
     */
    public static void sendKeysToAlert(String text) {
        LogUtils.logAction("Alert", "Sending keys to alert: " + text);
        try {
            switchTo().alert().sendKeys(text);
            LogUtils.logSuccess("Alert", "Keys sent to alert successfully");
        } catch (Exception e) {
            LogUtils.logError("Alert", "Failed to send keys to alert", e);
            throw e;
        }
    }
}
