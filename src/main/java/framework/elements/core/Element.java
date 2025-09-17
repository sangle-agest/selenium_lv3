package framework.elements.core;

import com.codeborne.selenide.SelenideElement;
import framework.utils.LogUtils;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Base class for UI elements
 */
public class Element {
    protected final String locator;
    protected final String name;
    
    /**
     * Constructor
     * @param locator CSS or XPath locator
     * @param name Descriptive name for logging
     */
    public Element(String locator, String name) {
        this.locator = locator;
        this.name = name;
    }
    
    /**
     * Get the underlying SelenideElement
     * @return SelenideElement for this locator
     */
    public SelenideElement getElement() {
        if (locator.startsWith("/")) {
            return $x(locator);
        } else {
            return $(locator);
        }
    }
    
    /**
     * Click on the element
     */
    public void click() {
        LogUtils.logAction(toString(), "Clicking");
        try {
            getElement().click();
            LogUtils.logSuccess(toString(), "Clicked successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to click", e);
            throw e;
        }
    }
    
    /**
     * Check if element exists
     * @return true if element exists
     */
    public boolean exists() {
        return getElement().exists();
    }
    
    /**
     * Get element attribute
     * @param attributeName Name of the attribute
     * @return Attribute value
     */
    public String getAttribute(String attributeName) {
        try {
            return getElement().getAttribute(attributeName);
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get attribute: " + attributeName, e);
            return null;
        }
    }
    
    /**
     * Get element text
     * @return Text content of the element
     */
    public String getText() {
        try {
            return getElement().text();
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get text", e);
            return "";
        }
    }
    
    @Override
    public String toString() {
        return name + " (" + locator + ")";
    }
}