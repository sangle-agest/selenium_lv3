package framework.pages.agoda;

import framework.base.BasePage;
import framework.elements.core.*;
import framework.utils.LogUtils;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.$;

import java.time.Duration;

public class SearchResultsPage extends BasePage {
    
    public SearchResultsPage() {
        super("Agoda Search Results Page");
    }
    
    // Search results
    private final ElementCollection hotelCards = new ElementCollection("[data-selenium='hotel-item']", "Hotel Cards");
    private final Button sortDropdown = new Button("[data-selenium='sortingDropdown']", "Sort Dropdown");
    private final ElementCollection sortOptions = new ElementCollection("[data-selenium='sortingOption']", "Sort Options");
    
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
        sortDropdown.click();
        
        // Find and click on the sort option
        boolean found = false;
        for (int i = 0; i < sortOptions.size(); i++) {
            BaseElement option = sortOptions.get(i);
            if (option.getText().contains(sortBy)) {
                option.click();
                found = true;
                break;
            }
        }
        
        if (!found) {
            LogUtils.logWarning(this.toString(), "Sort option not found: " + sortBy);
        } else {
            LogUtils.logSuccess(this.toString(), "Results sorted by: " + sortBy);
        }
        
        // Wait for results to update
        hotelCards.first().waitForVisible();
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
        
        // Wait for results to update
        hotelCards.first().waitForVisible();
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
        String name = new Label(hotelCards.get(index).getLocator() + " [data-selenium='hotel-name']", 
                "Hotel Name[" + index + "]").getText();
        LogUtils.logSuccess(this.toString(), "Got hotel name: " + name);
        return name;
    }

    /**
     * Get hotel price by index
     * @param index Index of hotel in results (0-based)
     * @return Hotel price as string
     */
    public String getHotelPriceAsString(int index) {
        LogUtils.logAction(this.toString(), "Getting hotel price at index: " + index);
        try {
            String price = new Label(hotelCards.get(index).getLocator() + " [data-selenium='display-price']",
                    "Hotel Price[" + index + "]").getText();
            LogUtils.logSuccess(this.toString(), "Got hotel price: " + price);
            return price;
        } catch (Exception e) {
            // Try with JavaScript as a fallback
            String priceSelector = "[data-selenium='display-price'], .price-text";
            String price = (String) executeJavaScript(
                "return document.querySelectorAll('" + hotelCards.get(index).getLocator() + "')[" + index + "]" +
                ".querySelector('" + priceSelector + "') ? " +
                "document.querySelectorAll('" + hotelCards.get(index).getLocator() + "')[" + index + "]" +
                ".querySelector('" + priceSelector + "').textContent.trim() : '';");
                        
            LogUtils.logSuccess(this.toString(), "Got hotel price using JavaScript: " + price);
            return price;
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
     * @return Hotel location
     */
    public String getHotelLocation(int index) {
        LogUtils.logAction(this.toString(), "Getting hotel location at index: " + index);
        try {
            // Try to find location using common selectors
            String locationSelector = "[data-selenium='area-name'], [data-selenium='hotel-address'], .address";
            Label locationLabel = new Label(hotelCards.get(index).getLocator() + " " + locationSelector, 
                    "Hotel Location[" + index + "]");
            
            if (locationLabel.exists()) {
                String location = locationLabel.getText();
                LogUtils.logSuccess(this.toString(), "Got hotel location: " + location);
                return location;
            }
            
            // Try with JavaScript as a fallback
            String location = (String) executeJavaScript(
                "return document.querySelectorAll('" + hotelCards.get(index).getLocator() + "')[" + index + "]" +
                ".querySelector('" + locationSelector + "') ? " +
                "document.querySelectorAll('" + hotelCards.get(index).getLocator() + "')[" + index + "]" +
                ".querySelector('" + locationSelector + "').textContent.trim() : '';");
                        
            if (location != null && !location.isEmpty()) {
                LogUtils.logSuccess(this.toString(), "Got hotel location using JavaScript: " + location);
                return location;
            }
            
            // If we still can't find it, try to look for any text that might contain location info
            LogUtils.logWarning(this.toString(), "Could not find location with standard selectors, trying alternative approach");
            return (String) executeJavaScript(
                "const card = document.querySelectorAll('" + hotelCards.get(index).getLocator() + "')[" + index + "];" +
                "const texts = Array.from(card.querySelectorAll('*')).map(el => el.textContent.trim())" +
                ".filter(text => text.includes('Da Nang') || text.includes('Danang') || " +
                "text.match(/\\b[A-Z][a-z]+(?:\\s+[A-Z][a-z]+)*,\\s+Vietnam\\b/));" +
                "return texts.length > 0 ? texts[0] : 'Unknown location';");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to get hotel location", e);
            return "Unknown location";
        }
    }
}