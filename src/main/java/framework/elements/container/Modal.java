package framework.elements.container;

import framework.elements.core.BaseElement;
import com.codeborne.selenide.Selenide;

/**
 * Modal/Dialog element wrapper
 */
public class Modal extends BaseElement {
    private final String closeButtonLocator;
    private final String confirmButtonLocator;
    private final String cancelButtonLocator;

    public Modal(String modalLocator, String closeButtonLocator, String confirmButtonLocator, 
                String cancelButtonLocator, String name) {
        super(modalLocator, name);
        this.closeButtonLocator = closeButtonLocator;
        this.confirmButtonLocator = confirmButtonLocator;
        this.cancelButtonLocator = cancelButtonLocator;
    }

    /**
     * Check if modal is open
     */
    public boolean isOpen() {
        return isDisplayed();
    }

    /**
     * Close the modal using close button
     */
    public void close() {
        if (isOpen() && closeButtonLocator != null) {
            Selenide.$(closeButtonLocator).click();
            waitForNotVisible();
        }
    }

    /**
     * Confirm/Accept the modal
     */
    public void confirm() {
        if (isOpen() && confirmButtonLocator != null) {
            Selenide.$(confirmButtonLocator).click();
            waitForNotVisible();
        }
    }

    /**
     * Cancel/Reject the modal
     */
    public void cancel() {
        if (isOpen() && cancelButtonLocator != null) {
            Selenide.$(cancelButtonLocator).click();
            waitForNotVisible();
        }
    }

    @Override
    public String toString() {
        return String.format("Modal '%s' [%s]", getName(), isOpen() ? "open" : "closed");
    }
}
