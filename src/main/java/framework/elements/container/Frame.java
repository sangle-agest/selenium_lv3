package framework.elements.container;

import framework.elements.core.BaseElement;
import com.codeborne.selenide.Selenide;

/**
 * Frame/iFrame element wrapper
 */
public class Frame extends BaseElement {
    public Frame(String locator, String name) {
        super(locator, name);
    }

    /**
     * Switch to this frame
     */
    public void switchTo() {
        waitForVisible();
        element.scrollTo();
        Selenide.switchTo().frame(element);
    }

    /**
     * Switch back to default content
     */
    public void switchBack() {
        Selenide.switchTo().defaultContent();
    }

    /**
     * Execute actions inside frame and switch back
     */
    public void withFrame(Runnable actions) {
        switchTo();
        try {
            actions.run();
        } finally {
            switchBack();
        }
    }

    @Override
    public String toString() {
        return String.format("Frame '%s' [%s]", getName(),
            isDisplayed() ? "visible" : "not visible");
    }
}
