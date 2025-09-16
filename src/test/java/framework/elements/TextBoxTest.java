package framework.elements;

import framework.elements.core.TextBox;
import org.testng.annotations.*;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import static org.testng.Assert.*;

/**
 * Unit tests for TextBox element
 */
public class TextBoxTest {
    
    private static final String TEST_PAGE_URL = "about:blank";
    private static final String TEST_TEXTBOX_HTML = 
        "<input id='test-input' type='text' class='test-class' value='Initial value'>";
    private static final String READ_ONLY_TEXTBOX_HTML = 
        "<input id='readonly-input' type='text' readonly value='Read only value'>";
    
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
            "<div>" + TEST_TEXTBOX_HTML + READ_ONLY_TEXTBOX_HTML + "</div>"
        );
    }
    
    @Test
    public void testSetText() {
        // Arrange
        TextBox textBox = new TextBox("#test-input", "Test Input");
        String newText = "New text value";
        
        // Act
        textBox.setText(newText);
        
        // Assert
        String actualValue = Selenide.executeJavaScript(
            "return document.getElementById('test-input').value;"
        );
        assertEquals(actualValue, newText, "TextBox text was not set correctly");
    }
    
    @Test
    public void testClearAndType() {
        // Arrange
        TextBox textBox = new TextBox("#test-input", "Test Input");
        String newText = "Typed text";
        
        // Act
        textBox.clearAndType(newText);
        
        // Assert
        String actualValue = Selenide.executeJavaScript(
            "return document.getElementById('test-input').value;"
        );
        assertEquals(actualValue, newText, "TextBox was not cleared and typed correctly");
    }
    
    @Test
    public void testAppendText() {
        // Arrange
        TextBox textBox = new TextBox("#test-input", "Test Input");
        String initialValue = "Initial value";
        String appendedText = " appended";
        
        // Act
        textBox.appendText(appendedText);
        
        // Assert
        String actualValue = Selenide.executeJavaScript(
            "return document.getElementById('test-input').value;"
        );
        assertEquals(actualValue, initialValue + appendedText, "Text was not appended correctly");
    }
    
    @Test
    public void testClear() {
        // Arrange
        TextBox textBox = new TextBox("#test-input", "Test Input");
        
        // Act
        textBox.clear();
        
        // Assert
        String actualValue = Selenide.executeJavaScript(
            "return document.getElementById('test-input').value;"
        );
        assertEquals(actualValue, "", "TextBox was not cleared");
    }
    
    @Test
    public void testGetValue() {
        // Arrange
        TextBox textBox = new TextBox("#test-input", "Test Input");
        String expectedValue = "Initial value";
        
        // Act
        String actualValue = textBox.getValue();
        
        // Assert
        assertEquals(actualValue, expectedValue, "Incorrect value returned");
    }
    
    @Test
    public void testIsReadOnly() {
        // Arrange
        TextBox normalTextBox = new TextBox("#test-input", "Test Input");
        TextBox readOnlyTextBox = new TextBox("#readonly-input", "Read Only Input");
        
        // Act & Assert
        assertFalse(normalTextBox.isReadOnly(), "Normal input should not be read-only");
        assertTrue(readOnlyTextBox.isReadOnly(), "Read-only input should be read-only");
    }
    
    @Test(expectedExceptions = RuntimeException.class)
    public void testSetTextOnNonExistentElement() {
        // Arrange
        TextBox nonExistentTextBox = new TextBox("#non-existent-input", "Non-existent Input");
        
        // Act - should throw exception
        nonExistentTextBox.setText("Some text");
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
