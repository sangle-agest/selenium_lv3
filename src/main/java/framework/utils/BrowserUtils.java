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
    
    /**
     * Get number of open windows/tabs
     * @return Number of open windows/tabs
     */
    public static int getWindowCount() {
        LogUtils.logAction("Browser", "Getting window count");
        try {
            int count = WebDriverRunner.getWebDriver().getWindowHandles().size();
            LogUtils.logSuccess("Browser", "Window count: " + count);
            return count;
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to get window count", e);
            throw e;
        }
    }
    
    /**
     * Switch to window by title
     * @param title Title of the window to switch to (partial match)
     * @return true if switched successfully, false if no window with matching title found
     */
    public static boolean switchToWindowByTitle(String title) {
        LogUtils.logAction("Browser", "Switching to window with title containing: " + title);
        try {
            String currentHandle = WebDriverRunner.getWebDriver().getWindowHandle();
            for (String handle : WebDriverRunner.getWebDriver().getWindowHandles()) {
                WebDriverRunner.getWebDriver().switchTo().window(handle);
                if (WebDriverRunner.getWebDriver().getTitle().contains(title)) {
                    LogUtils.logSuccess("Browser", "Switched to window with title: " + WebDriverRunner.getWebDriver().getTitle());
                    return true;
                }
            }
            
            // If we didn't find a matching window, go back to the original
            WebDriverRunner.getWebDriver().switchTo().window(currentHandle);
            LogUtils.logWarning("Browser", "No window found with title containing: " + title);
            return false;
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to switch to window by title: " + title, e);
            throw e;
        }
    }
    
    /**
     * Close current window/tab and switch to the window/tab at the specified index
     * @param indexToSwitchTo Index of the window to switch to after closing the current one
     */
    public static void closeCurrentWindowAndSwitchTo(int indexToSwitchTo) {
        LogUtils.logAction("Browser", "Closing current window and switching to window at index: " + indexToSwitchTo);
        try {
            // Store all window handles before closing
            java.util.ArrayList<String> tabs = new java.util.ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
            
            if (tabs.size() <= indexToSwitchTo) {
                LogUtils.logWarning("Browser", "Cannot switch to index " + indexToSwitchTo + 
                        " as there are only " + tabs.size() + " windows");
                return;
            }
            
            // Close current window
            WebDriverRunner.getWebDriver().close();
            
            // Switch to the specified window
            WebDriverRunner.getWebDriver().switchTo().window(tabs.get(indexToSwitchTo));
            LogUtils.logSuccess("Browser", "Closed window and switched to window at index: " + indexToSwitchTo);
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to close window and switch", e);
            throw e;
        }
    }
    
    /**
     * Wait for a new tab/window to open
     * @param expectedCount Expected number of tabs/windows
     * @param timeoutInSeconds Maximum time to wait in seconds
     * @return true if the expected number of tabs/windows opened, false otherwise
     */
    public static boolean waitForWindowCount(int expectedCount, int timeoutInSeconds) {
        LogUtils.logAction("Browser", "Waiting for window count to be: " + expectedCount);
        try {
            long startTime = System.currentTimeMillis();
            long timeout = timeoutInSeconds * 1000L;
            
            while (System.currentTimeMillis() - startTime < timeout) {
                int currentCount = getWindowCount();
                if (currentCount >= expectedCount) {
                    LogUtils.logSuccess("Browser", "Window count reached " + currentCount);
                    return true;
                }
                
                // Sleep briefly before checking again
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            LogUtils.logWarning("Browser", "Timed out waiting for window count to reach " + expectedCount + 
                    ", current count: " + getWindowCount());
            return false;
        } catch (Exception e) {
            LogUtils.logError("Browser", "Error while waiting for window count", e);
            throw e;
        }
    }
    
    /**
     * Close all windows/tabs except the first one and switch back to it
     */
    public static void closeAllWindowsExceptFirst() {
        LogUtils.logAction("Browser", "Closing all windows except the first one");
        try {
            java.util.ArrayList<String> tabs = new java.util.ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
            
            if (tabs.size() <= 1) {
                LogUtils.logWarning("Browser", "Only one window is open, nothing to close");
                return;
            }
            
            String firstTab = tabs.get(0);
            
            // Close all tabs except the first one
            for (int i = 1; i < tabs.size(); i++) {
                WebDriverRunner.getWebDriver().switchTo().window(tabs.get(i));
                WebDriverRunner.getWebDriver().close();
            }
            
            // Switch back to the first tab
            WebDriverRunner.getWebDriver().switchTo().window(firstTab);
            LogUtils.logSuccess("Browser", "Closed all windows except the first one and switched back to it");
        } catch (Exception e) {
            LogUtils.logError("Browser", "Failed to close windows", e);
            throw e;
        }
    }
}
