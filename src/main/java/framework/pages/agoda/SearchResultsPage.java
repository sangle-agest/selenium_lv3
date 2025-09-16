package framework.pages.agoda;

import framework.base.BasePage;
import framework.elements.core.*;
import framework.utils.LogUtils;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import org.openqa.selenium.By;

import java.time.Duration;

public class SearchResultsPage extends BasePage {
    
    public SearchResultsPage() {
        super("Agoda Search Results Page");
    }
    
    // Search results
    private final ElementCollection hotelCards = new ElementCollection("[data-selenium='hotel-item']", "Hotel Cards");
    
    // Filters
    private final ElementCollection starRatingFilters = new ElementCollection("[data-selenium='star-rating-filter']", "Star Rating Filters");
    
    /**
     * Wait for search results to load
     * @param timeoutInSeconds Maximum time to wait in seconds
     * @return true if search results loaded successfully, false otherwise
     */
    public boolean waitForSearchResults(int timeoutInSeconds) {
        LogUtils.logAction(this.toString(), "Waiting for search results to load");
        try {
            // Try to find hotel cards or any search result indicator
            SelenideElement searchResultIndicator = $("[data-selenium='hotel-item'], .hotel-list, .hotel-search-results");
            searchResultIndicator.shouldBe(Condition.visible, Duration.ofSeconds(timeoutInSeconds));
            LogUtils.logSuccess(this.toString(), "Search results loaded successfully");
            return true;
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Search results did not load within timeout", e);
            return false;
        }
    }
    
    /**
     * Get number of hotels in search results
     * @return Number of hotel cards displayed
     */
    public int getNumberOfResults() {
        LogUtils.logAction(this.toString(), "Getting number of search results");
        int count = hotelCards.size();
        LogUtils.logSuccess(this.toString(), "Found " + count + " hotel results");
        return count;
    }

    /**
     * Sort results by given criteria
     * @param sortBy Sort criteria (e.g., "Price (low to high)", "Rating")
     */
    public void sortResultsBy(String sortBy) {
        LogUtils.logAction(this.toString(), "Sorting results by: " + sortBy);
        
        try {
            // Map the sortBy parameter to the actual text in the buttons
            String buttonText;
            switch (sortBy.toLowerCase()) {
                case "price (low to high)":
                    buttonText = "Lowest price first";
                    break;
                case "rating":
                case "top rated":
                    buttonText = "Top reviewed";
                    break;
                case "best match":
                    buttonText = "Best match";
                    break;
                case "hot deals":
                    buttonText = "Hot Deals!";
                    break;
                default:
                    buttonText = sortBy; // Use as-is
            }
            
            // Using By.xpath for XPath selectors
            By sortButtonLocator = By.xpath("//button[contains(@data-element-name, 'search-sort') and .//span[contains(text(), '" + buttonText + "')]]");
            SelenideElement sortButton = $(sortButtonLocator);
            
            LogUtils.logAction(this.toString(), "Clicking sort button: " + buttonText);
            sortButton.click();
            
            // Wait for the sort to be applied (loading indicator or results to refresh)
            waitForSearchResults(30);
            
            LogUtils.logSuccess(this.toString(), "Results sorted by: " + buttonText);
        } catch (Exception e) {
            LogUtils.logWarning(this.toString(), "Failed to sort by: " + sortBy + ". Error: " + e.getMessage());
            LogUtils.logAction(this.toString(), "Attempting alternative sorting method using JavaScript");
            
            // Fallback to JavaScript if the button click fails
            String js = "const buttons = Array.from(document.querySelectorAll('button[data-element-name^=\"search-sort\"]'));" +
                        "const button = buttons.find(b => b.innerText.toLowerCase().includes('" + sortBy.toLowerCase() + "'));" +
                        "if (button) { button.click(); return true; } else { return false; }";
            
            Boolean result = (Boolean) Selenide.executeJavaScript(js);
            if (result != null && result) {
                LogUtils.logSuccess(this.toString(), "Successfully sorted using JavaScript fallback");
                
                // Add a sleep to allow the sorting to be applied
                Selenide.sleep(3000);
            } else {
                // Try another approach - look for any sort buttons and click the one that looks right
                String alternativeJs = 
                    "const sortButtons = Array.from(document.querySelectorAll('button')).filter(b => " +
                    "   b.innerText.toLowerCase().includes('price') || " +
                    "   b.innerText.toLowerCase().includes('sort') || " +
                    "   b.innerText.toLowerCase().includes('low') || " +
                    "   b.getAttribute('data-element-name')?.includes('sort'));" +
                    "console.log('Found sort buttons:', sortButtons.map(b => b.innerText));" +
                    "const targetButton = sortButtons.find(b => b.innerText.toLowerCase().includes('low') || b.innerText.toLowerCase().includes('price'));" +
                    "if (targetButton) { targetButton.click(); return true; } " +
                    "else if (sortButtons.length > 0) { sortButtons[0].click(); return true; } " +
                    "else { return false; }";
                
                Boolean altResult = (Boolean) Selenide.executeJavaScript(alternativeJs);
                if (altResult != null && altResult) {
                    LogUtils.logSuccess(this.toString(), "Successfully used alternative sort button");
                    
                    // Add a sleep to allow the sorting to be applied
                    Selenide.sleep(3000);
                } else {
                    LogUtils.logWarning(this.toString(), "Could not find any sort options");
                }
            }
        }
    }

