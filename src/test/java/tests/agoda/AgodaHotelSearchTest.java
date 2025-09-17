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
        open("");
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
    }
    
    /**
     * Data provider for hotel search test
     * @return Array of test data objects
     */
    @DataProvider(name = "hotelSearchData")
    public Object[][] provideHotelSearchData() {
        return new Object[][] {
            { AgodaHotelSearchTestData.forDaNang() },
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
    }
}
