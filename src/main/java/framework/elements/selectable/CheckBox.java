package framework.elements.selectable;

import framework.elements.core.BaseElement;
import framework.utils.LogUtils;

/**
 * Checkbox element wrapper
 */
public class CheckBox extends BaseElement {
    public CheckBox(String locator, String name) {
        super(locator, name);
    }

    /**
     * Check the checkbox
     */
    public void check() {
        LogUtils.logAction(toString(), "Checking checkbox");
        try {
            if (!isChecked()) {
                click();
                LogUtils.logSuccess(toString(), "Checkbox checked successfully");
            } else {
                LogUtils.logSuccess(toString(), "Checkbox already checked");
            }
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check checkbox", e);
            throw e;
        }
    }

    /**
     * Uncheck the checkbox
     */
    public void uncheck() {
        LogUtils.logAction(toString(), "Unchecking checkbox");
        try {
            if (isChecked()) {
                click();
                LogUtils.logSuccess(toString(), "Checkbox unchecked successfully");
            } else {
                LogUtils.logSuccess(toString(), "Checkbox already unchecked");
            }
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to uncheck checkbox", e);
            throw e;
        }
    }

    /**
     * Check if the checkbox is checked
     */
    public boolean isChecked() {
        LogUtils.logAction(toString(), "Getting checkbox state");
        try {
            boolean checked = element.isSelected();
            LogUtils.logSuccess(toString(), "Checkbox is " + (checked ? "checked" : "unchecked"));
            return checked;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get checkbox state", e);
            throw e;
        }
    }

    /**
     * Toggle checkbox state
     */
    public void toggle() {
        LogUtils.logAction(toString(), "Toggling checkbox state");
        try {
            boolean initialState = isChecked();
            click();
            LogUtils.logSuccess(toString(), 
                String.format("Checkbox toggled from %s to %s", 
                    initialState ? "checked" : "unchecked",
                    !initialState ? "checked" : "unchecked"));
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to toggle checkbox", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            return String.format("Checkbox '%s' [%s] {%s}", 
                getName(), getLocator(), isChecked() ? "checked" : "unchecked");
        } catch (Exception e) {
            return String.format("Checkbox '%s' [%s]", getName(), getLocator());
        }
    }
}
