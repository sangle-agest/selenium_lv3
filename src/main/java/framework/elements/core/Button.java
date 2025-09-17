package framework.elements.core;

import com.codeborne.selenide.Selenide;
import framework.utils.LogUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

/**
 * Button element with specialized button actions
 */
public class Button extends BaseElement {
    
    public Button(String locator, String name) {
        super(locator, name);
    }

    /**
     * Submit form (if button is submit type)
     */
    /**
     * Submit form (void version)
     */
    public void submit() {
        submitAndChain();
    }

    /**
     * Submit form with method chaining
     * @return this button for method chaining
     */
    public Button submitAndChain() {
        LogUtils.logAction(toString(), "Submitting form");
        try {
            getElement().submit();
            LogUtils.logSuccess(toString(), "Form submitted successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to submit form", e);
            throw e;
        }
    }
    
    /**
     * Focus the button using JavaScript (void version)
     */
    public void focus() {
        focusAndChain();
    }

    /**
     * Focus the button using JavaScript with method chaining
     * @return this button for method chaining
     */
    public Button focusAndChain() {
        LogUtils.logAction(toString(), "Focusing button");
        try {
            Selenide.executeJavaScript("arguments[0].focus();", getElement());
            LogUtils.logSuccess(toString(), "Button focused successfully");
            return this;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to focus button", e);
            throw e;
        }
    }    /**
     * Press and hold the button using Actions
     */
    public void pressAndHold() {
        LogUtils.logAction(toString(), "Pressing and holding button");
        try {
            new Actions(Selenide.webdriver().object())
                .clickAndHold(getElement())
                .perform();
            LogUtils.logSuccess(toString(), "Button pressed and held successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to press and hold button", e);
            throw e;
        }
    }

    /**
     * Release the button using Actions
     */
    public void release() {
        LogUtils.logAction(toString(), "Releasing button");
        try {
            new Actions(Selenide.webdriver().object())
                .release()
                .perform();
            LogUtils.logSuccess(toString(), "Button released successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to release button", e);
            throw e;
        }
    }

    /**
     * Press space key (alternative way to click button)
     */
    public void pressSpace() {
        LogUtils.logAction(toString(), "Pressing space key");
        try {
            getElement().sendKeys(Keys.SPACE);
            LogUtils.logSuccess(toString(), "Space key pressed successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to press space key", e);
            throw e;
        }
    }

    /**
     * Press enter key (alternative way to click button)
     */
    public void pressEnter() {
        LogUtils.logAction(toString(), "Pressing enter key");
        try {
            getElement().sendKeys(Keys.ENTER);
            LogUtils.logSuccess(toString(), "Enter key pressed successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to press enter key", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        return String.format("Button '%s' [%s]", getName(), getLocator());
    }
}
