package framework.pages.agoda;

import framework.base.BasePage;
import framework.elements.core.*;
import framework.elements.dropdown.AutoCompleteBox;
import framework.elements.control.Counter;

public class AgodaHomePage extends BasePage {
    // Search components
    private final TextBox searchBox = new TextBox("[data-selenium='searchText']", "Search Box");
    private final Button searchButton = new Button("[data-selenium='searchButton']", "Search Button");
    private final AutoCompleteBox destination = new AutoCompleteBox("[data-selenium='autoCompleteList']", "[data-selenium='autoCompleteList'] li", "Destination");
    
    // Date picker components
    private final Button checkInButton = new Button("[data-selenium='checkInButton']", "Check-in Button");
    private final Button checkOutButton = new Button("[data-selenium='checkOutButton']", "Check-out Button");
    private final ElementCollection datePickerDays = new ElementCollection("[data-selenium='calendar-day']", "Calendar Days");
    
    // Room occupancy components
    private final Button occupancyButton = new Button("[data-selenium='occupancyButton']", "Occupancy Button");
    private final Counter adultCounter = new Counter(
        "[data-selenium='occupancyAdultPlus']",
        "[data-selenium='occupancyAdultMinus']",
        "[data-selenium='occupancyButton']",
        "data-adults",
        "Adult Count"
    );
    private final Counter childCounter = new Counter(
        "[data-selenium='occupancyChildPlus']",
        "[data-selenium='occupancyChildMinus']",
        "[data-selenium='occupancyButton']",
        "data-children",
        "Child Count"
    );
    private final Button occupancyDoneButton = new Button("[data-selenium='occupancyDoneButton']", "Occupancy Done Button");

    /**
     * Search for a destination
     * @param destinationText Name of the destination
     */
    public void searchDestination(String destinationText) {
        destination.typeAndSelectFirst(destinationText);
    }

    /**
     * Set check-in and check-out dates
     * @param checkInOffset Days from today for check-in
     * @param checkOutOffset Days from today for check-out
     */
    public void setDates(int checkInOffset, int checkOutOffset) {
        checkInButton.click();
        datePickerDays.get(checkInOffset).click();
        datePickerDays.get(checkOutOffset).click();
    }

    /**
     * Set room occupancy
     * @param adults Number of adults
     * @param children Number of children
     */
    public void setOccupancy(int adults, int children) {
        occupancyButton.click();
        
        // Set adults and children count
        adultCounter.setValue(adults);
        childCounter.setValue(children);

        occupancyDoneButton.click();
    }

    /**
     * Perform search with current criteria
     */
    public void clickSearch() {
        searchButton.click();
    }

    /**
     * Get current adult count
     */
    public int getAdultCount() {
        return adultCounter.getValue();
    }

    /**
     * Get current child count
     */
    public int getChildCount() {
        return childCounter.getValue();
    }
}
