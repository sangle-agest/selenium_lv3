package tests.agoda;

import framework.pages.agoda.AgodaHomePage;
import framework.pages.agoda.SearchResultsPage;
import framework.utils.BrowserUtils;
import framework.utils.DateTimeHelper;
import framework.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Selenide.open;

/**
 * Test class for Agoda hotel search functionality
 * 
 * NOTE: This is a demo implementation. The actual selectors need to be updated 
 * with the correct ones from the Agoda website.
 */
public class AgodaHotelSearchTest extends AgodaBaseTest {
    
    private AgodaHomePage homePage;
    private SearchResultsPage searchResultsPage;
    
    @BeforeMethod
    public void initializePages() {
        LogUtils.logAction("AgodaHotelSearchTest", "Setting up test");
        
        // Open Agoda website (using baseUrl from AgodaBaseTest)
        open("/");
        
        homePage = new AgodaHomePage();
        searchResultsPage = new SearchResultsPage();
        
        LogUtils.logSuccess("AgodaHotelSearchTest", "Test setup completed successfully");
    }
    
    /**
     * TC 01: Search and Sort Hotel Successfully
     * 
     * Steps:
     * 1. Navigate to Agoda homepage
     * 2. Search hotel with:
     *    - Place: Da Nang
     *    - Date: 3 days from next Friday
     *    - Number of people: Family Travelers → 2 rooms, 4 adults
     * 3. Verify search result is displayed (first 5 hotels listed with Da Nang as destination)
     * 4. Sort hotels by lowest prices and verify the first 5 hotels are re-ordered by ascending price
     */
    @Test(description = "TC 01: Search and Sort Hotel Successfully", enabled = true)
    public void searchAndSortHotelTest() {
        LogUtils.logAction("AgodaHotelSearchTest", "Starting TC 01: Search and Sort Hotel Successfully");
        
        // Step 1: Already on Agoda home page (handled in setupTest)
        LogUtils.logAction("AgodaHotelSearchTest", "Step 1: Navigated to Agoda homepage");
        
        // Step 2: Search for a destination
        String destination = "Da Nang";
        LogUtils.logAction("AgodaHotelSearchTest", "Step 2: Searching for destination: " + destination);
        homePage.searchDestination(destination);
        
        // Calculate dates - 3 days from next Friday
        LogUtils.logAction("AgodaHotelSearchTest", "Setting dates: 3 days from next Friday");
        
        // Use DateTimeHelper to calculate check-in (next Friday) and check-out (3 days later)
        Date[] bookingDates = DateTimeHelper.calculateWeekendStay(Calendar.FRIDAY, 3);
        String checkInDate = DateTimeHelper.formatDate(bookingDates[0]);
        String checkOutDate = DateTimeHelper.formatDate(bookingDates[1]);
        
        LogUtils.logAction("AgodaHotelSearchTest", "Setting dates - Check-in: " + checkInDate + ", Check-out: " + checkOutDate);
        
        // Get days from today for UI interaction
        int fridayOffset = DateTimeHelper.getDaysUntilNextDayOfWeek(Calendar.FRIDAY);
        int checkoutOffset = fridayOffset + 3;
        
        homePage.setDates(fridayOffset, checkoutOffset);
        
        // Set occupancy - 2 rooms, 4 adults
        LogUtils.logAction("AgodaHotelSearchTest", "Setting occupancy: 2 rooms, 4 adults");
        homePage.setOccupancy(4, 0, 2); // 4 adults, 0 children, 2 rooms
        
        // Click on Search button
        LogUtils.logAction("AgodaHotelSearchTest", "Clicking search button");
        homePage.clickSearch();
        
        // Wait for the new tab to open (up to 10 seconds)
        LogUtils.logAction("AgodaHotelSearchTest", "Waiting for search results tab to open");
        boolean newTabOpened = BrowserUtils.waitForWindowCount(2, 10);
        
        if (newTabOpened) {
            // Switch to the new tab (index 1)
            LogUtils.logAction("AgodaHotelSearchTest", "Switching to search results tab");
            BrowserUtils.switchToWindow(1);
            LogUtils.logSuccess("AgodaHotelSearchTest", "Switched to search results tab");
        } else {
            // If no new tab opened, try to find the search results on the current page
            LogUtils.logWarning("AgodaHotelSearchTest", "No new tab opened, continuing on current page");
        }
        
        // Wait for search results page to load
        searchResultsPage.waitForPageToLoad();
        
        // Step 3: Verify search results are displayed
        LogUtils.logAction("AgodaHotelSearchTest", "Step 3: Verifying search results");
        int resultCount = searchResultsPage.getNumberOfResults();
        LogUtils.logSuccess("AgodaHotelSearchTest", "Search results displayed: " + resultCount + " hotels found");
        Assert.assertTrue(resultCount > 0, "Search should return at least one hotel");
        
        // Verify first 5 hotels are in Da Nang
        LogUtils.logAction("AgodaHotelSearchTest", "Verifying first 5 hotels are in Da Nang");
        int hotelsToCheck = Math.min(5, resultCount);
        for (int i = 0; i < hotelsToCheck; i++) {
            String hotelName = searchResultsPage.getHotelName(i);
            String hotelLocation = searchResultsPage.getHotelLocation(i);
            LogUtils.logAction("AgodaHotelSearchTest", "Hotel " + (i+1) + ": " + hotelName + " in " + hotelLocation);
            Assert.assertTrue(hotelLocation.contains(destination), 
                    "Hotel " + (i+1) + " should be in " + destination + " but was in " + hotelLocation);
        }
        
        // Step 4: Sort results by price (low to high)
        LogUtils.logAction("AgodaHotelSearchTest", "Step 4: Sorting results by 'Price (low to high)'");
        searchResultsPage.sortResultsBy("Price (low to high)");
        
        // Verify sorting is applied correctly to first 5 hotels
        LogUtils.logAction("AgodaHotelSearchTest", "Verifying first 5 hotels are sorted by ascending price");
        
        // Store prices before checking to avoid multiple calls to getHotelPrice
        double[] prices = new double[hotelsToCheck];
        for (int i = 0; i < hotelsToCheck; i++) {
            prices[i] = searchResultsPage.getHotelPrice(i);
            LogUtils.logAction("AgodaHotelSearchTest", "Hotel " + (i+1) + " price: " + prices[i]);
        }
        
        // Verify prices are in ascending order
        for (int i = 0; i < hotelsToCheck - 1; i++) {
            Assert.assertTrue(prices[i] <= prices[i+1], 
                    "Hotels should be sorted by price in ascending order. " +
                    "Hotel " + (i+1) + " price: " + prices[i] + ", " +
                    "Hotel " + (i+2) + " price: " + prices[i+1]);
        }
        
        // Verify hotels are still in Da Nang after sorting
        LogUtils.logAction("AgodaHotelSearchTest", "Verifying hotels are still in Da Nang after sorting");
        for (int i = 0; i < hotelsToCheck; i++) {
            String hotelLocation = searchResultsPage.getHotelLocation(i);
            Assert.assertTrue(hotelLocation.contains(destination), 
                    "After sorting, hotel " + (i+1) + " should still be in " + destination);
        }
        
        LogUtils.logSuccess("AgodaHotelSearchTest", "TC 01: Search and Sort Hotel Successfully - PASSED");
        
        // Clean up - close any additional tabs/windows
        cleanupTabs();
    }
    
    /**
     * Helper method to close additional tabs/windows and switch back to the first tab
     */
    private void cleanupTabs() {
        LogUtils.logAction("AgodaHotelSearchTest", "Cleaning up tabs/windows");
        try {
            // Use the new utility method to close all windows except the first one
            BrowserUtils.closeAllWindowsExceptFirst();
        } catch (Exception e) {
            LogUtils.logWarning("AgodaHotelSearchTest", "Error during tab cleanup: " + e.getMessage());
        }
    }
}
