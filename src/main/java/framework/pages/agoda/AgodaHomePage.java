package framework.pages.agoda;

import com.google.gson.JsonObject;
import framework.base.BasePage;
import framework.elements.core.*;
import framework.utils.LocatorManager;
import framework.utils.LogUtils;
import com.codeborne.selenide.Selenide;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Agoda Home Page Object
 * Contains all the elements and methods specific to the Agoda home page
 * Uses JSON-based locator management
 */
public class AgodaHomePage extends BasePage {
    // Locator file path and page name
    private static final String LOCATOR_FILE = "locators/agoda/agoda_locators.json";
    private static final String PAGE_NAME = "AgodaHomePage";
    
    // Locator manager and page locators
    private final LocatorManager locatorManager = LocatorManager.getInstance();
    private final JsonObject pageLocators;
    
    // Page elements initialized from JSON
    private final TextBox searchBox;
    private final Button checkInButton;
    private final Button occupancyButton;
    private final Button searchButton;
    
    // Constructor with page name
    public AgodaHomePage() {
        super("Agoda Home Page");
        
        // Load page locators
        pageLocators = locatorManager.getPageLocators(PAGE_NAME, LOCATOR_FILE);
        
        // Initialize elements from JSON
        searchBox = (TextBox) locatorManager.createElement(pageLocators, "searchBox");
        checkInButton = (Button) locatorManager.createElement(pageLocators, "checkInButton");
        occupancyButton = (Button) locatorManager.createElement(pageLocators, "occupancyButton");
        searchButton = (Button) locatorManager.createElement(pageLocators, "searchButton");
    }

}
