package framework.elements;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import static com.codeborne.selenide.Selenide.*;

/**
 * Base class for all element tests using The Internet Herokuapp
 */
public abstract class ElementsTestBase extends BaseTest {
    
    protected static final String BASE_URL = "https://the-internet.herokuapp.com";
    
    @BeforeMethod
    public void setUpTest() {
        // Open the base URL for the test site
        open(BASE_URL);
    }
    
    @AfterMethod
    public void tearDownTest() {
        // Clear local storage and cookies after each test
        clearBrowserLocalStorage();
        clearBrowserCookies();
    }
    
    /**
     * Helper method to navigate to a specific example page
     * @param path The path of the example page (e.g., "/checkboxes")
     */
    protected void navigateToExample(String path) {
        open(BASE_URL + path);
    }
}
