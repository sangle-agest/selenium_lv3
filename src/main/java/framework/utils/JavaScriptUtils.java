package framework.utils;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

/**
 * Utility class for JavaScript operations
 */
public class JavaScriptUtils {
    
    /**
     * Execute JavaScript
     */
    public static Object executeJs(String script, Object... args) {
        LogUtils.logAction("JavaScript", "Executing script: " + script);
        try {
            Object result = executeJavaScript(script, args);
            LogUtils.logSuccess("JavaScript", "Script executed successfully");
            return result;
        } catch (Exception e) {
            LogUtils.logError("JavaScript", "Failed to execute script", e);
            throw e;
        }
    }

    /**
     * Scroll by x and y coordinates
     */
    public static void scrollBy(int x, int y) {
        LogUtils.logAction("JavaScript", String.format("Scrolling by x: %d, y: %d", x, y));
        try {
            executeJs("window.scrollBy(arguments[0], arguments[1]);", x, y);
            LogUtils.logSuccess("JavaScript", "Scrolled successfully");
        } catch (Exception e) {
            LogUtils.logError("JavaScript", "Failed to scroll", e);
            throw e;
        }
    }

    /**
     * Highlight element (useful for debugging)
     */
    public static void highlightElement(By locator) {
        LogUtils.logAction("JavaScript", "Highlighting element: " + locator);
        try {
            SelenideElement element = $(locator);
            String originalStyle = element.getAttribute("style");
            
            executeJs("arguments[0].style.border='3px solid red'", element);
            Thread.sleep(500); // Highlight for half second
            
            if (originalStyle != null) {
                executeJs("arguments[0].style.border=arguments[1]", element, originalStyle);
            } else {
                executeJs("arguments[0].removeAttribute('style')", element);
            }
            
            LogUtils.logSuccess("JavaScript", "Element highlighted successfully");
        } catch (InterruptedException e) {
            LogUtils.logError("JavaScript", "Highlight interrupted", e);
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (Exception e) {
            LogUtils.logError("JavaScript", "Failed to highlight element", e);
            throw e;
        }
    }

    /**
     * Scroll element into view
     */
    public static void scrollIntoView(By locator) {
        LogUtils.logAction("JavaScript", "Scrolling element into view: " + locator);
        try {
            SelenideElement element = $(locator);
            executeJs("arguments[0].scrollIntoView(true);", element);
            LogUtils.logSuccess("JavaScript", "Scrolled to element successfully");
        } catch (Exception e) {
            LogUtils.logError("JavaScript", "Failed to scroll to element", e);
            throw e;
        }
    }
}
