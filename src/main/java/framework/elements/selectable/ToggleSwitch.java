package framework.elements.selectable;

import framework.elements.core.BaseElement;
import framework.utils.LogUtils;

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
        LogUtils.logAction(toString(), "Turning on toggle switch");
        try {
            if (!isOn()) {
                click();
                LogUtils.logSuccess(toString(), "Toggle switch turned on successfully");
            } else {
                LogUtils.logSuccess(toString(), "Toggle switch already on");
            }
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to turn on toggle switch", e);
            throw e;
        }
    }
    
    /**
     * Turn on the toggle switch with method chaining
     * @return this ToggleSwitch for method chaining
     */
    public ToggleSwitch turnOnAndChain() {
        turnOn();
        return this;
    }

    /**
     * Turn off the toggle switch
     */
    public void turnOff() {
        LogUtils.logAction(toString(), "Turning off toggle switch");
        try {
            if (isOn()) {
                click();
                LogUtils.logSuccess(toString(), "Toggle switch turned off successfully");
            } else {
                LogUtils.logSuccess(toString(), "Toggle switch already off");
            }
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to turn off toggle switch", e);
            throw e;
        }
    }
    
    /**
     * Turn off the toggle switch with method chaining
     * @return this ToggleSwitch for method chaining
     */
    public ToggleSwitch turnOffAndChain() {
        turnOff();
        return this;
    }

    /**
     * Check if the toggle switch is on
     */
    public boolean isOn() {
        LogUtils.logAction(toString(), "Checking if toggle switch is on");
        try {
            boolean on = getElement().isSelected() || 
                         (getElement().getAttribute("aria-checked") != null && 
                          getElement().getAttribute("aria-checked").equals("true"));
            LogUtils.logSuccess(toString(), "Toggle switch is " + (on ? "ON" : "OFF"));
            return on;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check toggle switch state", e);
            throw e;
        }
    }

    /**
     * Toggle switch state
     */
    public void toggle() {
        LogUtils.logAction(toString(), "Toggling switch state");
        try {
            boolean initialState = isOn();
            click();
            LogUtils.logSuccess(toString(), 
                String.format("Toggle switch toggled from %s to %s", 
                    initialState ? "ON" : "OFF",
                    !initialState ? "ON" : "OFF"));
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to toggle switch", e);
            throw e;
        }
    }
    
    /**
     * Toggle switch state with method chaining
     * @return this ToggleSwitch for method chaining
     */
    public ToggleSwitch toggleAndChain() {
        toggle();
        return this;
    }

    @Override
    public String toString() {
        try {
            return String.format("Toggle switch '%s' [%s] {%s}", 
                getName(), getLocator(), isOn() ? "ON" : "OFF");
        } catch (Exception e) {
            return String.format("Toggle switch '%s' [%s]", getName(), getLocator());
        }
    }
}
