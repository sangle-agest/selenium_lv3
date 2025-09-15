package framework.pages.agoda;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import framework.base.BasePage;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.*;

public class SearchResultsPage extends BasePage {
    // Search results
    private final ElementsCollection hotelCards = $$("[data-selenium='hotelCard']");
    private final SelenideElement sortDropdown = $("[data-selenium='sortDropdown']");
    private final ElementsCollection sortOptions = $$("[data-selenium='sortOption']");
    
    // Filters
    private final ElementsCollection starRatingFilters = $$("[data-selenium='starRatingFilter']");
    
    /**
     * Get number of hotels in search results
     * @return Number of hotel cards displayed
     */
    public int getNumberOfResults() {
        return hotelCards.size();
    }

    /**
     * Sort results by given criteria
     * @param sortBy Sort criteria (e.g., "Price (low to high)", "Rating")
     */
    public void sortResultsBy(String sortBy) {
        sortDropdown.click();
        sortOptions.find(text(sortBy)).click();
        // Wait for results to update
        hotelCards.first().shouldBe(visible);
    }

    /**
     * Filter by star rating
     * @param stars Number of stars (1-5)
     */
    public void filterByStarRating(int stars) {
        starRatingFilters.find(attribute("data-stars", String.valueOf(stars))).click();
        // Wait for results to update
        hotelCards.first().shouldBe(visible);
    }

    /**
     * Select hotel by index
     * @param index Index of hotel in results (0-based)
     */
    public void selectHotel(int index) {
        if (index >= 0 && index < hotelCards.size()) {
            scrollIntoView(hotelCards.get(index));
            hotelCards.get(index).click();
        }
    }

    /**
     * Get hotel name by index
     * @param index Index of hotel in results (0-based)
     * @return Hotel name
     */
    public String getHotelName(int index) {
        return hotelCards.get(index).$("[data-selenium='hotelName']").getText();
    }

    /**
     * Get hotel price by index
     * @param index Index of hotel in results (0-based)
     * @return Hotel price as string
     */
    public String getHotelPrice(int index) {
        return hotelCards.get(index).$("[data-selenium='hotelPrice']").getText();
    }
}
