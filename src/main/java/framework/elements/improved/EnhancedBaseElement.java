package framework.elements.improved;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import framework.utils.ConfigManager;
import framework.utils.LogUtils;
import java.time.Duration;
import java.util.function.Function;
import java.util.function.Supplier;

import org.openqa.selenium.StaleElementReferenceException;

import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.Selenide;

/**
 * Enhanced base element with additional features and improvements
 */
public abstract class EnhancedBaseElement {
    // Lazy initialization using Supplier
    protected final Supplier<SelenideElement> elementSupplier;
    protected final String name;
    protected final String locator;

    /**
     * Constructor with explicit name for the element
     * @param locator CSS or XPath selector
     * @param name Descriptive name for logging and debugging
     */
    public EnhancedBaseElement(String locator, String name) {
        this.elementSupplier = () -> $(locator);
        this.name = name;
        this.locator = locator;
    }
    
    /**
     * Constructor using locator as the name
     * @param locator CSS or XPath selector
     */
    public EnhancedBaseElement(String locator) {
        this.elementSupplier = () -> $(locator);
        this.name = locator;
        this.locator = locator;
    }

    // Get the actual element (lazy initialization)
    protected SelenideElement getElement() {
        return elementSupplier.get();
    }

    // Basic Properties
    public String getName() {
        return name;
    }

    public String getLocator() {
        return locator;
    }

