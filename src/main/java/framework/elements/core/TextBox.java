package framework.elements.core;

import framework.utils.LogUtils;

/**
 * TextBox element for text input operations
 */
public class TextBox extends BaseElement {
    
    public TextBox(String locator, String name) {
        super(locator, name);
    }

    /**
     * Enter text into the field (void version)
     * @param text Text to enter
     */
    public void setText(String text) {
        setTextAndChain(text);
    }
    
    /**
     * Enter text into the field with method chaining
     * @param text Text to enter
     * @return this textbox for method chaining
     */
    public TextBox setTextAndChain(String text) {
        LogUtils.logAction(toString(), "Setting text: " + text);
        try {
            waitForVisible();
            getElement().setValue(text);
            LogUtils.logSuccess(toString(), "Text set successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to set text", e);
            throw e;
        }
    }

    /**
     * Clear existing text and enter new text
     * @param text Text to enter
     */
    public TextBox clearAndType(String text) {
        LogUtils.logAction(toString(), "Clearing and typing text: " + text);
        try {
            waitForVisible();
            getElement().clear();
            getElement().setValue(text);
            LogUtils.logSuccess(toString(), "Text cleared and typed successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to clear and type text", e);
            throw e;
        }
    }

    /**
     * Append text to existing content
     * @param text Text to append
     */
    public TextBox appendText(String text) {
        LogUtils.logAction(toString(), "Appending text: " + text);
        try {
            waitForVisible();
            getElement().append(text);
            LogUtils.logSuccess(toString(), "Text appended successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to append text", e);
            throw e;
        }
    }

    /**
     * Press Enter key
     */
    public TextBox pressEnter() {
        LogUtils.logAction(toString(), "Pressing Enter key");
        try {
            getElement().pressEnter();
            LogUtils.logSuccess(toString(), "Enter key pressed successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to press Enter key", e);
            throw e;
        }
    }

    /**
     * Press Tab key
     */
    public TextBox pressTab() {
        LogUtils.logAction(toString(), "Pressing Tab key");
        try {
            getElement().pressTab();
            LogUtils.logSuccess(toString(), "Tab key pressed successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to press Tab key", e);
            throw e;
        }
    }

    /**
     * Clear the text field
     */
    public TextBox clear() {
        LogUtils.logAction(toString(), "Clearing text field");
        try {
            waitForVisible();
            getElement().clear();
            LogUtils.logSuccess(toString(), "Text field cleared successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to clear text field", e);
            throw e;
        }
    }

    /**
     * Check if field is empty
     */
    public boolean isEmpty() {
        try {
            boolean empty = getValue().trim().isEmpty();
            LogUtils.logAction(toString(), empty ? "Field is empty" : "Field is not empty");
            return empty;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if field is empty: " + e.getMessage());
            return true;
        }
    }

    @Override
    public String toString() {
        try {
            String value = getElement().getValue();
            return String.format("TextBox '%s' [%s] {value: '%s'}", getName(), getLocator(), 
                value.length() > 20 ? value.substring(0, 17) + "..." : value);
        } catch (Exception e) {
            return String.format("TextBox '%s' [%s]", getName(), getLocator());
        }
    }

    /**
     * Get placeholder text
     */
    public String getPlaceholder() {
        return getAttribute("placeholder");
    }
}
