package tests.agoda;

import java.util.Arrays;
import java.util.List;

/**
 * Data class for Agoda hotel search tests
 * Contains all the parameters and expected values for the tests
 */
public class AgodaHotelSearchTestData {
    
    // Search parameters
    private String destination;
    private int checkInOffsetDays;
    private int checkOutOffsetDays;
    private int adults;
    private int children;
    private int rooms;
    
    // Verification parameters
    private List<String> expectedLocationKeywords;
    private String sortOption;
    
    /**
     * Default constructor with common values
     */
    public AgodaHotelSearchTestData() {
        // Default values
        this.destination = "Da Nang";
        this.checkInOffsetDays = 3;
        this.checkOutOffsetDays = 6;
        this.adults = 4;
        this.children = 0;
        this.rooms = 2;
        this.expectedLocationKeywords = Arrays.asList("Da Nang", "Danang", "Vietnam");
        this.sortOption = "Price (low to high)";
    }
    
    /**
     * Custom constructor with all parameters
     * 
     * @param destination Hotel destination
     * @param checkInOffsetDays Days from today for check-in
     * @param checkOutOffsetDays Days from today for check-out
     * @param adults Number of adults
     * @param children Number of children
     * @param rooms Number of rooms
     * @param expectedLocationKeywords Keywords to verify in hotel location
     * @param sortOption Sorting option
     */
    public AgodaHotelSearchTestData(
            String destination, 
            int checkInOffsetDays, 
            int checkOutOffsetDays,
            int adults, 
            int children, 
            int rooms, 
            List<String> expectedLocationKeywords,
            String sortOption) {
        this.destination = destination;
        this.checkInOffsetDays = checkInOffsetDays;
        this.checkOutOffsetDays = checkOutOffsetDays;
        this.adults = adults;
        this.children = children;
        this.rooms = rooms;
        this.expectedLocationKeywords = expectedLocationKeywords;
        this.sortOption = sortOption;
    }
    
    /**
     * Factory method to create test data for Da Nang
     * @return Test data for Da Nang
     */
    public static AgodaHotelSearchTestData forDaNang() {
        return new AgodaHotelSearchTestData(
            "Da Nang",
            3, // check-in offset
            6, // check-out offset
            4, // adults
            0, // children
            2, // rooms
            Arrays.asList("Da Nang", "Danang", "Vietnam"),
            "Price (low to high)"
        );
    }
    
    /**
     * Factory method to create test data for Bangkok
     * @return Test data for Bangkok
     */
    public static AgodaHotelSearchTestData forBangkok() {
        return new AgodaHotelSearchTestData(
            "Bangkok",
            7, // check-in offset
            10, // check-out offset
            2, // adults
            0, // children
            1, // rooms
            Arrays.asList("Bangkok", "Thailand"),
            "Price (low to high)"
        );
    }
    
    /**
     * Factory method to create custom test data
     * @param destination Hotel destination
     * @param locationKeywords Keywords to verify in hotel location
     * @return Custom test data
     */
    public static AgodaHotelSearchTestData forCustomDestination(String destination, List<String> locationKeywords) {
        return new AgodaHotelSearchTestData(
            destination,
            3, // default check-in offset
            6, // default check-out offset
            2, // default adults
            0, // default children
            1, // default rooms
            locationKeywords,
            "Price (low to high)"
        );
    }

    // Getters and setters
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCheckInOffsetDays() {
        return checkInOffsetDays;
    }

    public void setCheckInOffsetDays(int checkInOffsetDays) {
        this.checkInOffsetDays = checkInOffsetDays;
    }

    public int getCheckOutOffsetDays() {
        return checkOutOffsetDays;
    }

    public void setCheckOutOffsetDays(int checkOutOffsetDays) {
        this.checkOutOffsetDays = checkOutOffsetDays;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public List<String> getExpectedLocationKeywords() {
        return expectedLocationKeywords;
    }

    public void setExpectedLocationKeywords(List<String> expectedLocationKeywords) {
        this.expectedLocationKeywords = expectedLocationKeywords;
    }

    public String getSortOption() {
        return sortOption;
    }

    public void setSortOption(String sortOption) {
        this.sortOption = sortOption;
    }
}
