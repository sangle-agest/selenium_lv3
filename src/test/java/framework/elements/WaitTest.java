package framework.elements;

import framework.elements.core.BaseElement;
import org.testng.annotations.*;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import static org.testng.Assert.*;

/**
 * Unit tests for BaseElement wait mechanisms
 */
public class WaitTest {
    
    private static final String TEST_PAGE_URL = "about:blank";
    
    @BeforeClass
    public void setUp() {
        // Set up the Selenide configuration for testing
        System.setProperty("selenide.timeout", "2000");
        System.setProperty("selenide.browser", "chrome");
        System.setProperty("selenide.headless", "true");
    }
    
    @BeforeMethod
    public void setUpTest() {
        // Open a blank page with basic HTML
        Selenide.open(TEST_PAGE_URL);
        Selenide.executeJavaScript(
            "document.body.innerHTML = '<div id=\"testContainer\"></div>';"
        );
    }
    
    @Test
    public void testWaitForVisible() {
        // Arrange - create a hidden element
        Selenide.executeJavaScript(
            "var el = document.createElement('div');" +
            "el.id = 'test-element';" +
            "el.style.display = 'none';" +
            "document.getElementById('testContainer').appendChild(el);"
        );
        
        BaseElement element = new BaseElement("#test-element", "Test Element") {};
        
        // Make the element visible after a delay
        Selenide.executeJavaScript(
            "setTimeout(function() {" +
            "  document.getElementById('test-element').style.display = 'block';" +
            "}, 500);"
        );
        
        // Act & Assert
        long startTime = System.currentTimeMillis();
        element.waitForVisible();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Verify element is now visible
        boolean isVisible = Selenide.executeJavaScript(
            "return window.getComputedStyle(document.getElementById('test-element')).display !== 'none';"
        );
        assertTrue(isVisible, "Element should be visible after wait");
        
        // Verify wait duration was reasonable (>= delay time but < timeout)
        assertTrue(duration >= 500, "Wait should have lasted at least 500ms");
        assertTrue(duration < 2000, "Wait should not have reached timeout");
    }
    
    @Test
    public void testWaitForClickable() {
        // Arrange - create a disabled element
        Selenide.executeJavaScript(
            "var button = document.createElement('button');" +
            "button.id = 'test-button';" +
            "button.disabled = true;" +
            "button.textContent = 'Test Button';" +
            "document.getElementById('testContainer').appendChild(button);"
        );
        
        BaseElement element = new BaseElement("#test-button", "Test Button") {};
        
        // Make the element enabled after a delay
        Selenide.executeJavaScript(
            "setTimeout(function() {" +
            "  document.getElementById('test-button').disabled = false;" +
            "}, 700);"
        );
        
        // Act & Assert
        long startTime = System.currentTimeMillis();
        element.waitForClickable();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Verify element is now enabled
        boolean isEnabled = Selenide.executeJavaScript(
            "return !document.getElementById('test-button').disabled;"
        );
        assertTrue(isEnabled, "Element should be enabled after wait");
        
        // Verify wait duration was reasonable
        assertTrue(duration >= 700, "Wait should have lasted at least 700ms");
        assertTrue(duration < 2000, "Wait should not have reached timeout");
    }
    
    @Test
    public void testWaitForExist() {
        // Arrange - container starts empty
        BaseElement element = new BaseElement("#dynamic-element", "Dynamic Element") {};
        
        // Add the element after a delay
        Selenide.executeJavaScript(
            "setTimeout(function() {" +
            "  var el = document.createElement('div');" +
            "  el.id = 'dynamic-element';" +
            "  document.getElementById('testContainer').appendChild(el);" +
            "}, 600);"
        );
        
        // Act & Assert
        long startTime = System.currentTimeMillis();
        element.waitForExist();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Verify element exists
        boolean exists = Selenide.executeJavaScript(
            "return document.getElementById('dynamic-element') !== null;"
        );
        assertTrue(exists, "Element should exist after wait");
        
        // Verify wait duration was reasonable
        assertTrue(duration >= 600, "Wait should have lasted at least 600ms");
        assertTrue(duration < 2000, "Wait should not have reached timeout");
    }
    
    @Test(expectedExceptions = AssertionError.class)
    public void testWaitTimeout() {
        // Arrange - element that will never appear
        BaseElement element = new BaseElement("#never-appears", "Never Appears Element") {};
        
        // Set a shorter timeout for this test
        Selenide.executeJavaScript(
            "window.originalTimeout = Selenide.timeout;" +
            "Selenide.timeout = 500;"
        );
        
        try {
            // Act - should throw timeout exception
            element.waitForVisible();
        } finally {
            // Restore original timeout
            Selenide.executeJavaScript(
                "Selenide.timeout = window.originalTimeout;"
            );
        }
    }
    
    @AfterMethod
    public void tearDownTest() {
        Selenide.executeJavaScript("document.body.innerHTML = '';");
    }
    
    @AfterClass
    public void tearDown() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Selenide.closeWebDriver();
        }
    }
}
