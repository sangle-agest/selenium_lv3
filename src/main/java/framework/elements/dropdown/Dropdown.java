package framework.elements.dropdown;

import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import java.util.List;

/**
 * Dropdown/Select element wrapper
 */
public class Dropdown extends BaseElement {
    public Dropdown(String selectLocator, String name) {
        super(selectLocator, name);
    }

    /**
     * Select option by visible text (void version)
     */
    public void selectByVisibleText(String text) {
        LogUtils.logAction(toString(), "Selecting option by text: " + text);
        try {
            waitForClickable();
            getElement().selectOption(text);
            LogUtils.logSuccess(toString(), "Option selected successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to select option by text", e);
            throw e;
        }
    }
    
    /**
     * Select option by visible text with method chaining
     * @return this dropdown for method chaining
     */
    public Dropdown selectByVisibleTextAndChain(String text) {
        selectByVisibleText(text);
        return this;
    }

    /**
     * Select option by value attribute (void version)
     */
    public void selectByValue(String value) {
        LogUtils.logAction(toString(), "Selecting option by value: " + value);
        try {
            waitForClickable();
            getElement().selectOptionByValue(value);
            LogUtils.logSuccess(toString(), "Option selected successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to select option by value", e);
            throw e;
        }
    }
    
    /**
     * Select option by value attribute with method chaining
     * @return this dropdown for method chaining
     */
    public Dropdown selectByValueAndChain(String value) {
        selectByValue(value);
        return this;
    }

    /**
     * Select option by index (void version)
     */
    public void selectByIndex(int index) {
        LogUtils.logAction(toString(), "Selecting option by index: " + index);
        try {
            waitForClickable();
            getElement().selectOption(index);
            LogUtils.logSuccess(toString(), "Option selected successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to select option by index", e);
            throw e;
        }
    }
    
    /**
     * Select option by index with method chaining
     * @return this dropdown for method chaining
     */
    public Dropdown selectByIndexAndChain(int index) {
        selectByIndex(index);
        return this;
    }

    /**
     * Get selected option text
     */
    public String getSelectedText() {
        LogUtils.logAction("Dropdown '" + getName() + "'", "Getting selected option text");
        try {
            String text = getElement().getSelectedOption().getText();
            LogUtils.logSuccess("Dropdown '" + getName() + "'", "Got selected text: " + text);
            return text;
        } catch (Exception e) {
            LogUtils.logError("Dropdown '" + getName() + "'", "Failed to get selected text", e);
            throw e;
        }
    }

    /**
     * Get selected option value
     */
    public String getSelectedValue() {
        LogUtils.logAction(toString(), "Getting selected option value");
        try {
            String value = getElement().getSelectedOption().getValue();
            LogUtils.logSuccess(toString(), "Got selected value: " + value);
            return value;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get selected value", e);
            throw e;
        }
    }

    /**
     * Get all available options
     */
    public List<String> getAllOptions() {
        LogUtils.logAction(toString(), "Getting all option texts");
        try {
            List<String> options = getElement().getOptions().texts();
            LogUtils.logSuccess(toString(), String.format("Got %d options", options.size()));
            return options;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get option texts", e);
            throw e;
        }
    }

    /**
     * Get all available option values
     */
    public List<String> getAllValues() {
        LogUtils.logAction(toString(), "Getting all option values");
        try {
            List<String> values = getElement().getOptions().stream()
                    .map(option -> option.getValue())
                    .toList();
            LogUtils.logSuccess(toString(), String.format("Got %d option values", values.size()));
            return values;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get option values", e);
            throw e;
        }
    }

    /**
     * Check if option exists by text
     */
    public boolean hasOption(String text) {
        LogUtils.logAction(toString(), "Checking if option exists: " + text);
        try {
            boolean exists = getElement().getOptions().texts().contains(text);
            LogUtils.logSuccess(toString(), String.format("Option '%s' %s", text, 
                exists ? "exists" : "does not exist"));
            return exists;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if option exists: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if option exists by value
     */
    public boolean hasValue(String value) {
        LogUtils.logAction(toString(), "Checking if value exists: " + value);
        try {
            boolean exists = getAllValues().contains(value);
            LogUtils.logSuccess(toString(), String.format("Value '%s' %s", value, 
                exists ? "exists" : "does not exist"));
            return exists;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check if value exists: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get number of options
     */
    public int getOptionsCount() {
        try {
            int count = getElement().getOptions().size();
            LogUtils.logAction("Dropdown '" + getName() + "'", "Got options count: " + count);
            return count;
        } catch (Exception e) {
            LogUtils.logWarning("Dropdown '" + getName() + "'", "Failed to get options count: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public String toString() {
        try {
            int count = getElement().getOptions().size();
            String selectedText = "unknown";
            try {
                selectedText = getElement().getSelectedOption().getText();
            } catch (Exception e) {
                // Ignore
            }
            return String.format("Dropdown '%s' with %d options, selected: '%s'", 
                getName(), 
                count, 
                selectedText);
        } catch (Exception e) {
            return String.format("Dropdown '%s'", getName());
        }
    }
}
