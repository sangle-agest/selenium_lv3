package framework.elements.media;

import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import com.codeborne.selenide.Selenide;

/**
 * Tooltip element wrapper
 */
public class Tooltip extends BaseElement {
    private final String triggerLocator;

    public Tooltip(String tooltipLocator, String triggerLocator, String name) {
        super(tooltipLocator, name);
        this.triggerLocator = triggerLocator;
    }

    /**
     * Get tooltip text by hovering over the trigger element
     */
    public String getTooltipText() {
        LogUtils.logAction(toString(), "Getting tooltip text");
        try {
            hover();
            String text = getText();
            LogUtils.logSuccess(toString(), "Got tooltip text: " + text);
            return text;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get tooltip text", e);
            throw e;
        }
    }

    /**
     * Hover over the trigger element to show tooltip
     */
    public void hover() {
        LogUtils.logAction(toString(), "Hovering over trigger element: " + triggerLocator);
        try {
            // Using raw Selenide $ to hover over trigger
            Selenide.$(triggerLocator).hover();
            // Wait for tooltip to be visible
            waitForVisible();
            LogUtils.logSuccess(toString(), "Hovered over trigger element successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to hover over trigger element", e);
            throw e;
        }
    }

    /**
     * Check if tooltip is currently displayed
     */
    public boolean isTooltipVisible() {
        LogUtils.logAction(toString(), "Checking if tooltip is visible");
        try {
            boolean visible = isDisplayed();
            LogUtils.logSuccess(toString(), "Tooltip is " + (visible ? "visible" : "not visible"));
            return visible;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check tooltip visibility", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            return String.format("Tooltip '%s' [%s] {trigger: %s}", 
                getName(), getLocator(), triggerLocator);
        } catch (Exception e) {
            return String.format("Tooltip '%s'", getName());
        }
    }
}
