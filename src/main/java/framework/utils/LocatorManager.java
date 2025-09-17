package framework.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import framework.elements.core.Button;
import framework.elements.core.Element;
import framework.elements.core.ElementCollection;
import framework.elements.core.TextBox;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages locators from JSON files to provide element definitions
 * for page objects.
 */
public class LocatorManager {

    private static final Map<String, JsonObject> pagesCache = new HashMap<>();
    private static LocatorManager instance;

    private LocatorManager() {
        // Private constructor for singleton
    }

    /**
     * Get singleton instance
     */
    public static synchronized LocatorManager getInstance() {
        if (instance == null) {
            instance = new LocatorManager();
        }
        return instance;
    }

    /**
     * Get a page's locator definitions
     * @param pageName Name of the page as defined in the JSON file
     * @param filePath Path to the JSON file (relative to resources)
     * @return JsonObject with the page's locators
     */
    public JsonObject getPageLocators(String pageName, String filePath) {
        String cacheKey = filePath + "#" + pageName;
        
        if (pagesCache.containsKey(cacheKey)) {
            return pagesCache.get(cacheKey);
        }
        
        try {
            // Load the JSON file from resources
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                // If not found in resources, try as file path
                JsonObject jsonObject = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();
                JsonObject pageObject = jsonObject.getAsJsonObject(pageName);
                if (pageObject != null) {
                    pagesCache.put(cacheKey, pageObject);
                    return pageObject;
                }
                LogUtils.logError(this.toString(), "Page '" + pageName + "' not found in " + filePath, null);
                return new JsonObject();
            } else {
                // Parse from resources
                Reader reader = new InputStreamReader(inputStream);
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                JsonObject pageObject = jsonObject.getAsJsonObject(pageName);
                if (pageObject != null) {
                    pagesCache.put(cacheKey, pageObject);
                    return pageObject;
                }
                LogUtils.logError(this.toString(), "Page '" + pageName + "' not found in " + filePath, null);
                return new JsonObject();
            }
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to load locators for " + pageName + " from " + filePath, e);
            return new JsonObject();
        }
    }

    /**
     * Create an element from the locator definition
     * @param pageLocators JsonObject with the page's locators
     * @param elementName Name of the element in the JSON
     * @return The appropriate element instance
     */
    public Object createElement(JsonObject pageLocators, String elementName) {
        try {
            JsonObject elementDef = pageLocators.getAsJsonObject(elementName);
            if (elementDef == null) {
                LogUtils.logError(this.toString(), "Element '" + elementName + "' not found in page definition", null);
                return null;
            }
            
            String locator = elementDef.get("locator").getAsString();
            String name = elementDef.has("name") ? elementDef.get("name").getAsString() : elementName;
            String type = elementDef.has("type") ? elementDef.get("type").getAsString() : "Element";
            
            // Create the appropriate element type
            switch (type) {
                case "Button":
                    return new Button(locator, name);
                case "TextBox":
                    return new TextBox(locator, name);
                case "ElementCollection":
                    return new ElementCollection(locator, name);
                default:
                    return new Element(locator, name);
            }
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to create element '" + elementName + "'", e);
            return null;
        }
    }

    /**
     * Get string locator from the page definition
     * @param pageLocators JsonObject with the page's locators
     * @param elementName Name of the element in the JSON
     * @return The locator string
     */
    public String getLocator(JsonObject pageLocators, String elementName) {
        try {
            JsonObject elementDef = pageLocators.getAsJsonObject(elementName);
            if (elementDef == null) {
                LogUtils.logError(this.toString(), "Element '" + elementName + "' not found in page definition", null);
                return "";
            }
            
            return elementDef.get("locator").getAsString();
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to get locator for '" + elementName + "'", e);
            return "";
        }
    }
    
    @Override
    public String toString() {
        return "LocatorManager";
    }
}