package framework.elements.selectable;

import framework.elements.core.BaseElement;

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
        if (!isSelected()) {
            click();
        }
    }

    /**
     * Check if the radio button is selected
     */
    public boolean isSelected() {
        return element.isSelected();
    }

    @Override
    public String toString() {
        return String.format("Radio button '%s' [%s]", getName(), isSelected() ? "selected" : "not selected");
    }
}
