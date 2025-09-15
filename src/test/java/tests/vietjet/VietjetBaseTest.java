package tests.vietjet;

import com.codeborne.selenide.Configuration;
import framework.utils.ConfigManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Base test class for Vietjet tests
 */
public class VietjetBaseTest {
    
    @BeforeClass
    public void setUp() {
        Configuration.browser = ConfigManager.getBrowser();
        Configuration.browserSize = ConfigManager.getBrowserSize();
        Configuration.timeout = ConfigManager.getElementTimeout();
        Configuration.pageLoadTimeout = ConfigManager.getPageLoadTimeout();
        Configuration.headless = ConfigManager.isHeadless();
        
        if (!ConfigManager.getRemoteGridUrl().isEmpty()) {
            Configuration.remote = ConfigManager.getRemoteGridUrl();
        }
    }

    @BeforeMethod
    public void setUpTest() {
        // Set base URL for Vietjet
        Configuration.baseUrl = "https://www.vietjetair.com/";
    }
}
