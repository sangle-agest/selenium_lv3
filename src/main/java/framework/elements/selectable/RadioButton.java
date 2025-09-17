package framework.elements.selectable;

import framework.elements.core.BaseElement;
import framework.utils.LogUtils;

/**
 * Radio button element wrapper
 */
public class RadioButton extends BaseElement {
    public RadioButton(String locator, String name) {
        super(locator, name);
    }

    /**
     * Select the radio button
     */
    public void select() {
        LogUtils.logAction(toString(), "Selecting radio button");
        try {
            if (!isSelected()) {
                click();
                LogUtils.logSuccess(toString(), "Radio button selected successfully");
            } else {
                LogUtils.logSuccess(toString(), "Radio button already selected");
            }
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to select radio button", e);
            throw e;
        }
    }
    
    /**
     * Select the radio button with method chaining
     * @return this RadioButton for method chaining
     */
    public RadioButton selectAndChain() {
        select();
        return this;
    }

    /**
     * Check if the radio button is selected
     */
    public boolean isSelected() {
        LogUtils.logAction(toString(), "Checking if radio button is selected");
        try {
            boolean selected = getElement().isSelected();
            LogUtils.logSuccess(toString(), "Radio button is " + (selected ? "selected" : "not selected"));
            return selected;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check if radio button is selected", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            return String.format("Radio button '%s' [%s] {%s}", 
                getName(), getLocator(), isSelected() ? "selected" : "not selected");
        } catch (Exception e) {
            return String.format("Radio button '%s' [%s]", getName(), getLocator());
        }
    }
}
