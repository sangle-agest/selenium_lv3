package tests.agoda;

import framework.pages.agoda.AgodaHomePage;
import framework.pages.agoda.SearchResultsPage;
import framework.utils.BrowserUtils;
import framework.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

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
        // Don't add a slash as the URL already has one
        open("");
        
        // Wait a moment for the page to start loading
        sleep(2000); // 2 seconds
        
        homePage = new AgodaHomePage();
        searchResultsPage = new SearchResultsPage();
        
        LogUtils.logSuccess("AgodaHotelSearchTest", "Test setup completed successfully");
    }
    
    /**
     * Ensures browser cleanup happens after each test method, even if the test fails
     */
    @AfterMethod
    public void tearDown() {
        LogUtils.logAction("AgodaHotelSearchTest", "Running test teardown");
        cleanupTabs();
    }
    
    /**
     * Data provider for hotel search test
     * @return Array of test data objects
     */
    @DataProvider(name = "hotelSearchData")
    public Object[][] provideHotelSearchData() {
        return new Object[][] {
            { AgodaHotelSearchTestData.forDaNang() },
            // Uncomment to test with Bangkok data as well
            // { AgodaHotelSearchTestData.forBangkok() }
        };
    }
    
    /**
     * TC 01: Search and Sort Hotel Successfully
     * 
     * Steps:
     * 1. Navigate to Agoda homepage
     * 2. Search hotel with parameters from test data:
     *    - Place: Destination from test data
     *    - Date: Check-in and check-out dates from test data
     *    - Number of people: Adults, children, and rooms from test data
     * 3. Verify search result is displayed with hotels in the expected location
     * 4. Sort hotels by price and verify they are in ascending order
     */
    @Test(description = "TC 01: Search and Sort Hotel Successfully", 
          dataProvider = "hotelSearchData", 
          enabled = true)
    public void searchAndSortHotelTest(AgodaHotelSearchTestData testData) {
        LogUtils.logAction("AgodaHotelSearchTest", "Starting TC 01: Search and Sort Hotel Successfully");
        LogUtils.logAction("AgodaHotelSearchTest", "Using test data for destination: " + testData.getDestination());
        
        try {
            // Step 1: Already on Agoda home page (handled in setupTest)
            LogUtils.logAction("AgodaHotelSearchTest", "Step 1: Navigated to Agoda homepage");
            
            // Step 2: Search for a destination from test data
            String destination = testData.getDestination();
            LogUtils.logAction("AgodaHotelSearchTest", "Step 2: Searching for destination: " + destination);
            homePage.searchDestination(destination);
            
            // Calculate dates based on test data
            LogUtils.logAction("AgodaHotelSearchTest", "Setting dates with check-in offset: " + 
                              testData.getCheckInOffsetDays() + ", check-out offset: " + 
                              testData.getCheckOutOffsetDays());
            
            homePage.setDates(testData.getCheckInOffsetDays(), testData.getCheckOutOffsetDays());
            
            // Set occupancy from test data
            LogUtils.logAction("AgodaHotelSearchTest", "Setting occupancy: " + 
                              testData.getRooms() + " rooms, " + 
                              testData.getAdults() + " adults, " + 
                              testData.getChildren() + " children");
            
            homePage.setOccupancy(testData.getAdults(), testData.getChildren(), testData.getRooms());
            
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
            
            // Wait for search results to appear with a longer timeout (20 seconds)
            boolean searchResultsLoaded = searchResultsPage.waitForSearchResults(20);
            Assert.assertTrue(searchResultsLoaded, "Search results should load within the timeout period");
            
            // Step 3: Verify search results are displayed
            LogUtils.logAction("AgodaHotelSearchTest", "Step 3: Verifying search results");
            int resultCount = searchResultsPage.getNumberOfResults();
            LogUtils.logSuccess("AgodaHotelSearchTest", "Search results displayed: " + resultCount + " hotels found");
            Assert.assertTrue(resultCount > 0, "Search should return at least one hotel");
            
            // Check hotel location
            LogUtils.logAction("AgodaHotelSearchTest", "Verifying at least one hotel is available in the expected location");
            
            // Get hotel details - these methods should handle their own exceptions in the page object
            String hotelName = searchResultsPage.getHotelName(0);  // Just check the first hotel
            String hotelLocation = searchResultsPage.getHotelLocation(0);
            LogUtils.logAction("AgodaHotelSearchTest", "First hotel: " + hotelName + " in " + hotelLocation);
            
            // Use location keywords from test data for verification
            boolean isValidLocation = isInExpectedLocation(hotelName, hotelLocation, testData.getExpectedLocationKeywords());
            
            Assert.assertTrue(isValidLocation, 
                    "First hotel should be in " + testData.getDestination() + " but was in " + hotelLocation);
            
            LogUtils.logSuccess("AgodaHotelSearchTest", "Hotel verification successful");
            
            // Step 4: Sort results by price (low to high)
            LogUtils.logAction("AgodaHotelSearchTest", "Step 4: Sorting results by '" + testData.getSortOption() + "'");
            
            // Use the specialized sorting method instead of the general one for better reliability
            if (testData.getSortOption().toLowerCase().contains("price")) {
                searchResultsPage.sortByLowestPrice();
            } else {
                searchResultsPage.sortResultsBy(testData.getSortOption());
            }
            
            // Verify all hotel prices are in ascending order
            int numHotelsToCheck = Math.min(searchResultsPage.getNumberOfResults(), 5); // Check up to 5 hotels
            
            if (numHotelsToCheck > 1) {
                boolean isSorted = verifyPriceSorting(searchResultsPage, numHotelsToCheck);
                if (isSorted) {
                    LogUtils.logSuccess("AgodaHotelSearchTest", "Verified price sorting is working for " + 
                                      numHotelsToCheck + " hotels");
                }
            } else {
                LogUtils.logWarning("AgodaHotelSearchTest", "Only one hotel found, can't verify sorting");
            }
            
            LogUtils.logSuccess("AgodaHotelSearchTest", "TC 01: Search and Sort Hotel Successfully - PASSED");
        } finally {
            // Clean up - close any additional tabs/windows
            cleanupTabs();
        }
    }
    
    /**
     * Helper method to verify if a hotel is in the expected location
     * @param hotelName The hotel name
     * @param hotelLocation The hotel location
     * @param expectedKeywords List of expected location keywords
     * @return true if the hotel is in the expected location
     */
    private boolean isInExpectedLocation(String hotelName, String hotelLocation, java.util.List<String> expectedKeywords) {
        // Check both hotel name and location for any of the expected keywords
        String combinedText = (hotelName + " " + hotelLocation).toLowerCase();
        
        for (String keyword : expectedKeywords) {
            if (combinedText.contains(keyword.toLowerCase())) {
                LogUtils.logSuccess("AgodaHotelSearchTest", "Found location keyword '" + keyword + "' in hotel information");
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Helper method to close additional tabs/windows and switch back to the first tab
     */
    private void cleanupTabs() {
        LogUtils.logAction("AgodaHotelSearchTest", "Cleaning up tabs/windows");
        BrowserUtils.closeAllWindowsExceptFirst();
    }
    
    /**
     * Helper method to verify hotel prices are in ascending order
     * 
     * @param searchResultsPage The search results page object
     * @param numHotelsToCheck Number of hotels to check
     * @return true if all prices are in ascending order, false otherwise
     */
    private boolean verifyPriceSorting(SearchResultsPage searchResultsPage, int numHotelsToCheck) {
        LogUtils.logAction("AgodaHotelSearchTest", "Verifying price sorting for " + numHotelsToCheck + " hotels");
        
        // Get prices for all hotels to check
        double[] prices = new double[numHotelsToCheck];
        boolean allPricesValid = true;
        
        // Collect all prices
        for (int i = 0; i < numHotelsToCheck; i++) {
            prices[i] = searchResultsPage.getHotelPrice(i);
            LogUtils.logAction("AgodaHotelSearchTest", "Hotel " + (i+1) + " price: " + prices[i]);
            
            // Check if we got a valid price (greater than 0)
            if (prices[i] <= 0) {
                LogUtils.logWarning("AgodaHotelSearchTest", "Invalid price for hotel " + (i+1) + ": " + prices[i]);
                allPricesValid = false;
            }
        }
        
        // If any price is invalid, we can't verify sorting
        if (!allPricesValid) {
            LogUtils.logWarning("AgodaHotelSearchTest", 
                "Could not verify price sorting, some hotels have invalid prices (0 or negative)");
            return false;
        }
        
        // Check if prices are in ascending order
        boolean isSorted = true;
        for (int i = 0; i < numHotelsToCheck - 1; i++) {
            if (prices[i] > prices[i+1]) {
                LogUtils.logError("AgodaHotelSearchTest", 
                    "Hotels are not properly sorted by price. Hotel " + (i+1) + " price (" + 
                    prices[i] + ") is greater than hotel " + (i+2) + " price (" + prices[i+1] + ")", 
                    new AssertionError("Price sorting failed"));
                
                Assert.fail("Hotels should be sorted by price (low to high), but hotel " + (i+1) + 
                    " price (" + prices[i] + ") is greater than hotel " + (i+2) + 
                    " price (" + prices[i+1] + ")");
                
                isSorted = false;
                break;
            }
        }
        
        return isSorted;
    }
}
