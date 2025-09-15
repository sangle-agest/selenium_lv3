package framework.utils;

import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;

/**
 * Utility class for browser-level operations
 */
public class BrowserUtils {
    
    /**
     * Open URL in browser
     */
    public static void openUrl(String url) {
        LogUtils.logAction("Browser", "Opening URL: " + url);
        try {
            open(url);
            LogUtils.logSuccess("Browser", "Opened URL successfully");
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to open URL: " + url, e);
            throw e;
        }
    }

    /**
     * Get current URL
     */
    public static String getCurrentUrl() {
        LogUtils.logAction("Browser", "Getting current URL");
        try {
            String url = WebDriverRunner.url();
            LogUtils.logSuccess("Browser", "Current URL: " + url);
            return url;
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to get current URL", e);
            throw e;
        }
    }

    /**
     * Get page title
     */
    public static String getTitle() {
        LogUtils.logAction("Browser", "Getting page title");
        try {
            String title = WebDriverRunner.getWebDriver().getTitle();
            LogUtils.logSuccess("Browser", "Page title: " + title);
            return title;
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to get page title", e);
            throw e;
        }
    }

    /**
     * Refresh current page
     */
    public static void refresh() {
        LogUtils.logAction("Browser", "Refreshing page");
        try {
            refresh();
            LogUtils.logSuccess("Browser", "Page refreshed successfully");
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to refresh page", e);
            throw e;
        }
    }

    /**
     * Navigate back
     */
    public static void back() {
        LogUtils.logAction("Browser", "Navigating back");
        try {
            back();
            LogUtils.logSuccess("Browser", "Navigated back successfully");
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to navigate back", e);
            throw e;
        }
    }

    /**
     * Navigate forward
     */
    public static void forward() {
        LogUtils.logAction("Browser", "Navigating forward");
        try {
            forward();
            LogUtils.logSuccess("Browser", "Navigated forward successfully");
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to navigate forward", e);
            throw e;
        }
    }

    /**
     * Switch to window by index
     */
    public static void switchToWindow(int index) {
        LogUtils.logAction("Browser", "Switching to window at index: " + index);
        try {
            switchTo().window(index);
            LogUtils.logSuccess("Browser", "Switched to window successfully");
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to switch to window at index: " + index, e);
            throw e;
        }
    }

    /**
     * Switch to frame by locator
     */
    public static void switchToFrame(By locator) {
        LogUtils.logAction("Browser", "Switching to frame: " + locator);
        try {
            switchTo().frame($(locator));
            LogUtils.logSuccess("Browser", "Switched to frame successfully");
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to switch to frame: " + locator, e);
            throw e;
        }
    }

    /**
     * Switch to default content
     */
    public static void switchToDefaultContent() {
        LogUtils.logAction("Browser", "Switching to default content");
        try {
            switchTo().defaultContent();
            LogUtils.logSuccess("Browser", "Switched to default content successfully");
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to switch to default content", e);
            throw e;
        }
    }
}
