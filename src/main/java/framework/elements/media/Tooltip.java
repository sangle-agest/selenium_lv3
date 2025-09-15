package framework.elements.media;

import framework.elements.core.BaseElement;

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
        hover();
        return getText();
    }

    /**
     * Hover over the trigger element to show tooltip
     */
    public void hover() {
        // Using raw Selenide $ to hover over trigger
        com.codeborne.selenide.Selenide.$(triggerLocator).hover();
        // Wait for tooltip to be visible
        waitForVisible();
    }

    /**
     * Check if tooltip is currently displayed
     */
    public boolean isTooltipVisible() {
        return isDisplayed();
    }

    @Override
    public String toString() {
        return String.format("Tooltip '%s' [%s]", getName(), 
            isTooltipVisible() ? "visible" : "hidden");
    }
}
