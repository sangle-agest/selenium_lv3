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
     * Enter text into the field
     * @param text Text to enter
     */
    public void setText(String text) {
        LogUtils.logAction(toString(), "Setting text: " + text);
        try {
            waitForVisible();
            element.setValue(text);
            LogUtils.logSuccess(toString(), "Text set successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to set text", e);
            throw e;
        }
    }

    /**
     * Clear existing text and enter new text
     * @param text Text to enter
     */
    public void clearAndType(String text) {
        LogUtils.logAction(toString(), "Clearing and typing text: " + text);
        try {
            waitForVisible();
            element.clear();
            element.setValue(text);
            LogUtils.logSuccess(toString(), "Text cleared and typed successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to clear and type text", e);
            throw e;
        }
    }

    /**
     * Append text to existing content
     * @param text Text to append
     */
    public void appendText(String text) {
        LogUtils.logAction(toString(), "Appending text: " + text);
        try {
            waitForVisible();
            element.append(text);
            LogUtils.logSuccess(toString(), "Text appended successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to append text", e);
            throw e;
        }
    }

    /**
     * Press Enter key
     */
    public void pressEnter() {
        LogUtils.logAction(toString(), "Pressing Enter key");
        try {
            element.pressEnter();
            LogUtils.logSuccess(toString(), "Enter key pressed successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to press Enter key", e);
            throw e;
        }
    }

    /**
     * Press Tab key
     */
    public void pressTab() {
        LogUtils.logAction(toString(), "Pressing Tab key");
        try {
            element.pressTab();
            LogUtils.logSuccess(toString(), "Tab key pressed successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to press Tab key", e);
            throw e;
        }
    }

    /**
     * Clear the text field
     */
    public void clear() {
        LogUtils.logAction(toString(), "Clearing text field");
        try {
            waitForVisible();
            element.clear();
            LogUtils.logSuccess(toString(), "Text field cleared successfully");
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
        String value = getValue();
        return String.format("TextBox '%s' [%s] {value: '%s'}", getName(), getLocator(), 
            value.length() > 20 ? value.substring(0, 17) + "..." : value);
    }

    /**
     * Get placeholder text
     */
    public String getPlaceholder() {
        return getAttribute("placeholder");
    }
}
