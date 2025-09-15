package framework.pages.agoda;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import framework.base.BasePage;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.*;

public class HotelDetailsPage extends BasePage {
    // Hotel information
    private final SelenideElement hotelName = $("[data-selenium='hotelName']");
    private final SelenideElement hotelAddress = $("[data-selenium='hotelAddress']");
    private final SelenideElement hotelRating = $("[data-selenium='hotelRating']");
    
    // Room selection
    private final ElementsCollection roomTypes = $$("[data-selenium='roomType']");
    private final ElementsCollection roomPrices = $$("[data-selenium='roomPrice']");
    private final ElementsCollection bookButtons = $$("[data-selenium='bookButton']");
    
    // Amenities and facilities
    private final ElementsCollection amenities = $$("[data-selenium='amenity']");
    private final SelenideElement showAllAmenitiesButton = $("[data-selenium='showAllAmenities']");

    /**
     * Get hotel name from details page
     * @return Hotel name
     */
    public String getHotelName() {
        waitForElementVisible(hotelName);
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
    public ElementsCollection getRoomTypes() {
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
        scrollIntoView(bookButtons.get(index));
        bookButtons.get(index).click();
    }

    /**
     * Show all amenities
     */
    public void showAllAmenities() {
        if (showAllAmenitiesButton.is(visible)) {
            showAllAmenitiesButton.click();
        }
    }

    /**
     * Get list of amenities
     * @return List of amenity names
     */
    public ElementsCollection getAmenities() {
        return amenities;
    }
}
