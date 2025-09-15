package framework.elements.media;

import framework.elements.core.BaseElement;

/**
 * Icon element wrapper
 */
public class Icon extends BaseElement {
    public Icon(String locator, String name) {
        super(locator, name);
    }

    /**
     * Get icon class or type
     */
    public String getIconType() {
        String className = element.getAttribute("class");
        // Common icon class patterns: fa-*, icon-*, material-icons, etc.
        return className != null ? className : "";
    }

    @Override
    public String toString() {
        return String.format("Icon '%s' [type: %s]", getName(), getIconType());
    }
}
