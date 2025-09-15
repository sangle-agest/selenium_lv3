package framework.elements.media;

import framework.elements.core.BaseElement;
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
        return getAttribute("src");
    }

    /**
     * Get image alt text
     */
    public String getAltText() {
        return getAttribute("alt");
    }

    /**
     * Get image width
     */
    public int getWidth() {
        return Integer.parseInt(getAttribute("width"));
    }

    /**
     * Get image height
     */
    public int getHeight() {
        return Integer.parseInt(getAttribute("height"));
    }

    /**
     * Check if image is loaded
     */
    public boolean isLoaded() {
        return element.isImage();
    }

    /**
     * Get natural width (actual image size)
     */
    public int getNaturalWidth() {
        return Integer.parseInt(Selenide.executeJavaScript("return arguments[0].naturalWidth", element).toString());
    }

    /**
     * Get natural height (actual image size)
     */
    public int getNaturalHeight() {
        return Integer.parseInt(Selenide.executeJavaScript("return arguments[0].naturalHeight", element).toString());
    }
}
