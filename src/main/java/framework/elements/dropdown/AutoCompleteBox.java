package framework.elements.dropdown;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import static com.codeborne.selenide.Selenide.$$;

/**
 * AutoComplete element with suggestions list
 */
public class AutoCompleteBox extends BaseElement {
    private final ElementsCollection suggestions;
    
    public AutoCompleteBox(String inputLocator, String suggestionsLocator, String name) {
        super(inputLocator, name);
        this.suggestions = $$(suggestionsLocator);
    }

    /**
     * Type text and select first suggestion
     * @param text Text to type
     */
    public void typeAndSelectFirst(String text) {
        LogUtils.logAction(toString(), "Typing text and selecting first suggestion: " + text);
        try {
            waitForVisible();
            element.setValue(text);
            SelenideElement firstSuggestion = suggestions.first();
            String selectedText = firstSuggestion.getText();
            firstSuggestion.click();
            LogUtils.logSuccess(toString(), String.format("Selected first suggestion: '%s'", selectedText));
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to type and select first suggestion", e);
            throw e;
        }
    }

    /**
     * Type text and select suggestion by index
     * @param text Text to type
     * @param index Index of suggestion to select
     */
    public void typeAndSelect(String text, int index) {
        LogUtils.logAction(toString(), String.format("Typing text and selecting suggestion at index %d: %s", 
            index, text));
        try {
            waitForVisible();
            element.setValue(text);
            SelenideElement suggestion = suggestions.get(index);
            String selectedText = suggestion.getText();
            suggestion.click();
            LogUtils.logSuccess(toString(), String.format("Selected suggestion at index %d: '%s'", 
                index, selectedText));
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to type and select suggestion by index", e);
            throw e;
        }
    }

    /**
     * Type text and select suggestion by exact text
     * @param text Text to type
     * @param suggestionText Exact text of suggestion to select
     */
    public void typeAndSelectByText(String text, String suggestionText) {
        LogUtils.logAction(toString(), String.format("Typing text and selecting suggestion: '%s'", 
            suggestionText));
        try {
            waitForVisible();
            element.setValue(text);
            SelenideElement suggestion = suggestions.findBy(
                com.codeborne.selenide.Condition.exactText(suggestionText));
            suggestion.click();
            LogUtils.logSuccess(toString(), String.format("Selected suggestion: '%s'", suggestionText));
        } catch (Exception e) {
            LogUtils.logError(toString(), 
                String.format("Failed to type and select suggestion: '%s'", suggestionText), e);
            throw e;
        }
    }

    /**
     * Type text and select suggestion that contains text
     * @param text Text to type
     * @param containsText Text that suggestion should contain
     */
    public void typeAndSelectByContains(String text, String containsText) {
        LogUtils.logAction(toString(), String.format("Typing text and selecting suggestion containing: '%s'", 
            containsText));
        try {
            waitForVisible();
            element.setValue(text);
            SelenideElement suggestion = suggestions.findBy(
                com.codeborne.selenide.Condition.text(containsText));
            String selectedText = suggestion.getText();
            suggestion.click();
            LogUtils.logSuccess(toString(), 
                String.format("Selected suggestion containing '%s': '%s'", containsText, selectedText));
        } catch (Exception e) {
            LogUtils.logError(toString(), 
                String.format("Failed to type and select suggestion containing: '%s'", containsText), e);
            throw e;
        }
    }

    /**
     * Get number of suggestions
     */
    public int getSuggestionsCount() {
        LogUtils.logAction(toString(), "Getting suggestions count");
        try {
            int count = suggestions.size();
            LogUtils.logSuccess(toString(), String.format("Found %d suggestions", count));
            return count;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to get suggestions count: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public String toString() {
        String value = getValue();
        return String.format("AutoComplete '%s' [%s] {value: '%s', suggestions: %d}", 
            getName(), getLocator(),
            value.length() > 20 ? value.substring(0, 17) + "..." : value,
            getSuggestionsCount());
    }

    /**
     * Get all suggestion texts
     */
    public java.util.List<String> getSuggestionTexts() {
        return suggestions.texts();
    }
}
