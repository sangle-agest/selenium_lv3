package framework.elements.control;

import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.interactions.Actions;

/**
 * Slider element wrapper
 */
public class Slider extends BaseElement {
    
    public Slider(String locator, String name) {
        super(locator, name);
    }

    /**
     * Move slider to specific percentage
     * @param percentage Value from 0 to 100
     */
    public void slideTo(int percentage) {
        LogUtils.logAction(toString(), String.format("Moving slider to %d%%", percentage));
        try {
            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException("Percentage must be between 0 and 100");
            }

            int width = element.getSize().getWidth();
            int xOffset = (int) (width * (percentage / 100.0)) - (width / 2);

            new Actions(Selenide.webdriver().object())
                .clickAndHold(element)
                .moveByOffset(xOffset, 0)
                .release()
                .perform();
                
            LogUtils.logSuccess(toString(), String.format("Slider moved to %d%%", percentage));
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to move slider", e);
            throw e;
        }
    }

    /**
     * Get current value
     */
    public String getValue() {
        LogUtils.logAction(toString(), "Getting slider value");
        try {
            String value = element.getValue();
            LogUtils.logSuccess(toString(), "Got slider value: " + value);
            return value;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get slider value", e);
            throw e;
        }
    }

    /**
     * Move slider by offset
     */
    public void moveByOffset(int xOffset) {
        LogUtils.logAction(toString(), String.format("Moving slider by offset: %d", xOffset));
        try {
            new Actions(Selenide.webdriver().object())
                .clickAndHold(element)
                .moveByOffset(xOffset, 0)
                .release()
                .perform();
            LogUtils.logSuccess(toString(), "Slider moved by offset successfully");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to move slider by offset", e);
            throw e;
        }
    }

    /**
     * Get min value
     */
    public String getMin() {
        LogUtils.logAction(toString(), "Getting min value");
        try {
            String min = getAttribute("min");
            LogUtils.logSuccess(toString(), "Got min value: " + min);
            return min;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get min value", e);
            throw e;
        }
    }

    /**
     * Get max value
     */
    public String getMax() {
        LogUtils.logAction(toString(), "Getting max value");
        try {
            String max = getAttribute("max");
            LogUtils.logSuccess(toString(), "Got max value: " + max);
            return max;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get max value", e);
            throw e;
        }
    }

    /**
     * Get step value
     */
    public String getStep() {
        LogUtils.logAction(toString(), "Getting step value");
        try {
            String step = getAttribute("step");
            LogUtils.logSuccess(toString(), "Got step value: " + step);
            return step;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get step value", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            return String.format("Slider '%s' [%s] {value: %s, min: %s, max: %s}", 
                getName(), getLocator(), getValue(), getMin(), getMax());
        } catch (Exception e) {
            return String.format("Slider '%s' [%s]", getName(), getLocator());
        }
    }
}
