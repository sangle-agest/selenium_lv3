package framework.elements.navigation;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Breadcrumbs navigation element wrapper
 */
public class Breadcrumbs extends BaseElement {
    private final String segmentLocator;
    
    /**
     * Constructor with breadcrumb container and segment locator
     */
    public Breadcrumbs(String containerLocator, String segmentLocator, String name) {
        super(containerLocator, name);
        this.segmentLocator = segmentLocator;
    }
    
    /**
     * Get full breadcrumb path as text
     */
    public String getPath() {
        LogUtils.logAction(toString(), "Getting breadcrumb path");
        try {
            List<String> segments = getSegments();
            String path = String.join(" > ", segments);
            LogUtils.logSuccess(toString(), "Got path: " + path);
            return path;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get breadcrumb path", e);
            throw e;
        }
    }
    
    /**
     * Get all breadcrumb segments as list
     */
    public List<String> getSegments() {
        LogUtils.logAction(toString(), "Getting breadcrumb segments");
        try {
            ElementsCollection segments = getElement().$$(segmentLocator);
            List<String> segmentTexts = new ArrayList<>();
            
            for (SelenideElement segment : segments) {
                segmentTexts.add(segment.getText().trim());
            }
            
            LogUtils.logSuccess(toString(), String.format("Got %d segments: %s", 
                segmentTexts.size(), String.join(", ", segmentTexts)));
            return segmentTexts;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get breadcrumb segments", e);
            throw e;
        }
    }
    
    /**
     * Get breadcrumb segment by index (0-based)
     */
    public String getSegment(int index) {
        LogUtils.logAction(toString(), "Getting breadcrumb segment at index " + index);
        try {
            ElementsCollection segments = getElement().$$(segmentLocator);
            
            if (index < 0 || index >= segments.size()) {
                LogUtils.logWarning(toString(), "Invalid segment index: " + index);
                throw new IndexOutOfBoundsException("Invalid segment index: " + index);
            }
            
            String segmentText = segments.get(index).getText().trim();
            LogUtils.logSuccess(toString(), "Got segment: " + segmentText);
            return segmentText;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get segment at index " + index, e);
            throw e;
        }
    }
    
    /**
     * Click on breadcrumb segment by index (0-based)
     */
    public void clickSegment(int index) {
        LogUtils.logAction(toString(), "Clicking breadcrumb segment at index " + index);
        try {
            ElementsCollection segments = getElement().$$(segmentLocator);
            
            if (index < 0 || index >= segments.size()) {
                LogUtils.logWarning(toString(), "Invalid segment index: " + index);
                throw new IndexOutOfBoundsException("Invalid segment index: " + index);
            }
            
            String segmentText = segments.get(index).getText().trim();
            segments.get(index).click();
            LogUtils.logSuccess(toString(), "Clicked on segment: " + segmentText);
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to click segment at index " + index, e);
            throw e;
        }
    }
    
    /**
     * Click on breadcrumb segment by index (0-based) with method chaining
     * @return this Breadcrumbs for method chaining
     */
    public Breadcrumbs clickSegmentAndChain(int index) {
        clickSegment(index);
        return this;
    }
    
    /**
     * Click on breadcrumb segment by text
     */
    public void clickSegment(String segmentText) {
        LogUtils.logAction(toString(), "Clicking breadcrumb segment: " + segmentText);
        try {
            ElementsCollection segments = getElement().$$(segmentLocator);
            boolean found = false;
            
            for (SelenideElement segment : segments) {
                if (segment.getText().trim().equals(segmentText)) {
                    segment.click();
                    found = true;
                    break;
                }
            }
            
            if (found) {
                LogUtils.logSuccess(toString(), "Clicked on segment: " + segmentText);
            } else {
                LogUtils.logWarning(toString(), "Segment not found: " + segmentText);
                throw new IllegalArgumentException("Segment not found: " + segmentText);
            }
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to click segment: " + segmentText, e);
            throw e;
        }
    }
    
    /**
     * Click on breadcrumb segment by text with method chaining
     * @return this Breadcrumbs for method chaining
     */
    public Breadcrumbs clickSegmentAndChain(String segmentText) {
        clickSegment(segmentText);
        return this;
    }
    
    /**
     * Check if breadcrumb contains segment
     */
    public boolean containsSegment(String segmentText) {
        LogUtils.logAction(toString(), "Checking if breadcrumb contains segment: " + segmentText);
        try {
            List<String> segments = getSegments();
            boolean contains = segments.contains(segmentText);
            LogUtils.logSuccess(toString(), 
                contains ? "Segment found: " + segmentText : "Segment not found: " + segmentText);
            return contains;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check for segment: " + segmentText, e);
            throw e;
        }
    }
    
    /**
     * Get total number of segments
     */
    public int getSegmentCount() {
        LogUtils.logAction(toString(), "Getting segment count");
        try {
            int count = getElement().$$(segmentLocator).size();
            LogUtils.logSuccess(toString(), "Segment count: " + count);
            return count;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get segment count", e);
            throw e;
        }
    }
    
    /**
     * Click the home/first segment
     */
    public void clickHome() {
        LogUtils.logAction(toString(), "Clicking home segment");
        try {
            clickSegment(0);
            LogUtils.logSuccess(toString(), "Clicked home segment");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to click home segment", e);
            throw e;
        }
    }
    
    /**
     * Click the home/first segment with method chaining
     * @return this Breadcrumbs for method chaining
     */
    public Breadcrumbs clickHomeAndChain() {
        clickHome();
        return this;
    }
    
    /**
     * Click the current/last segment
     */
    public void clickCurrent() {
        LogUtils.logAction(toString(), "Clicking current segment");
        try {
            int lastIndex = getSegmentCount() - 1;
            clickSegment(lastIndex);
            LogUtils.logSuccess(toString(), "Clicked current segment");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to click current segment", e);
            throw e;
        }
    }
    
    /**
     * Click the current/last segment with method chaining
     * @return this Breadcrumbs for method chaining
     */
    public Breadcrumbs clickCurrentAndChain() {
        clickCurrent();
        return this;
    }
    
    @Override
    public String toString() {
        try {
            return String.format("Breadcrumbs '%s' [%s] {path: %s}", 
                getName(), getLocator(), getPath());
        } catch (Exception e) {
            return String.format("Breadcrumbs '%s' [%s]", getName(), getLocator());
        }
    }
}
