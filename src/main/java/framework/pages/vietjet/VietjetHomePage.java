package framework.pages.vietjet;

import com.codeborne.selenide.SelenideElement;
import framework.base.BasePage;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Condition;

public class VietjetHomePage extends BasePage {
    // Flight search components
    private final SelenideElement fromInput = $("#select-origin");
    private final SelenideElement toInput = $("#select-destination");
    private final SelenideElement departDateInput = $("#select-depart");
    private final SelenideElement returnDateInput = $("#select-return");
    private final SelenideElement searchButton = $("#btn-search-flight");
    private final SelenideElement roundTripRadio = $("#round-trip");
    private final SelenideElement oneWayRadio = $("#one-way");

    // Passenger selection
    private final SelenideElement passengerDropdown = $("#passenger-dropdown");
    private final SelenideElement adultPlusButton = $("#adult-plus");
    private final SelenideElement adultMinusButton = $("#adult-minus");
    private final SelenideElement childPlusButton = $("#child-plus");
    private final SelenideElement childMinusButton = $("#child-minus");
    private final SelenideElement infantPlusButton = $("#infant-plus");
    private final SelenideElement infantMinusButton = $("#infant-minus");

    /**
     * Select flight type (Round trip or One way)
     * @param isRoundTrip true for round trip, false for one way
     */
    public void selectFlightType(boolean isRoundTrip) {
        if (isRoundTrip) {
            roundTripRadio.click();
        } else {
            oneWayRadio.click();
        }
    }

    /**
     * Set departure and arrival locations
     * @param from Departure city/airport
     * @param to Arrival city/airport
     */
    public void setLocations(String from, String to) {
        fromInput.setValue(from);
        toInput.setValue(to);
    }

    /**
     * Set departure and return dates
     * @param departDate Departure date in DD/MM/YYYY format
     * @param returnDate Return date in DD/MM/YYYY format (optional)
     */
    public void setDates(String departDate, String returnDate) {
        departDateInput.setValue(departDate);
        if (returnDate != null) {
            returnDateInput.setValue(returnDate);
        }
    }

    /**
     * Set passenger counts
     * @param adults Number of adult passengers
     * @param children Number of child passengers
     * @param infants Number of infant passengers
     */
    public void setPassengers(int adults, int children, int infants) {
        passengerDropdown.click();
        
        // Set adults
        setPassengerCount(adults, adultPlusButton, adultMinusButton);
        
        // Set children
        setPassengerCount(children, childPlusButton, childMinusButton);
        
        // Set infants
        setPassengerCount(infants, infantPlusButton, infantMinusButton);
    }

    private void setPassengerCount(int target, SelenideElement plusButton, SelenideElement minusButton) {
        while (getCurrentCount(plusButton) < target) {
            plusButton.click();
        }
        while (getCurrentCount(plusButton) > target) {
            minusButton.click();
        }
    }

    private int getCurrentCount(SelenideElement button) {
        return Integer.parseInt(button.parent().$(".passenger-count").getText());
    }

    /**
     * Click search button to find flights
     */
    public void searchFlights() {
        searchButton.click();
    }
}
