package tests.agoda;

import framework.pages.agoda.AgodaHomePage;
import framework.pages.agoda.SearchResultsPage;
import framework.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
     * 1. Go to Agoda home page
     * 2. Search for a destination
     * 3. Set check-in and check-out dates
     * 4. Set occupancy (adults and children)
     * 5. Click on Search button
     * 6. Verify search results are displayed
     * 7. Sort results by price (low to high)
     * 8. Verify sorting is applied correctly
     */
    @Test(description = "TC 01: Search and Sort Hotel Successfully", enabled = true)
    public void searchAndSortHotelTest() {
        LogUtils.logAction("AgodaHotelSearchTest", "Starting TC 01: Search and Sort Hotel Successfully");
        
        // Step 1: Already on Agoda home page (handled in setupTest)
        
        // Step 2: Search for a destination
        String destination = "Bangkok";
        LogUtils.logAction("AgodaHotelSearchTest", "Searching for destination: " + destination);
        homePage.searchDestination(destination);
        
        // Step 3: Set check-in and check-out dates (7 days from now, staying for 3 nights)
        LogUtils.logAction("AgodaHotelSearchTest", "Setting dates: Check-in 7 days from now, stay for 3 nights");
        homePage.setDates(7, 10);
        
        // Step 4: Set occupancy (2 adults, 1 child)
        LogUtils.logAction("AgodaHotelSearchTest", "Setting occupancy: 2 adults, 1 child");
        homePage.setOccupancy(2, 1);
        
        // Verify occupancy is set correctly
        Assert.assertEquals(homePage.getAdultCount(), 2, "Adult count should be 2");
        Assert.assertEquals(homePage.getChildCount(), 1, "Child count should be 1");
        
        // Step 5: Click on Search button
        LogUtils.logAction("AgodaHotelSearchTest", "Clicking search button");
        homePage.clickSearch();
        
        // Step 6: Verify search results are displayed
        int resultCount = searchResultsPage.getNumberOfResults();
        LogUtils.logSuccess("AgodaHotelSearchTest", "Search results displayed: " + resultCount + " hotels found");
        Assert.assertTrue(resultCount > 0, "Search should return at least one hotel");
        
        // Step 7: Sort results by price (low to high)
        LogUtils.logAction("AgodaHotelSearchTest", "Sorting results by 'Price (low to high)'");
        searchResultsPage.sortResultsBy("Price (low to high)");
        
        // Step 8: Verify sorting is applied correctly
        LogUtils.logAction("AgodaHotelSearchTest", "Verifying sorting is applied correctly");
        
        // Get first few hotel prices to verify sorting
        double firstHotelPrice = searchResultsPage.getHotelPrice(0);
        double secondHotelPrice = searchResultsPage.getHotelPrice(1);
        
        // Verify prices are in ascending order
        Assert.assertTrue(firstHotelPrice <= secondHotelPrice, 
                "Hotels should be sorted by price in ascending order. First price: " + 
                firstHotelPrice + ", Second price: " + secondHotelPrice);
        
        LogUtils.logSuccess("AgodaHotelSearchTest", "TC 01: Search and Sort Hotel Successfully - PASSED");
    }
}
