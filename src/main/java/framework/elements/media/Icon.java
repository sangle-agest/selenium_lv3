package framework.elements.media;

import framework.elements.core.BaseElement;
import framework.utils.LogUtils;

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
        LogUtils.logAction(toString(), "Getting icon type/class");
        try {
            String className = getElement().getAttribute("class");
            // Common icon class patterns: fa-*, icon-*, material-icons, etc.
            String iconType = className != null ? className : "";
            LogUtils.logSuccess(toString(), "Got icon type: " + iconType);
            return iconType;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get icon type", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            return String.format("Icon '%s' [%s] {type: %s}", 
                getName(), getLocator(), getIconType());
        } catch (Exception e) {
            return String.format("Icon '%s' [%s]", getName(), getLocator());
        }
    }
}
