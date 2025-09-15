package framework.pages.agoda;

import framework.base.BasePage;
import framework.elements.core.*;
import framework.utils.LogUtils;

public class SearchResultsPage extends BasePage {
    // Search results
    private final ElementCollection hotelCards = new ElementCollection("[data-selenium='hotel-item']", "Hotel Cards");
    private final Button sortDropdown = new Button("[data-selenium='sortingDropdown']", "Sort Dropdown");
    private final ElementCollection sortOptions = new ElementCollection("[data-selenium='sortingOption']", "Sort Options");
    
    // Filters
    private final ElementCollection starRatingFilters = new ElementCollection("[data-selenium='star-rating-filter']", "Star Rating Filters");
    
    /**
     * Get number of hotels in search results
     * @return Number of hotel cards displayed
     */
    public int getNumberOfResults() {
        LogUtils.logAction("SearchResultsPage", "Getting number of search results");
        int count = hotelCards.size();
        LogUtils.logSuccess("SearchResultsPage", "Found " + count + " hotel results");
        return count;
    }

    /**
     * Sort results by given criteria
     * @param sortBy Sort criteria (e.g., "Price (low to high)", "Rating")
     */
    public void sortResultsBy(String sortBy) {
        LogUtils.logAction("SearchResultsPage", "Sorting results by: " + sortBy);
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
            LogUtils.logWarning("SearchResultsPage", "Sort option not found: " + sortBy);
        } else {
            LogUtils.logSuccess("SearchResultsPage", "Results sorted by: " + sortBy);
        }
        
        // Wait for results to update
        hotelCards.first().waitForVisible();
    }

    /**
     * Filter by star rating
     * @param stars Number of stars (1-5)
     */
    public void filterByStarRating(int stars) {
        LogUtils.logAction("SearchResultsPage", "Filtering by " + stars + " star rating");
        
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
            LogUtils.logWarning("SearchResultsPage", stars + " star rating filter not found");
        } else {
            LogUtils.logSuccess("SearchResultsPage", "Applied " + stars + " star rating filter");
        }
        
        // Wait for results to update
        hotelCards.first().waitForVisible();
    }

    /**
     * Select hotel by index
     * @param index Index of hotel in results (0-based)
     */
    public void selectHotel(int index) {
        LogUtils.logAction("SearchResultsPage", "Selecting hotel at index: " + index);
        
        if (index >= 0 && index < hotelCards.size()) {
            hotelCards.get(index).scrollIntoView();
            hotelCards.get(index).click();
            LogUtils.logSuccess("SearchResultsPage", "Selected hotel at index: " + index);
        } else {
            LogUtils.logError("SearchResultsPage", "Invalid hotel index: " + index, 
                    new IndexOutOfBoundsException("Index: " + index + ", Size: " + hotelCards.size()));
        }
    }

    /**
     * Get hotel name by index
     * @param index Index of hotel in results (0-based)
     * @return Hotel name
     */
    public String getHotelName(int index) {
        LogUtils.logAction("SearchResultsPage", "Getting hotel name at index: " + index);
        String name = new Label(hotelCards.get(index).getLocator() + " [data-selenium='hotel-name']", 
                "Hotel Name[" + index + "]").getText();
        LogUtils.logSuccess("SearchResultsPage", "Got hotel name: " + name);
        return name;
    }

    /**
     * Get hotel price by index
     * @param index Index of hotel in results (0-based)
     * @return Hotel price as string
     */
    public String getHotelPrice(int index) {
        LogUtils.logAction("SearchResultsPage", "Getting hotel price at index: " + index);
        String price = new Label(hotelCards.get(index).getLocator() + " [data-selenium='display-price']", 
                "Hotel Price[" + index + "]").getText();
        LogUtils.logSuccess("SearchResultsPage", "Got hotel price: " + price);
        return price;
    }
}
