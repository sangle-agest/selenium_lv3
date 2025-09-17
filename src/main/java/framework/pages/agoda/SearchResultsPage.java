package framework.pages.agoda;

import com.google.gson.JsonObject;
import framework.base.BasePage;
import framework.elements.core.*;
import framework.utils.LocatorManager;
import framework.utils.LogUtils;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.$;

import java.time.Duration;

public class SearchResultsPage extends BasePage {
    
    // Locator file path and page name
    private static final String LOCATOR_FILE = "locators/agoda/agoda_locators.json";
    private static final String PAGE_NAME = "SearchResultsPage";
    
    // Locator manager and page locators
    private final LocatorManager locatorManager = LocatorManager.getInstance();
    private final JsonObject pageLocators;
    
    // Page elements initialized from JSON
    private final ElementCollection hotelItems;
    private final Button sortButton;
    private final Button priceAscendingOption;
    private final ElementCollection priceList;
    
    public SearchResultsPage() {
        super("Agoda Search Results Page");
        
        // Load page locators
        pageLocators = locatorManager.getPageLocators(PAGE_NAME, LOCATOR_FILE);
        
        // Initialize elements from JSON
        hotelItems = (ElementCollection) locatorManager.createElement(pageLocators, "hotelItems");
        sortButton = (Button) locatorManager.createElement(pageLocators, "sortButton");
        priceAscendingOption = (Button) locatorManager.createElement(pageLocators, "priceAscendingOption");
        priceList = (ElementCollection) locatorManager.createElement(pageLocators, "priceList");
    }
}
