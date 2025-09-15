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
     * Select option by visible text
     */
    public void selectByVisibleText(String text) {
        LogUtils.logAction(toString(), "Selecting option by text: " + text);
        try {
            waitForClickable();
            element.selectOption(text);
            LogUtils.logSuccess(toString(), "Option selected successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to select option by text", e);
            throw e;
        }
    }

    /**
     * Select option by value attribute
     */
    public void selectByValue(String value) {
        LogUtils.logAction(toString(), "Selecting option by value: " + value);
        try {
            waitForClickable();
            element.selectOptionByValue(value);
            LogUtils.logSuccess(toString(), "Option selected successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to select option by value", e);
            throw e;
        }
    }

    /**
     * Select option by index
     */
    public void selectByIndex(int index) {
        LogUtils.logAction(toString(), "Selecting option by index: " + index);
        try {
            waitForClickable();
            element.selectOption(index);
            LogUtils.logSuccess(toString(), "Option selected successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to select option by index", e);
            throw e;
        }
    }

    /**
     * Get selected option text
     */
    public String getSelectedText() {
        LogUtils.logAction(toString(), "Getting selected option text");
        try {
            String text = element.getSelectedOption().getText();
            LogUtils.logSuccess(toString(), "Got selected text: " + text);
            return text;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get selected text", e);
            throw e;
        }
    }

    /**
     * Get selected option value
     */
    public String getSelectedValue() {
        LogUtils.logAction(toString(), "Getting selected option value");
        try {
            String value = element.getSelectedOption().getValue();
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
            List<String> options = element.getOptions().texts();
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
            List<String> values = element.getOptions().stream()
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
            boolean exists = element.getOptions().texts().contains(text);
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
            int count = element.getOptions().size();
            LogUtils.logAction(toString(), "Got options count: " + count);
            return count;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to get options count: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("Dropdown '%s' with %d options, selected: '%s'", 
            getName(), 
            getOptionsCount(), 
            getSelectedText());
    }
}