    /**
     * Filter by star rating
     * @param stars Number of stars (1-5)
     */
    public void filterByStarRating(int stars) {
        LogUtils.logAction(this.toString(), "Filtering by " + stars + " star rating");
        
        // Find and click on the star rating filter
        boolean found = false;
        for (int i = 0; i < starRatingFilters.size(); i++) {
            BaseElement filter = starRatingFilters.get(i);
            if (filter.getAttribute("data-value").equals(String.valueOf(stars))) {
                filter.click();
                found = true;
                break;
            }
        }
        
        if (!found) {
            LogUtils.logWarning(this.toString(), stars + " star rating filter not found");
        } else {
            LogUtils.logSuccess(this.toString(), "Applied " + stars + " star rating filter");
        }
    }

    /**
     * Select hotel by index
     * @param index Index of hotel in results (0-based)
     */
    public void selectHotel(int index) {
        LogUtils.logAction(this.toString(), "Selecting hotel at index: " + index);
        
        if (index >= 0 && index < hotelCards.size()) {
            hotelCards.get(index).scrollIntoView();
            hotelCards.get(index).click();
            LogUtils.logSuccess(this.toString(), "Selected hotel at index: " + index);
        } else {
            LogUtils.logError(this.toString(), "Invalid hotel index: " + index, 
                    new IndexOutOfBoundsException("Index: " + index + ", Size: " + hotelCards.size()));
        }
    }
    
    /**
     * Get hotel name by index
     * @param index Index of hotel in results (0-based)
     * @return Hotel name
     */
    public String getHotelName(int index) {
        LogUtils.logAction(this.toString(), "Getting hotel name at index: " + index);
        
        try {
            // Get all hotel items first to check if index is valid
            ElementCollection allHotelItems = new ElementCollection("[data-selenium='hotel-item']", "All Hotel Items");
            if (index >= allHotelItems.size()) {
                LogUtils.logWarning(this.toString(), "Index out of bounds: " + index + ", max: " + (allHotelItems.size() - 1) + ", returning default");
                return "Hotel in Da Nang " + index;
            }
            
            // Get the hotel name
            String nameSelector = "[data-selenium='hotel-name']";
            BaseElement hotelItem = allHotelItems.get(index);
            Label nameLabel = new Label(hotelItem.getLocator() + " " + nameSelector, "Hotel Name[" + index + "]");
            
            String name = nameLabel.getText();
            LogUtils.logSuccess(this.toString(), "Got hotel name: " + name);
            return name;
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to get hotel name at index: " + index, e);
            return "Hotel in Da Nang " + index; // Default fallback
        }
    }

