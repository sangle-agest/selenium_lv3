package framework.pages.agoda;

import framework.base.BasePage;
import framework.elements.core.*;

public class HotelDetailsPage extends BasePage {
    // Hotel information
    private final Label hotelName = new Label("[data-selenium='hotelName']", "Hotel Name");
    private final Label hotelAddress = new Label("[data-selenium='hotelAddress']", "Hotel Address");
    private final Label hotelRating = new Label("[data-selenium='hotelRating']", "Hotel Rating");
    
    // Room selection
    private final ElementCollection roomTypes = new ElementCollection("[data-selenium='roomType']", "Room Types");
    private final ElementCollection roomPrices = new ElementCollection("[data-selenium='roomPrice']", "Room Prices");
    private final ElementCollection bookButtons = new ElementCollection("[data-selenium='bookButton']", "Book Buttons");
    
    // Amenities and facilities
    private final ElementCollection amenities = new ElementCollection("[data-selenium='amenity']", "Amenities");
    private final Button showAllAmenitiesButton = new Button("[data-selenium='showAllAmenities']", "Show All Amenities");

    /**
     * Get hotel name from details page
     * @return Hotel name
     */
    public String getHotelName() {
        hotelName.waitForVisible();
        return hotelName.getText();
    }

    /**
     * Get hotel address
     * @return Hotel address
     */
    public String getHotelAddress() {
        return hotelAddress.getText();
    }

    /**
     * Get hotel rating
     * @return Hotel rating
     */
    public String getHotelRating() {
        return hotelRating.getText();
    }

    /**
     * Get available room types
     * @return List of room type names
     */
    public ElementCollection getRoomTypes() {
        return roomTypes;
    }

    /**
     * Get room price by index
     * @param index Room index in the list
     * @return Room price as string
     */
    public String getRoomPrice(int index) {
        return roomPrices.get(index).getText();
    }

    /**
     * Book a room by index
     * @param index Room index in the list
     */
    public void bookRoom(int index) {
        bookButtons.get(index).scrollIntoView();
        bookButtons.get(index).click();
    }

    /**
     * Show all amenities
     */
    public void showAllAmenities() {
        if (showAllAmenitiesButton.isDisplayed()) {
            showAllAmenitiesButton.click();
        }
    }

    /**
     * Get list of amenities
     * @return List of amenity names
     */
    public ElementCollection getAmenities() {
        return amenities;
    }
}
