package framework.pages.vietjet;

import com.google.gson.JsonObject;
import framework.base.BasePage;
import framework.elements.core.*;
import framework.utils.LocatorManager;
import framework.utils.LogUtils;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

/**
 * Vietjet Home Page Object using JSON-based locators
 * Contains all the elements and methods specific to the Vietjet home page
 */
public class VietjetHomePageJSON extends BasePage {
    // Locator file path and page name
    private static final String LOCATOR_FILE = "locators/vietjet/vietjet_locators.json";
    private static final String PAGE_NAME = "VietjetHomePage";
    
    // Locator manager and page locators
    private final LocatorManager locatorManager = LocatorManager.getInstance();
    private final JsonObject pageLocators;
    
    // Page elements initialized from JSON
    private final TextBox originInput;
    private final TextBox destinationInput;
    private final Button departDatePicker;
    private final Button returnDatePicker;
    private final Button searchButton;
    private final Button oneWayRadio;
    private final Button roundTripRadio;
    
    // Constructor with page name
    public VietjetHomePageJSON() {
        super("Vietjet Home Page");
        
        // Load page locators
        pageLocators = locatorManager.getPageLocators(PAGE_NAME, LOCATOR_FILE);
        
        // Initialize elements from JSON
        originInput = (TextBox) locatorManager.createElement(pageLocators, "originInput");
        destinationInput = (TextBox) locatorManager.createElement(pageLocators, "destinationInput");
        departDatePicker = (Button) locatorManager.createElement(pageLocators, "departDatePicker");
        returnDatePicker = (Button) locatorManager.createElement(pageLocators, "returnDatePicker");
        searchButton = (Button) locatorManager.createElement(pageLocators, "searchButton");
        oneWayRadio = (Button) locatorManager.createElement(pageLocators, "oneWayRadio");
        roundTripRadio = (Button) locatorManager.createElement(pageLocators, "roundTripRadio");
    }
    
    /**
     * Select flight type (one way or round trip)
     * @param isRoundTrip true for round trip, false for one way
     */
    public void selectFlightType(boolean isRoundTrip) {
        LogUtils.logAction(this.toString(), "Selecting flight type: " + (isRoundTrip ? "Round Trip" : "One Way"));
        
        try {
            if (isRoundTrip) {
                roundTripRadio.click();
            } else {
                oneWayRadio.click();
            }
            LogUtils.logSuccess(this.toString(), "Selected flight type: " + (isRoundTrip ? "Round Trip" : "One Way"));
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to select flight type", e);
            throw e;
        }
    }
    
    /**
     * Set origin and destination
     * @param origin Origin airport code
     * @param destination Destination airport code
     */
    public void setRoute(String origin, String destination) {
        LogUtils.logAction(this.toString(), "Setting route: " + origin + " to " + destination);
        
        try {
            originInput.setText(origin);
            destinationInput.setText(destination);
            LogUtils.logSuccess(this.toString(), "Set route: " + origin + " to " + destination);
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to set route", e);
            throw e;
        }
    }
    
    /**
     * Set departure date
     * @param departDate Departure date in the format expected by the application
     */
    public void setDepartureDate(String departDate) {
        LogUtils.logAction(this.toString(), "Setting departure date: " + departDate);
        
        try {
            departDatePicker.click();
            // Select the date (simplified for example)
            // In real implementation, would need to interact with the date picker
            Selenide.executeJavaScript("document.getElementById('departDate').value = '" + departDate + "';");
            LogUtils.logSuccess(this.toString(), "Set departure date: " + departDate);
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to set departure date", e);
            throw e;
        }
    }
    
    /**
     * Set return date (for round trip)
     * @param returnDate Return date in the format expected by the application
     */
    public void setReturnDate(String returnDate) {
        LogUtils.logAction(this.toString(), "Setting return date: " + returnDate);
        
        try {
            returnDatePicker.click();
            // Select the date (simplified for example)
            // In real implementation, would need to interact with the date picker
            Selenide.executeJavaScript("document.getElementById('returnDate').value = '" + returnDate + "';");
            LogUtils.logSuccess(this.toString(), "Set return date: " + returnDate);
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to set return date", e);
            throw e;
        }
    }
    
    /**
     * Click search to find flights
     */
    public void search() {
        LogUtils.logAction(this.toString(), "Clicking search button");
        
        try {
            searchButton.click();
            LogUtils.logSuccess(this.toString(), "Search button clicked successfully");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to click search button", e);
            throw e;
        }
    }
    
    /**
     * Complete search flow for one way trip
     * @param origin Origin airport code
     * @param destination Destination airport code
     * @param departDate Departure date
     */
    public void searchOneWay(String origin, String destination, String departDate) {
        selectFlightType(false);
        setRoute(origin, destination);
        setDepartureDate(departDate);
        search();
    }
    
    /**
     * Complete search flow for round trip
     * @param origin Origin airport code
     * @param destination Destination airport code
     * @param departDate Departure date
     * @param returnDate Return date
     */
    public void searchRoundTrip(String origin, String destination, String departDate, String returnDate) {
        selectFlightType(true);
        setRoute(origin, destination);
        setDepartureDate(departDate);
        setReturnDate(returnDate);
        search();
    }
}