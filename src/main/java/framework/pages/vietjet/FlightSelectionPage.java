package framework.pages.vietjet;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import framework.base.BasePage;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.*;

public class FlightSelectionPage extends BasePage {
    // Flight list components
    private final ElementsCollection departureFights = $$(".departure-flight-item");
    private final ElementsCollection returnFlights = $$(".return-flight-item");
    private final SelenideElement sortDropdown = $(".sort-dropdown");
    
    // Filter components
    private final SelenideElement priceFilterMin = $("#price-filter-min");
    private final SelenideElement priceFilterMax = $("#price-filter-max");
    private final SelenideElement timeFilterMorning = $("#time-morning");
    private final SelenideElement timeFilterAfternoon = $("#time-afternoon");
    private final SelenideElement timeFilterEvening = $("#time-evening");
    
    /**
     * Get number of departure flights
     * @return Number of departure flights found
     */
    public int getDepartureFlightCount() {
        return departureFights.size();
    }
    
    /**
     * Get number of return flights
     * @return Number of return flights found
     */
    public int getReturnFlightCount() {
        return returnFlights.size();
    }

    /**
     * Select departure flight by index
     * @param index Index of flight in the list (0-based)
     */
    public void selectDepartureFlight(int index) {
        waitForElementVisible(departureFights.get(index));
        scrollIntoView(departureFights.get(index));
        departureFights.get(index).click();
    }

    /**
     * Select return flight by index
     * @param index Index of flight in the list (0-based)
     */
    public void selectReturnFlight(int index) {
        waitForElementVisible(returnFlights.get(index));
        scrollIntoView(returnFlights.get(index));
        returnFlights.get(index).click();
    }

    /**
     * Filter flights by price range
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     */
    public void filterByPrice(int minPrice, int maxPrice) {
        priceFilterMin.setValue(String.valueOf(minPrice));
        priceFilterMax.setValue(String.valueOf(maxPrice));
    }

    /**
     * Filter flights by time of day
     * @param timeOfDay "morning", "afternoon", or "evening"
     */
    public void filterByTime(String timeOfDay) {
        switch (timeOfDay.toLowerCase()) {
            case "morning":
                timeFilterMorning.click();
                break;
            case "afternoon":
                timeFilterAfternoon.click();
                break;
            case "evening":
                timeFilterEvening.click();
                break;
        }
    }

    /**
     * Get departure flight price
     * @param index Index of flight in the list (0-based)
     * @return Price as string
     */
    public String getDepartureFlightPrice(int index) {
        return departureFights.get(index).$(".flight-price").getText();
    }

    /**
     * Get return flight price
     * @param index Index of flight in the list (0-based)
     * @return Price as string
     */
    public String getReturnFlightPrice(int index) {
        return returnFlights.get(index).$(".flight-price").getText();
    }
}
