package base; // Change from 'base' to 'tests'

import com.codeborne.selenide.Configuration;
import framework.config.LoggingConfig;
import framework.utils.ConfigManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Selenide.*;

public abstract class BaseTest {

    @BeforeSuite
    public void setupSuite() {
        // Initialize logging configuration to suppress warnings
        LoggingConfig.initSuppressedLogging();
    }

    @BeforeClass
    public void setUp() {
        // Set system property to suppress CDP warnings
        System.setProperty("org.openqa.selenium.devtools.CdpVersionFinder.suppressDevToolsLogging", "true");
        
        Configuration.browser = ConfigManager.getBrowser();
        Configuration.browserSize = ConfigManager.getBrowserSize();
        Configuration.pageLoadTimeout = ConfigManager.getPageLoadTimeout();
        Configuration.timeout = ConfigManager.getElementTimeout();
        Configuration.headless = ConfigManager.isHeadless();
    }

    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }
}