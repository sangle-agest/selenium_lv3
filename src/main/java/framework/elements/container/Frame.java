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
     * Switch to this frame (void version)
     */
    public void switchTo() {
        waitForVisible();
        getElement().scrollTo();
        Selenide.switchTo().frame(getElement());
    }

    /**
     * Switch to this frame with method chaining
     * @return this frame for method chaining
     */
    public Frame switchToAndChain() {
        switchTo();
        return this;
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