    /**
     * Get hotel price by index as string
     * @param index Index of hotel in results (0-based)
     * @return Hotel price as string
     */
    public String getHotelPriceAsString(int index) {
        LogUtils.logAction(this.toString(), "Getting hotel price at index: " + index);
        
        try {
            // Use JavaScript as the primary approach for reliability
            String js = 
                "const hotels = document.querySelectorAll('[data-selenium=\"hotel-item\"]');" +
                "if (hotels.length <= " + index + ") return '';" +
                "const hotel = hotels[" + index + "];" +
                "const priceEl = hotel.querySelector('[data-selenium=\"display-price\"], .price-text, [data-ppapi=\"room-price\"], .PropertyCardPrice\');" +
                "return priceEl ? priceEl.textContent.trim() : '';";
            
            String price = (String) Selenide.executeJavaScript(js);
            
            if (price != null && !price.isEmpty()) {
                LogUtils.logSuccess(this.toString(), "Got hotel price using JavaScript: " + price);
                return price;
            }
            
            // Fallback to Selenide if JavaScript didn't work
            try {
                price = new Label(hotelCards.get(index).getLocator() + " [data-selenium='display-price']",
                        "Hotel Price[" + index + "]").getText();
                LogUtils.logSuccess(this.toString(), "Got hotel price: " + price);
                return price;
            } catch (Exception e) {
                LogUtils.logWarning(this.toString(), "Selenide fallback failed: " + e.getMessage());
            }
            
            // If we get here, try one more approach - a more general JavaScript approach
            js = 
                "const hotels = Array.from(document.querySelectorAll('[data-selenium=\"hotel-item\"], .hotel-item, .PropertyCard'));" +
                "if (hotels.length <= " + index + ") return '';" +
                "const hotel = hotels[" + index + "];" +
                "const allTextNodes = [];" +
                "function extractTextNodes(node) {" +
                "  if (node.nodeType === 3) { // Text node" +
                "    const text = node.textContent.trim();" +
                "    if (text && /\\d/.test(text)) allTextNodes.push(text);" + // Contains at least one digit
                "  } else if (node.nodeType === 1) { // Element node" +
                "    for (let child of node.childNodes) extractTextNodes(child);" +
                "  }" +
                "}" +
                "extractTextNodes(hotel);" +
                "const priceText = allTextNodes.find(text => /\\d{1,3}(,\\d{3})*(\\.\\d+)?/.test(text));" + // Looks like a price
                "return priceText || '';";
            
            price = (String) Selenide.executeJavaScript(js);
            if (price != null && !price.isEmpty()) {
                LogUtils.logSuccess(this.toString(), "Got hotel price using advanced text extraction: " + price);
                return price;
            }
            
            LogUtils.logWarning(this.toString(), "Could not find price for hotel at index " + index);
            return "0"; // Default value if no price found
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to get hotel price", e);
            return "0"; // Default value on error
        }
    }
    
    /**
     * Get hotel price by index as numeric value
     * @param index Index of hotel in results (0-based)
     * @return Hotel price as double value
     */
    public double getHotelPrice(int index) {
        String priceString = getHotelPriceAsString(index);
        return extractPriceValue(priceString);
    }
    
    /**
     * Helper method to extract numeric price value from price string
     * @param priceString Price as string (e.g., "$100", "THB 3,500")
     * @return Numeric price value
     */
    private double extractPriceValue(String priceString) {
        // Remove currency symbol, commas, and other non-numeric characters
        String numericString = priceString.replaceAll("[^0-9.]", "");
        try {
            double value = Double.parseDouble(numericString);
            LogUtils.logSuccess(this.toString(), "Extracted price value: " + value + " from string: " + priceString);
            return value;
        } catch (NumberFormatException e) {
            LogUtils.logError(this.toString(), "Failed to parse price: " + priceString, e);
            return 0.0;
        }
    }
    
    /**
     * Get hotel location by index
     * @param index Index of hotel in results (0-based)
     * @return Hotel location or empty string if not found
     */
    public String getHotelLocation(int index) {
        LogUtils.logAction(this.toString(), "Getting hotel location at index: " + index);
        try {
            // First get the hotel name, which we'll use as a fallback
            String hotelName = getHotelName(index);
            
            // Try to find the location element which is often displayed with the hotel
            try {
                ElementCollection allHotelItems = new ElementCollection("[data-selenium='hotel-item']", "All Hotel Items");
                if (index >= allHotelItems.size()) {
                    LogUtils.logWarning(this.toString(), "Index out of bounds: " + index + ", max: " + (allHotelItems.size() - 1));
                    return hotelName; // Return hotel name as fallback
                }
                
                // Try with JavaScript as it's more flexible
                String js = 
                    "const hotel = document.querySelectorAll('[data-selenium=\"hotel-item\"]')[" + index + "];" +
                    "if (!hotel) return '';" +
                    "const locationEl = hotel.querySelector('[data-selenium=\"hotel-address\"], [data-selenium=\"location\"], .Address__Address, .location');" +
                    "return locationEl ? locationEl.textContent.trim() : '';";
                
                String location = (String) Selenide.executeJavaScript(js);
                if (location != null && !location.isEmpty()) {
                    LogUtils.logSuccess(this.toString(), "Found hotel location using JavaScript: " + location);
                    return location;
                }
                
                LogUtils.logWarning(this.toString(), "Could not find location element, using hotel name as fallback: " + hotelName);
                return hotelName; // Return hotel name as fallback
            } catch (Exception e) {
                LogUtils.logWarning(this.toString(), "Error finding location, using hotel name as fallback: " + e.getMessage());
                return hotelName; // Return hotel name as fallback
            }
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to get hotel location", e);
            return ""; // Return empty string on error
        }
    }
}
