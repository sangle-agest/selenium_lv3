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
            getElement().selectOption(text);
        }
    }
    
    /**
     * Select multiple options by visible text with method chaining
     * @return this ListBox for method chaining
     */
    public ListBox selectByTextsAndChain(String... texts) {
        selectByTexts(texts);
        return this;
    }

    /**
     * Select multiple options by values
     */
    public void selectByValues(String... values) {
        waitForClickable();
        for (String value : values) {
            getElement().selectOptionByValue(value);
        }
    }
    
    /**
     * Select multiple options by values with method chaining
     * @return this ListBox for method chaining
     */
    public ListBox selectByValuesAndChain(String... values) {
        selectByValues(values);
        return this;
    }

    /**
     * Get all selected options text
     */
    public List<String> getSelectedTexts() {
        return getElement().getSelectedOptions().texts();
    }

    /**
     * Get all selected option values
     */
    public List<String> getSelectedValues() {
        return getElement().getSelectedOptions().stream()
                .map(option -> option.getValue())
                .toList();
    }

    /**
     * Deselect all options
     */
    public void deselectAll() {
        getSelectedTexts().forEach(text -> {
            getElement().selectOption(text); // clicking again deselects in multi-select
        });
    }
    
    /**
     * Deselect all options with method chaining
     * @return this ListBox for method chaining
     */
    public ListBox deselectAllAndChain() {
        deselectAll();
        return this;
    }

    /**
     * Deselect options by visible text
     */
    public void deselectByTexts(String... texts) {
        for (String text : texts) {
            if (getSelectedTexts().contains(text)) {
                getElement().selectOption(text); // clicking again deselects in multi-select
            }
        }
    }
    
    /**
     * Deselect options by visible text with method chaining
     * @return this ListBox for method chaining
     */
    public ListBox deselectByTextsAndChain(String... texts) {
        deselectByTexts(texts);
        return this;
    }

    /**
     * Check if multiple selection is supported
     */
    public boolean isMultiple() {
        return "multiple".equals(getElement().getAttribute("multiple"));
    }

    @Override
    public String toString() {
        return String.format("ListBox '%s' [%d selected of %d options]", 
            getName(), 
            getSelectedTexts().size(),
            getElement().getOptions().size());
    }
}