    // Visibility & State
    public boolean isDisplayed() {
        try {
            boolean displayed = getElement().is(Condition.visible);
            LogUtils.logAction(toString(), displayed ? "Is displayed" : "Is not displayed");
            return displayed;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isEnabled() {
        try {
            boolean enabled = getElement().is(Condition.enabled);
            LogUtils.logAction(toString(), enabled ? "Is enabled" : "Is disabled");
            return enabled;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if enabled: " + e.getMessage());
            return false;
        }
    }

    public boolean exists() {
        try {
            boolean exists = getElement().exists();
            LogUtils.logAction(toString(), exists ? "Exists" : "Does not exist");
            return exists;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if exists: " + e.getMessage());
            return false;
        }
    }

    // Actions
    public EnhancedBaseElement click() {
        LogUtils.logAction(toString(), "Clicking");
        try {
            waitForClickable();
            getElement().click();
            LogUtils.logSuccess(toString(), "Clicked successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to click", e);
            throw e;
        }
    }

    public String getText() {
        LogUtils.logAction(toString(), "Getting text");
        try {
            String text = getElement().getText();
            LogUtils.logSuccess(toString(), "Got text: " + text);
            return text;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get text", e);
            throw e;
        }
    }

    public String getAttribute(String attributeName) {
        LogUtils.logAction(toString(), "Getting attribute: " + attributeName);
        try {
            String value = getElement().getAttribute(attributeName);
            LogUtils.logSuccess(toString(), String.format("Got attribute %s: %s", attributeName, value));
            return value;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get attribute: " + attributeName, e);
            throw e;
        }
    }

    public String getValue() {
        LogUtils.logAction(toString(), "Getting value");
        try {
            String value = getElement().getValue();
            LogUtils.logSuccess(toString(), "Got value: " + value);
            return value;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get value", e);
            throw e;
        }
    }

    // Wait Conditions
    public EnhancedBaseElement waitForVisible() {
        LogUtils.logAction(toString(), "Waiting to be visible");
        try {
            getElement().shouldBe(Condition.visible, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element became visible");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting to be visible", e);
            throw e;
        }
    }

    public EnhancedBaseElement waitForClickable() {
        LogUtils.logAction(toString(), "Waiting to be clickable");
        try {
            getElement().shouldBe(Condition.visible, Duration.ofMillis(ConfigManager.getElementTimeout()));
            getElement().shouldBe(Condition.enabled, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element became clickable");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting to be clickable", e);
            throw e;
        }
    }

    public EnhancedBaseElement waitForExist() {
        LogUtils.logAction(toString(), "Waiting to exist");
        try {
            getElement().shouldBe(Condition.exist, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element exists");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting to exist", e);
            throw e;
        }
    }

    public EnhancedBaseElement waitForNotVisible() {
        LogUtils.logAction(toString(), "Waiting to be not visible");
        try {
            getElement().shouldBe(Condition.hidden, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element became not visible");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting to be not visible", e);
            throw e;
        }
    }

    // New Enhanced Wait Methods
    public EnhancedBaseElement waitForText(String expectedText) {
        LogUtils.logAction(toString(), "Waiting for text: " + expectedText);
        try {
            getElement().shouldHave(Condition.exactText(expectedText), 
                Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element has expected text");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting for text: " + expectedText, e);
            throw e;
        }
    }

    public EnhancedBaseElement waitForTextContains(String partialText) {
        LogUtils.logAction(toString(), "Waiting for text containing: " + partialText);
        try {
            getElement().shouldHave(Condition.text(partialText), 
                Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element contains expected text");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting for text containing: " + partialText, e);
            throw e;
        }
    }

    public EnhancedBaseElement waitForAttributeValue(String attribute, String value) {
        LogUtils.logAction(toString(), 
            String.format("Waiting for attribute %s to have value %s", attribute, value));
        try {
            getElement().shouldHave(Condition.attribute(attribute, value), 
                Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element has expected attribute value");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), 
                String.format("Failed waiting for attribute %s to have value %s", attribute, value), e);
            throw e;
        }
    }

    // Mouse Actions
    public EnhancedBaseElement hover() {
        LogUtils.logAction(toString(), "Hovering");
        try {
            getElement().hover();
            LogUtils.logSuccess(toString(), "Hovered successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to hover", e);
            throw e;
        }
    }

    public EnhancedBaseElement rightClick() {
        LogUtils.logAction(toString(), "Right clicking");
        try {
            getElement().contextClick();
            LogUtils.logSuccess(toString(), "Right clicked successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to right click", e);
            throw e;
        }
    }

    public EnhancedBaseElement doubleClick() {
        LogUtils.logAction(toString(), "Double clicking");
        try {
            getElement().doubleClick();
            LogUtils.logSuccess(toString(), "Double clicked successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to double click", e);
            throw e;
        }
    }

    // Scroll
    public EnhancedBaseElement scrollTo() {
        LogUtils.logAction(toString(), "Scrolling to element");
        try {
            getElement().scrollTo();
            LogUtils.logSuccess(toString(), "Scrolled to element successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to scroll to element", e);
            throw e;
        }
    }

    public EnhancedBaseElement scrollIntoView() {
        LogUtils.logAction(toString(), "Scrolling element into view");
        try {
            Selenide.executeJavaScript(
                "arguments[0].scrollIntoView({behavior: 'instant', block: 'center', inline: 'center'})",
                getElement()
            );
            LogUtils.logSuccess(toString(), "Scrolled into view successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to scroll into view", e);
            throw e;
        }
    }

    // CSS & Style
    public String getCssValue(String propertyName) {
        LogUtils.logAction(toString(), "Getting CSS value: " + propertyName);
        try {
            String value = getElement().getCssValue(propertyName);
            LogUtils.logSuccess(toString(), String.format("Got CSS value %s: %s", propertyName, value));
            return value;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get CSS value: " + propertyName, e);
            throw e;
        }
    }

    public boolean hasClass(String className) {
        try {
            boolean hasClass = getElement().has(Condition.cssClass(className));
            LogUtils.logAction(toString(), String.format("Class '%s' %s", className, 
                hasClass ? "is present" : "is not present"));
            return hasClass;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check class: " + className);
            return false;
        }
    }

    // State Validation
    public boolean isDisabled() {
        try {
            boolean disabled = getElement().is(Condition.disabled);
            LogUtils.logAction(toString(), disabled ? "Is disabled" : "Is not disabled");
            return disabled;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if disabled: " + e.getMessage());
            return false;
        }
    }

    public boolean isReadOnly() {
        try {
            boolean readOnly = getElement().is(Condition.readonly);
            LogUtils.logAction(toString(), readOnly ? "Is read-only" : "Is not read-only");
            return readOnly;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if read-only: " + e.getMessage());
            return false;
        }
    }

    // Custom Wait Conditions
    public EnhancedBaseElement waitForCondition(WebElementCondition condition) {
        return waitForCondition(condition, ConfigManager.getElementTimeout());
    }

    public EnhancedBaseElement waitForCondition(WebElementCondition condition, long timeoutMillis) {
        LogUtils.logAction(toString(), "Waiting for condition: " + condition);
        try {
            getElement().shouldBe(condition, Duration.ofMillis(timeoutMillis));
            LogUtils.logSuccess(toString(), "Condition met successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting for condition: " + condition, e);
            throw e;
        }
    }
    
    // New: Execute with retry for stale element cases
    public <T> T executeWithRetry(Function<SelenideElement, T> action, String actionDescription) {
        LogUtils.logAction(toString(), actionDescription);
        Exception lastException = null;
        int maxRetries = 3;
        
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                T result = action.apply(getElement());
                LogUtils.logSuccess(toString(), "Successfully " + actionDescription);
                return result;
            } catch (StaleElementReferenceException e) {
                lastException = e;
                LogUtils.logWarning(toString(), "Stale element encountered, retrying: " + e.getMessage());
                Selenide.sleep(500); // Brief pause before retry
            } catch (Exception e) {
                LogUtils.logError(toString(), "Failed to " + actionDescription, e);
                throw e;
            }
        }
        
        LogUtils.logError(toString(), "Failed to " + actionDescription + " after " + maxRetries + " attempts", lastException);
        throw new RuntimeException("Failed after " + maxRetries + " retries", lastException);
    }
    
    // Wait for AJAX calls to complete
    public EnhancedBaseElement waitForAjaxComplete() {
        LogUtils.logAction(toString(), "Waiting for AJAX calls to complete");
        try {
            Selenide.executeJavaScript(
                "return new Promise(resolve => {" +
                "  const checkReady = () => {" +
                "    const jQueryActive = typeof jQuery !== 'undefined' ? jQuery.active : 0;" +
                "    const ajaxActive = typeof window.XMLHttpRequest !== 'undefined' ? " +
                "      document.querySelectorAll('*').length > 0 : false;" +
                "    if (jQueryActive === 0 && !ajaxActive) {" +
                "      resolve();" +
                "    } else {" +
                "      setTimeout(checkReady, 100);" +
                "    }" +
                "  };" +
                "  checkReady();" +
                "});"
            );
            LogUtils.logSuccess(toString(), "AJAX calls completed");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting for AJAX calls to complete", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        
        // If name is the same as locator (when using the single-param constructor), 
        // just show the element type and locator to avoid redundancy
        if (name.equals(locator)) {
            return String.format("%s [%s]", className, locator);
        }
        return String.format("%s '%s' [%s]", className, name, locator);
    }
}
