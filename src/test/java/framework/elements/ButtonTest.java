package framework.elements;

import framework.elements.core.Button;
import org.testng.annotations.*;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import static org.testng.Assert.*;

/**
 * Unit tests for Button element
 */
public class ButtonTest {
    
    private static final String TEST_PAGE_URL = "about:blank";
    private static final String TEST_BUTTON_HTML = 
        "<button id='test-button' class='test-class'>Test Button</button>";
    private static final String DISABLED_BUTTON_HTML = 
        "<button id='disabled-button' disabled>Disabled Button</button>";
    
    @BeforeClass
    public void setUp() {
        // Set up the Selenide configuration for testing
        System.setProperty("selenide.timeout", "2000");
        System.setProperty("selenide.browser", "chrome");
        System.setProperty("selenide.headless", "true");
    }
    
    @BeforeMethod
    public void setUpTest() {
        // Open a blank page and inject our test HTML
        Selenide.open(TEST_PAGE_URL);
        Selenide.executeJavaScript(
            "document.body.innerHTML = arguments[0]", 
            "<div>" + TEST_BUTTON_HTML + DISABLED_BUTTON_HTML + "</div>"
        );
    }
    
    @Test
    public void testButtonClick() {
        // Arrange
        Button button = new Button("#test-button", "Test Button");
        Selenide.executeJavaScript(
            "document.getElementById('test-button').addEventListener('click', function() { " +
            "  this.setAttribute('data-clicked', 'true'); " +
            "});"
        );
        
        // Act
        button.click();
        
        // Assert
        String clickAttribute = Selenide.executeJavaScript(
            "return document.getElementById('test-button').getAttribute('data-clicked');"
        );
        assertEquals(clickAttribute, "true", "Button click was not registered");
    }
    
    @Test
    public void testButtonIsEnabled() {
        // Arrange
        Button enabledButton = new Button("#test-button", "Test Button");
        Button disabledButton = new Button("#disabled-button", "Disabled Button");
        
        // Act & Assert
        assertTrue(enabledButton.isEnabled(), "Button should be enabled");
        assertFalse(disabledButton.isEnabled(), "Button should be disabled");
    }
    
    @Test
    public void testButtonSubmit() {
        // Arrange
        Selenide.executeJavaScript(
            "document.body.innerHTML = '<form id=\"test-form\" onsubmit=\"event.preventDefault(); " + 
            "this.setAttribute(\'data-submitted\', \'true\');\">" +
            "<button id=\"submit-button\" type=\"submit\">Submit</button></form>';"
        );
        Button submitButton = new Button("#submit-button", "Submit Button");
        
        // Act
        submitButton.submit();
        
        // Assert
        String submittedAttribute = Selenide.executeJavaScript(
            "return document.getElementById('test-form').getAttribute('data-submitted');"
        );
        assertEquals(submittedAttribute, "true", "Form was not submitted");
    }
    
    @Test
    public void testButtonFocus() {
        // Arrange
        Button button = new Button("#test-button", "Test Button");
        
        // Act
        button.focus();
        
        // Assert
        Boolean isFocused = Selenide.executeJavaScript(
            "return document.getElementById('test-button') === document.activeElement;"
        );
        assertTrue(isFocused, "Button should be focused");
    }
    
    @Test(expectedExceptions = RuntimeException.class)
    public void testClickNonExistentButton() {
        // Arrange
        Button nonExistentButton = new Button("#non-existent-button", "Non-existent Button");
        
        // Act - should throw exception
        nonExistentButton.click();
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
