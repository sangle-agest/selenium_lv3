package framework.elements.selectable;

import framework.elements.core.BaseElement;

/**
 * Toggle switch element wrapper
 */
public class ToggleSwitch extends BaseElement {
    public ToggleSwitch(String locator, String name) {
        super(locator, name);
    }

    /**
     * Turn on the toggle switch
     */
    public void turnOn() {
        if (!isOn()) {
            click();
        }
    }

    /**
     * Turn off the toggle switch
     */
    public void turnOff() {
        if (isOn()) {
            click();
        }
    }

    /**
     * Check if the toggle switch is on
     */
    public boolean isOn() {
        return element.isSelected() || element.getAttribute("aria-checked").equals("true");
    }

    /**
     * Toggle switch state
     */
    public void toggle() {
        click();
    }

    @Override
    public String toString() {
        return String.format("Toggle switch '%s' [%s]", getName(), isOn() ? "ON" : "OFF");
    }
}
