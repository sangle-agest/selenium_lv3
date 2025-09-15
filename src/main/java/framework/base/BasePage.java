package framework.base;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import framework.utils.ConfigManager;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;

/**
 * BasePage: Parent class for all page objects.
 * Contains common methods like navigation, waits, and getting page title.
 */
public abstract class BasePage {

    /**
     * Opens a given URL in the browser.
     * @param url Full URL of the page.
     */
    public void openPage(String url) {
        open(url);
    }

    /**
     * Get current page title.
     * @return Page title string.
     */
    public String getPageTitle() {
        return title();
    }
    
    /**
     * Wait for element to be visible
     * @param element SelenideElement to wait for
     */
    protected void waitForElementVisible(SelenideElement element) {
        element.shouldBe(Condition.visible, Duration.ofMillis(ConfigManager.getElementTimeout()));
    }

    /**
     * Wait for element to be clickable
     * @param element SelenideElement to wait for
     */
    protected void waitForElementClickable(SelenideElement element) {
        element.shouldBe(Condition.enabled, Duration.ofMillis(ConfigManager.getElementTimeout()));
    }

    /**
     * Scroll element into view
     * @param element SelenideElement to scroll to
     */
    protected void scrollIntoView(SelenideElement element) {
        element.scrollTo();
    }

    /**
     * Check if element exists and is visible
     * @param element SelenideElement to check
     * @return true if element exists and is visible
     */
    protected boolean isElementVisible(SelenideElement element) {
        return element.is(Condition.visible);
    }
}
