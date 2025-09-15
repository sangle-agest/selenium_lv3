package framework.elements.core;

import framework.utils.LogUtils;

/**
 * Label element for static text
 */
public class Label extends BaseElement {
    
    public Label(String locator, String name) {
        super(locator, name);
    }

    /**
     * Get inner text (trimmed)
     */
    public String getInnerText() {
        LogUtils.logAction(toString(), "Getting inner text");
        try {
            String text = getText().trim();
            LogUtils.logSuccess(toString(), "Got inner text: " + text);
            return text;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get inner text", e);
            throw e;
        }
    }

    /**
     * Check if label contains text
     */
    public boolean containsText(String text) {
        LogUtils.logAction(toString(), "Checking if contains text: " + text);
        try {
            boolean contains = getText().contains(text);
            LogUtils.logSuccess(toString(), contains ? 
                "Text found" : "Text not found");
            return contains;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check text content", e);
            throw e;
        }
    }

    /**
     * Get text content (including hidden text)
     */
    public String getTextContent() {
        LogUtils.logAction(toString(), "Getting text content");
        try {
            String content = getAttribute("textContent");
            LogUtils.logSuccess(toString(), "Got text content: " + content);
            return content;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get text content", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            String text = getText().trim();
            return String.format("Label '%s' [%s] {text: '%s'}", 
                getName(), getLocator(),
                text.length() > 20 ? text.substring(0, 17) + "..." : text);
        } catch (Exception e) {
            return String.format("Label '%s' [%s]", getName(), getLocator());
        }
    }
}
