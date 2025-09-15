package framework.elements.container;

import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import com.codeborne.selenide.Condition;

/**
 * Panel/Section element wrapper
 */
public class Panel extends BaseElement {
    private final String expandButtonLocator;
    private final String collapseButtonLocator;
    private final String contentLocator;
    
    public Panel(String panelLocator, String expandButtonLocator, 
                String collapseButtonLocator, String contentLocator, String name) {
        super(panelLocator, name);
        this.expandButtonLocator = expandButtonLocator;
        this.collapseButtonLocator = collapseButtonLocator;
        this.contentLocator = contentLocator;
    }

    /**
     * Expand the panel
     */
    public void expand() {
        LogUtils.logAction(toString(), "Expanding panel");
        try {
            if (!isExpanded()) {
                element.$(expandButtonLocator).click();
                element.$(contentLocator).shouldBe(Condition.visible);
                LogUtils.logSuccess(toString(), "Panel expanded successfully");
            } else {
                LogUtils.logAction(toString(), "Panel is already expanded");
            }
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to expand panel", e);
            throw e;
        }
    }

    /**
     * Collapse the panel
     */
    public void collapse() {
        LogUtils.logAction(toString(), "Collapsing panel");
        try {
            if (isExpanded()) {
                element.$(collapseButtonLocator).click();
                element.$(contentLocator).shouldBe(Condition.hidden);
                LogUtils.logSuccess(toString(), "Panel collapsed successfully");
            } else {
                LogUtils.logAction(toString(), "Panel is already collapsed");
            }
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to collapse panel", e);
            throw e;
        }
    }

    /**
     * Check if panel is expanded
     */
    public boolean isExpanded() {
        LogUtils.logAction(toString(), "Checking if panel is expanded");
        try {
            boolean expanded = element.$(contentLocator).is(Condition.visible);
            LogUtils.logSuccess(toString(), expanded ? "Panel is expanded" : "Panel is collapsed");
            return expanded;
        } catch (Exception e) {
            LogUtils.logWarning(toString(), "Failed to check panel state: " + e.getMessage());
            return false;
        }
    }

    /**
     * Toggle panel state
     */
    public void toggle() {
        LogUtils.logAction(toString(), "Toggling panel state");
        try {
            boolean wasExpanded = isExpanded();
            if (wasExpanded) {
                collapse();
            } else {
                expand();
            }
            LogUtils.logSuccess(toString(), String.format("Panel toggled from %s to %s",
                wasExpanded ? "expanded" : "collapsed",
                !wasExpanded ? "expanded" : "collapsed"));
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to toggle panel", e);
            throw e;
        }
    }

    /**
     * Get panel title
     */
    public String getTitle() {
        LogUtils.logAction(toString(), "Getting panel title");
        try {
            String title = element.$("[role='heading']").getText();
            LogUtils.logSuccess(toString(), "Got panel title: " + title);
            return title;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get panel title", e);
            throw e;
        }
    }

    /**
     * Get panel content
     */
    public String getContent() {
        LogUtils.logAction(toString(), "Getting panel content");
        try {
            String content = element.$(contentLocator).getText();
            LogUtils.logSuccess(toString(), "Got panel content");
            return content;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get panel content", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        return String.format("Panel '%s' [%s] {state: %s}", 
            getName(), getLocator(),
            isExpanded() ? "expanded" : "collapsed");
    }

    /**
     * Wait for panel to be expanded
     */
    public void waitForExpanded() {
        element.$(contentLocator).shouldBe(Condition.visible);
    }

    /**
     * Wait for panel to be collapsed
     */
    public void waitForCollapsed() {
        element.$(contentLocator).shouldBe(Condition.hidden);
    }
}
