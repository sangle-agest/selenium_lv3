package base; // Change from 'base' to 'tests'

import com.codeborne.selenide.Configuration;
import framework.utils.ConfigManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import static com.codeborne.selenide.Selenide.*;

public abstract class BaseTest {

    @BeforeClass
    public void setUp() {
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