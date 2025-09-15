package framework.elements.control;

import framework.elements.core.BaseElement;

/**
 * Progress bar element wrapper
 */
public class ProgressBar extends BaseElement {
    private final String valueAttribute;

    public ProgressBar(String locator, String name) {
        this(locator, name, "value");
    }

    public ProgressBar(String locator, String name, String valueAttribute) {
        super(locator, name);
        this.valueAttribute = valueAttribute;
    }

    /**
     * Get progress value (0-100)
     */
    public int getProgress() {
        String value = element.getAttribute(valueAttribute);
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // Try parsing as float and converting to int
                try {
                    return (int) Float.parseFloat(value);
                } catch (NumberFormatException ex) {
                    return 0;
                }
            }
        }
        // Try getting width percentage if value attribute is not available
        String style = element.getAttribute("style");
        if (style != null && style.contains("width:")) {
            try {
                String width = style.split("width:")[1].split("%")[0].trim();
                return (int) Float.parseFloat(width);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Wait until progress reaches 100%
     */
    public void waitUntilComplete() {
        while (getProgress() < 100) {
            try {
                Thread.sleep(500); // Check every 500ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Wait until progress reaches specific value
     */
    public void waitUntilValue(int expectedValue) {
        while (getProgress() < expectedValue) {
            try {
                Thread.sleep(500); // Check every 500ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("ProgressBar '%s' [%d%%]", getName(), getValue());
    }
}
