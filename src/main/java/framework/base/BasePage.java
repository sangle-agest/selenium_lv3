package framework.base;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import framework.utils.ConfigManager;
import framework.utils.LogUtils;
import framework.utils.BrowserUtils;
import framework.utils.JavaScriptUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;

/**
 * BasePage: Parent class for all page objects.
 * Contains common methods like navigation, waits, and getting page title.
 */
public abstract class BasePage {
    protected final String pageName;
    
    /**
     * Constructor with page name for logging
     */
    public BasePage(String pageName) {
        this.pageName = pageName;
        LogUtils.logAction(this.toString(), "Initializing page object");
    }
    
    /**
     * Default constructor
     */
    public BasePage() {
        this.pageName = this.getClass().getSimpleName();
    }

    /**
     * Opens a given URL in the browser.
     * @param url Full URL of the page.
     */
    public void openPage(String url) {
        LogUtils.logAction(this.toString(), "Opening page: " + url);
        try {
            open(url);
            LogUtils.logSuccess(this.toString(), "Page opened successfully: " + url);
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to open page: " + url, e);
            throw e;
        }
    }

    /**
     * Get current page title.
     * @return Page title string.
     */
    public String getPageTitle() {
        LogUtils.logAction(this.toString(), "Getting page title");
        try {
            String title = title();
            LogUtils.logSuccess(this.toString(), "Page title: " + title);
            return title;
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to get page title", e);
            throw e;
        }
    }
    
    /**
     * Get current page URL
     * @return Current URL string
     */
    public String getCurrentUrl() {
        LogUtils.logAction(this.toString(), "Getting current URL");
        try {
            String url = WebDriverRunner.url();
            LogUtils.logSuccess(this.toString(), "Current URL: " + url);
            return url;
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to get current URL", e);
            throw e;
        }
    }
    
    /**
     * Wait for element to be visible
     * @param element SelenideElement to wait for
     */
    protected void waitForElementVisible(SelenideElement element) {
        LogUtils.logAction(this.toString(), "Waiting for element to be visible");
        try {
            element.shouldBe(Condition.visible, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(this.toString(), "Element is visible");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Element not visible after timeout", e);
            throw e;
        }
    }

    /**
     * Wait for element to be clickable
     * @param element SelenideElement to wait for
     */
    protected void waitForElementClickable(SelenideElement element) {
        LogUtils.logAction(this.toString(), "Waiting for element to be clickable");
        try {
            element.shouldBe(Condition.enabled, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(this.toString(), "Element is clickable");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Element not clickable after timeout", e);
            throw e;
        }
    }

    /**
     * Scroll element into view
     * @param element SelenideElement to scroll to
     */
    protected void scrollIntoView(SelenideElement element) {
        LogUtils.logAction(this.toString(), "Scrolling element into view");
        try {
            element.scrollTo();
            LogUtils.logSuccess(this.toString(), "Scrolled to element");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to scroll to element", e);
            throw e;
        }
    }

    /**
     * Check if element exists and is visible
     * @param element SelenideElement to check
     * @return true if element exists and is visible
     */
    protected boolean isElementVisible(SelenideElement element) {
        LogUtils.logAction(this.toString(), "Checking if element is visible");
        try {
            boolean isVisible = element.is(Condition.visible);
            LogUtils.logSuccess(this.toString(), "Element is " + (isVisible ? "visible" : "not visible"));
            return isVisible;
        } catch (Exception e) {
            LogUtils.logWarning(this.toString(), "Failed to check element visibility, assuming not visible");
            return false;
        }
    }
    
    /**
     * Wait for page to load completely
     */
    public void waitForPageToLoad() {
        LogUtils.logAction(this.toString(), "Waiting for page to load completely");
        try {
            WebDriver driver = WebDriverRunner.getWebDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // Wait for the readyState to be 'complete'
            long timeout = ConfigManager.getPageLoadTimeout();
            long startTime = System.currentTimeMillis();
            
            while (System.currentTimeMillis() - startTime < timeout) {
                String readyState = (String) js.executeScript("return document.readyState");
                if (readyState.equals("complete")) {
                    LogUtils.logSuccess(this.toString(), "Page loaded completely");
                    return;
                }
                sleep(100);
            }
            
            LogUtils.logWarning(this.toString(), "Page load timed out");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Error while waiting for page to load", e);
            throw e;
        }
    }
    
    /**
     * Refresh current page
     */
    public void refreshPage() {
        LogUtils.logAction(this.toString(), "Refreshing page");
        try {
            refresh();
            waitForPageToLoad();
            LogUtils.logSuccess(this.toString(), "Page refreshed successfully");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to refresh page", e);
            throw e;
        }
    }
    
    /**
     * Navigate back
     */
    public void goBack() {
        LogUtils.logAction(this.toString(), "Navigating back");
        try {
            back();
            waitForPageToLoad();
            LogUtils.logSuccess(this.toString(), "Navigated back successfully");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to navigate back", e);
            throw e;
        }
    }
    
    /**
     * Take screenshot of current page
     * @return Path to the screenshot file
     */
    public String takeScreenshot() {
        LogUtils.logAction(this.toString(), "Taking screenshot");
        try {
            String screenshotPath = screenshot("screenshot_" + System.currentTimeMillis());
            LogUtils.logSuccess(this.toString(), "Screenshot taken: " + screenshotPath);
            return screenshotPath;
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to take screenshot", e);
            throw e;
        }
    }
    
    /**
     * Execute JavaScript on the page
     * @param script JavaScript code to execute
     * @param args Arguments to pass to the script
     * @return Result of script execution
     */
    protected Object executeJavaScript(String script, Object... args) {
        LogUtils.logAction(this.toString(), "Executing JavaScript");
        try {
            Object result = JavaScriptUtils.executeJs(script, args);
            LogUtils.logSuccess(this.toString(), "JavaScript executed successfully");
            return result;
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to execute JavaScript", e);
            throw e;
        }
    }
    
    /**
     * Switch to frame by locator
     * @param locator Frame locator
     */
    protected void switchToFrame(By locator) {
        LogUtils.logAction(this.toString(), "Switching to frame: " + locator);
        try {
            BrowserUtils.switchToFrame(locator);
            LogUtils.logSuccess(this.toString(), "Switched to frame successfully");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to switch to frame", e);
            throw e;
        }
    }
    
    /**
     * Switch back to default content
     */
    protected void switchToDefaultContent() {
        LogUtils.logAction(this.toString(), "Switching to default content");
        try {
            BrowserUtils.switchToDefaultContent();
            LogUtils.logSuccess(this.toString(), "Switched to default content successfully");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to switch to default content", e);
            throw e;
        }
    }
    
    @Override
    public String toString() {
        return "Page[" + pageName + "]";
    }
}
