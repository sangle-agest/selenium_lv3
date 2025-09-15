package framework.elements.core;

import framework.utils.LogUtils;

/**
 * Link element for hyperlinks
 */
public class Link extends BaseElement {
    
    public Link(String locator, String name) {
        super(locator, name);
    }

    /**
     * Get href attribute
     */
    public String getHref() {
        LogUtils.logAction(toString(), "Getting href attribute");
        try {
            String href = getAttribute("href");
            LogUtils.logSuccess(toString(), "Got href: " + href);
            return href;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get href", e);
            throw e;
        }
    }

    /**
     * Get link target
     */
    public String getTarget() {
        LogUtils.logAction(toString(), "Getting target attribute");
        try {
            String target = getAttribute("target");
            LogUtils.logSuccess(toString(), "Got target: " + target);
            return target;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get target", e);
            throw e;
        }
    }

    /**
     * Check if link opens in new window
     */
    public boolean opensInNewWindow() {
        LogUtils.logAction(toString(), "Checking if link opens in new window");
        try {
            String target = getTarget();
            boolean opensNew = target != null && target.equals("_blank");
            LogUtils.logSuccess(toString(), opensNew ? "Link opens in new window" : "Link opens in same window");
            return opensNew;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check if link opens in new window", e);
            throw e;
        }
    }

    /**
     * Get link title (tooltip)
     */
    public String getTitle() {
        LogUtils.logAction(toString(), "Getting title attribute");
        try {
            String title = getAttribute("title");
            LogUtils.logSuccess(toString(), "Got title: " + title);
            return title;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get title", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        String href = getHref();
        return String.format("Link '%s' [%s] {href: %s}", 
            getName(), getLocator(), 
            href != null ? href : "no href");
    }
}
