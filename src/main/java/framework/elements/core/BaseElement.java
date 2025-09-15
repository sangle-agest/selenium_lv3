package framework.elements.core;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import framework.utils.ConfigManager;
import framework.utils.LogUtils;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.Selenide;

/**
 * Base class for all web elements providing common functionality
 */
public abstract class BaseElement {
    protected final SelenideElement element;
    protected final String name;
    protected final String locator;

    public BaseElement(String locator, String name) {
        this.element = $(locator);
        this.name = name;
        this.locator = locator;
    }

    // Basic Properties
    public String getName() {
        return name;
    }

    public String getLocator() {
        return locator;
    }

    public SelenideElement getElement() {
        return element;
    }

    // Visibility & State
    public boolean isDisplayed() {
        try {
            boolean displayed = element.is(Condition.visible);
            LogUtils.logAction(toString(), displayed ? "Is displayed" : "Is not displayed");
            return displayed;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isEnabled() {
        try {
            boolean enabled = element.is(Condition.enabled);
            LogUtils.logAction(toString(), enabled ? "Is enabled" : "Is disabled");
            return enabled;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if enabled: " + e.getMessage());
            return false;
        }
    }

    public boolean exists() {
        try {
            boolean exists = element.exists();
            LogUtils.logAction(toString(), exists ? "Exists" : "Does not exist");
            return exists;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if exists: " + e.getMessage());
            return false;
        }
    }

    // Actions
    public void click() {
        LogUtils.logAction(toString(), "Clicking");
        try {
            waitForClickable();
            element.click();
            LogUtils.logSuccess(toString(), "Clicked successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to click", e);
            throw e;
        }
    }

    public String getText() {
        LogUtils.logAction(toString(), "Getting text");
        try {
            String text = element.getText();
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
            String value = element.getAttribute(attributeName);
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
            String value = element.getValue();
            LogUtils.logSuccess(toString(), "Got value: " + value);
            return value;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get value", e);
            throw e;
        }
    }

    // Wait Conditions
    public void waitForVisible() {
        LogUtils.logAction(toString(), "Waiting to be visible");
        try {
            element.shouldBe(Condition.visible, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element became visible");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting to be visible", e);
            throw e;
        }
    }

    public void waitForClickable() {
        LogUtils.logAction(toString(), "Waiting to be clickable");
        try {
            element.shouldBe(Condition.enabled, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element became clickable");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting to be clickable", e);
            throw e;
        }
    }

    public void waitForExist() {
        LogUtils.logAction(toString(), "Waiting to exist");
        try {
            element.shouldBe(Condition.exist, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element exists");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting to exist", e);
            throw e;
        }
    }

    public void waitForNotVisible() {
        LogUtils.logAction(toString(), "Waiting to be not visible");
        try {
            element.shouldBe(Condition.hidden, Duration.ofMillis(ConfigManager.getElementTimeout()));
            LogUtils.logSuccess(toString(), "Element became not visible");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting to be not visible", e);
            throw e;
        }
    }

    // Mouse Actions
    public void hover() {
        LogUtils.logAction(toString(), "Hovering");
        try {
            element.hover();
            LogUtils.logSuccess(toString(), "Hovered successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to hover", e);
            throw e;
        }
    }

    public void rightClick() {
        LogUtils.logAction(toString(), "Right clicking");
        try {
            element.contextClick();
            LogUtils.logSuccess(toString(), "Right clicked successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to right click", e);
            throw e;
        }
    }

    public void doubleClick() {
        LogUtils.logAction(toString(), "Double clicking");
        try {
            element.doubleClick();
            LogUtils.logSuccess(toString(), "Double clicked successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to double click", e);
            throw e;
        }
    }

    // Scroll
    public void scrollTo() {
        LogUtils.logAction(toString(), "Scrolling to element");
        try {
            element.scrollTo();
            LogUtils.logSuccess(toString(), "Scrolled to element successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to scroll to element", e);
            throw e;
        }
    }

    public void scrollIntoView() {
        LogUtils.logAction(toString(), "Scrolling element into view");
        try {
            Selenide.executeJavaScript(
                "arguments[0].scrollIntoView({behavior: 'instant', block: 'center', inline: 'center'})",
                element
            );
            LogUtils.logSuccess(toString(), "Scrolled into view successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to scroll into view", e);
            throw e;
        }
    }

    // CSS & Style
    public String getCssValue(String propertyName) {
        LogUtils.logAction(toString(), "Getting CSS value: " + propertyName);
        try {
            String value = element.getCssValue(propertyName);
            LogUtils.logSuccess(toString(), String.format("Got CSS value %s: %s", propertyName, value));
            return value;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get CSS value: " + propertyName, e);
            throw e;
        }
    }

    public boolean hasClass(String className) {
        try {
            boolean hasClass = element.has(Condition.cssClass(className));
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
            boolean disabled = element.is(Condition.disabled);
            LogUtils.logAction(toString(), disabled ? "Is disabled" : "Is not disabled");
            return disabled;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if disabled: " + e.getMessage());
            return false;
        }
    }

    public boolean isReadOnly() {
        try {
            boolean readOnly = element.is(Condition.readonly);
            LogUtils.logAction(toString(), readOnly ? "Is read-only" : "Is not read-only");
            return readOnly;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if read-only: " + e.getMessage());
            return false;
        }
    }

    // Custom Wait Conditions
    public void waitForCondition(WebElementCondition condition) {
        waitForCondition(condition, ConfigManager.getElementTimeout());
    }

    public void waitForCondition(WebElementCondition condition, long timeoutMillis) {
        LogUtils.logAction(toString(), "Waiting for condition: " + condition);
        try {
            element.shouldBe(condition, Duration.ofMillis(timeoutMillis));
            LogUtils.logSuccess(toString(), "Condition met successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed waiting for condition: " + condition, e);
            throw e;
        }
    }

    @Override
    public String toString() {
        return String.format("Element '%s' [%s]", name, locator);
    }
}
