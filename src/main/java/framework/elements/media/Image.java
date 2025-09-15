package framework.elements.media;

import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import com.codeborne.selenide.Selenide;

/**
 * Image element wrapper
 */
public class Image extends BaseElement {
    
    public Image(String locator, String name) {
        super(locator, name);
    }

    /**
     * Get image source URL
     */
    public String getSrc() {
        LogUtils.logAction(toString(), "Getting image source URL");
        try {
            String src = getAttribute("src");
            LogUtils.logSuccess(toString(), "Got image source: " + src);
            return src;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get image source URL", e);
            throw e;
        }
    }

    /**
     * Get image alt text
     */
    public String getAltText() {
        LogUtils.logAction(toString(), "Getting image alt text");
        try {
            String alt = getAttribute("alt");
            LogUtils.logSuccess(toString(), "Got image alt text: " + alt);
            return alt;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get image alt text", e);
            throw e;
        }
    }

    /**
     * Get image width
     */
    public int getWidth() {
        LogUtils.logAction(toString(), "Getting image width");
        try {
            int width = Integer.parseInt(getAttribute("width"));
            LogUtils.logSuccess(toString(), "Got image width: " + width);
            return width;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get image width", e);
            throw e;
        }
    }

    /**
     * Get image height
     */
    public int getHeight() {
        LogUtils.logAction(toString(), "Getting image height");
        try {
            int height = Integer.parseInt(getAttribute("height"));
            LogUtils.logSuccess(toString(), "Got image height: " + height);
            return height;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get image height", e);
            throw e;
        }
    }

    /**
     * Check if image is loaded
     */
    public boolean isLoaded() {
        LogUtils.logAction(toString(), "Checking if image is loaded");
        try {
            boolean loaded = element.isImage();
            LogUtils.logSuccess(toString(), "Image is " + (loaded ? "loaded" : "not loaded"));
            return loaded;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check if image is loaded", e);
            throw e;
        }
    }

    /**
     * Get natural width (actual image size)
     */
    public int getNaturalWidth() {
        LogUtils.logAction(toString(), "Getting natural image width");
        try {
            int naturalWidth = Integer.parseInt(Selenide.executeJavaScript("return arguments[0].naturalWidth", element).toString());
            LogUtils.logSuccess(toString(), "Got natural width: " + naturalWidth);
            return naturalWidth;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get natural width", e);
            throw e;
        }
    }

    /**
     * Get natural height (actual image size)
     */
    public int getNaturalHeight() {
        LogUtils.logAction(toString(), "Getting natural image height");
        try {
            int naturalHeight = Integer.parseInt(Selenide.executeJavaScript("return arguments[0].naturalHeight", element).toString());
            LogUtils.logSuccess(toString(), "Got natural height: " + naturalHeight);
            return naturalHeight;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get natural height", e);
            throw e;
        }
    }
    
    @Override
    public String toString() {
        try {
            return String.format("Image '%s' [%s] {src: %s, alt: %s, %dx%d}", 
                getName(), getLocator(), 
                getSrc().substring(getSrc().lastIndexOf("/") + 1), 
                getAltText(), getWidth(), getHeight());
        } catch (Exception e) {
            return String.format("Image '%s' [%s]", getName(), getLocator());
        }
    }
}
