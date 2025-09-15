package framework.elements.core;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebElementCondition;
import static com.codeborne.selenide.Selenide.$$;
import java.util.List;

/**
 * Collection of elements wrapper
 */
public class ElementCollection {
    protected final ElementsCollection elements;
    protected final String name;
    protected final String locator;

    public ElementCollection(String locator, String name) {
        this.elements = $$(locator);
        this.name = name;
        this.locator = locator;
    }

    /**
     * Get element at index
     * @param index Index of element (0-based)
     */
    public BaseElement get(int index) {
        return new BaseElement(locator + ":nth-child(" + (index + 1) + ")", name + "[" + index + "]") {};
    }

    /**
     * Get first element
     */
    public BaseElement first() {
        return new BaseElement(locator + ":first-child", name + "[first]") {};
    }

    /**
     * Get last element
     */
    public BaseElement last() {
        return new BaseElement(locator + ":last-child", name + "[last]") {};
    }

    /**
     * Get size of collection
     */
    public int size() {
        return elements.size();
    }

    /**
     * Get text from all elements
     */
    public List<String> getTexts() {
        return elements.texts();
    }

    /**
     * Find element by exact text
     */
    public BaseElement findByText(String text) {
        return new BaseElement(locator + ":has-text('" + text + "')", name) {};
    }

    /**
     * Find element that contains text
     */
    public BaseElement findByPartialText(String partialText) {
        return new BaseElement(locator + ":contains('" + partialText + "')", name) {};
    }

    /**
     * Filter elements by text
     */
    public List<BaseElement> filterByText(String text) {
        List<BaseElement> result = new java.util.ArrayList<>();
        List<String> texts = elements.texts();
        for (int i = 0; i < texts.size(); i++) {
            if (texts.get(i).contains(text)) {
                result.add(new BaseElement(locator + ":nth-child(" + (i + 1) + ")", name) {});
            }
        }
        return result;
    }

    /**
     * Get element by attribute value
     */
    public BaseElement findByAttribute(String attribute, String value) {
        return new BaseElement(locator + "[" + attribute + "='" + value + "']", name) {};
    }

    /**
     * Check if collection is empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Get underlying Selenide ElementsCollection
     */
    public ElementsCollection getElements() {
        return elements;
    }
}
