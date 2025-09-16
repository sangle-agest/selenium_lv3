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
 * Contains page-specific operations and delegates browser operations to BrowserUtils
 * and JavaScript operations to JavaScriptUtils.
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
     * Delegates to BrowserUtils for browser operations.
     * @param url Full URL of the page.
     */
    public void openPage(String url) {
        LogUtils.logAction(this.toString(), "Opening page: " + url);
        BrowserUtils.openUrl(url);
    }

    /**
     * Get current page title.
     * Delegates to BrowserUtils for browser operations.
     * @return Page title string.
     */
    public String getPageTitle() {
        LogUtils.logAction(this.toString(), "Getting page title");
        return BrowserUtils.getTitle();
    }
    
    /**
     * Get current page URL.
     * Delegates to BrowserUtils for browser operations.
     * @return Current URL string
     */
    public String getCurrentUrl() {
        LogUtils.logAction(this.toString(), "Getting current URL");
        return BrowserUtils.getCurrentUrl();
    }
    
    /**
     * Wait for element to be visible
     * This is retained in BasePage as it's commonly used in page-specific interactions
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
     * This is retained in BasePage as it's commonly used in page-specific interactions
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
     * Scroll element into view using JavaScriptUtils
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
     * This is retained in BasePage as it's commonly used in page-specific interactions
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
     * This is a page-specific operation so it belongs in BasePage
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
     * Refresh current page - delegates to BrowserUtils
     */
    public void refreshPage() {
        LogUtils.logAction(this.toString(), "Refreshing page");
        BrowserUtils.refresh();
        waitForPageToLoad();
    }
    
    /**
     * Navigate back - delegates to BrowserUtils
     */
    public void goBack() {
        LogUtils.logAction(this.toString(), "Navigating back");
        BrowserUtils.back();
        waitForPageToLoad();
    }
    
    /**
     * Take screenshot of current page
     * This is a page-specific operation so it belongs in BasePage
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
     * Execute JavaScript on the page - delegates to JavaScriptUtils
     * @param script JavaScript code to execute
     * @param args Arguments to pass to the script
     * @return Result of script execution
     */
    protected Object executeJavaScript(String script, Object... args) {
        LogUtils.logAction(this.toString(), "Executing JavaScript");
        return JavaScriptUtils.executeJs(script, args);
    }
    
    /**
     * Switch to frame by locator - delegates to BrowserUtils
     * @param locator Frame locator
     */
    protected void switchToFrame(By locator) {
        LogUtils.logAction(this.toString(), "Switching to frame: " + locator);
        BrowserUtils.switchToFrame(locator);
    }
    
    /**
     * Switch back to default content - delegates to BrowserUtils
     */
    protected void switchToDefaultContent() {
        LogUtils.logAction(this.toString(), "Switching to default content");
        BrowserUtils.switchToDefaultContent();
    }
    
    @Override
    public String toString() {
        return "Page[" + pageName + "]";
    }
}
