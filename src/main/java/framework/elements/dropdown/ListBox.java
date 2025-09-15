package framework.elements.dropdown;

import framework.elements.core.BaseElement;
import java.util.List;

/**
 * ListBox element wrapper for multi-select lists
 */
public class ListBox extends BaseElement {
    public ListBox(String locator, String name) {
        super(locator, name);
    }

    /**
     * Select multiple options by visible text
     */
    public void selectByTexts(String... texts) {
        waitForClickable();
        for (String text : texts) {
            element.selectOption(text);
        }
    }

    /**
     * Select multiple options by values
     */
    public void selectByValues(String... values) {
        waitForClickable();
        for (String value : values) {
            element.selectOptionByValue(value);
        }
    }

    /**
     * Get all selected options text
     */
    public List<String> getSelectedTexts() {
        return element.getSelectedOptions().texts();
    }

    /**
     * Get all selected option values
     */
    public List<String> getSelectedValues() {
        return element.getSelectedOptions().stream()
                .map(option -> option.getValue())
                .toList();
    }

    /**
     * Deselect all options
     */
    public void deselectAll() {
        getSelectedTexts().forEach(text -> {
            element.selectOption(text); // clicking again deselects in multi-select
        });
    }

    /**
     * Deselect options by visible text
     */
    public void deselectByTexts(String... texts) {
        for (String text : texts) {
            if (getSelectedTexts().contains(text)) {
                element.selectOption(text); // clicking again deselects in multi-select
            }
        }
    }

    /**
     * Check if multiple selection is supported
     */
    public boolean isMultiple() {
        return "multiple".equals(element.getAttribute("multiple"));
    }

    @Override
    public String toString() {
        return String.format("ListBox '%s' [%d selected of %d options]", 
            getName(), 
            getSelectedTexts().size(),
            element.getOptions().size());
    }
}
