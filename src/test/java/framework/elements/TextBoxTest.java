package framework.elements;

import framework.elements.core.TextBox;
import org.testng.annotations.*;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import static org.testng.Assert.*;
import static com.codeborne.selenide.Selenide.$;

/**
 * Unit tests for TextBox element using The Internet Herokuapp site
 */
public class TextBoxTest extends ElementsTestBase {
    
    @Test
    public void testSetText() {
        // Navigate to the inputs page
        navigateToExample("/inputs");
        
        // Arrange
        TextBox textBox = new TextBox("input[type='number']", "Number Input");
        String newText = "42";
        
        // Act
        textBox.setText(newText);
        
        // Assert
        String actualValue = textBox.getValue();
        assertEquals(actualValue, newText, "TextBox text was not set correctly");
    }
    
    @Test
    public void testClearAndType() {
        // Navigate to the inputs page
        navigateToExample("/inputs");
        
        // Arrange
        TextBox textBox = new TextBox("input[type='number']", "Number Input");
        String initialText = "10";
        String newText = "99";
        
        // First set some initial text
        textBox.setText(initialText);
        
        // Act - clear and type new text
        textBox.clearAndType(newText);
        
        // Assert
        String actualValue = textBox.getValue();
        assertEquals(actualValue, newText, "TextBox was not cleared and typed correctly");
    }
    
    @Test
    public void testAppendText() {
        // Navigate to the inputs page
        navigateToExample("/inputs");
        
        // Arrange
        TextBox textBox = new TextBox("input[type='number']", "Number Input");
        
        // First set some text
        textBox.setText("10");
        
        // Act - append more text
        textBox.appendText("20");
        
        // Assert
        String actualValue = textBox.getValue();
        assertEquals(actualValue, "1020", "Text was not appended correctly");
    }
    
    @Test
    public void testClear() {
        // Navigate to the inputs page
        navigateToExample("/inputs");
        
        // Arrange
        TextBox textBox = new TextBox("input[type='number']", "Number Input");
        
        // First set some text so we can clear it
        textBox.setText("123");
        
        // Act
        textBox.clear();
        
        // Assert
        String actualValue = textBox.getValue();
        assertEquals(actualValue, "", "TextBox was not cleared");
    }
    
    @Test
    public void testPressEnter() {
        // Navigate to the key presses example
        navigateToExample("/key_presses");
        
        // Arrange
        TextBox inputBox = new TextBox("#target", "Key Input");
        
        // Act - set text and press Enter
        inputBox.setText("Test");
        inputBox.pressEnter();
        
        // Assert - check the result shows Enter was pressed
        String resultText = $("#result").getText();
        assertEquals(resultText, "You entered: ENTER", "Enter key was not registered correctly");
    }
    
    @Test
    public void testMethodChaining() {
        // Navigate to the inputs page
        navigateToExample("/inputs");
        
        // Arrange
        TextBox textBox = new TextBox("input[type='number']", "Number Input");
        
        // Act - use method chaining
        textBox.setTextAndChain("50").appendText("0");
        String value = textBox.getValue();
        
        // Assert
        assertEquals(value, "500", "Method chaining did not work correctly");
    }
    
    @Test(expectedExceptions = ElementNotFound.class)
    public void testSetTextOnNonExistentElement() {
        // Navigate to the inputs page
        navigateToExample("/inputs");
        
        // Arrange
        TextBox nonExistentTextBox = new TextBox("#non-existent-input", "Non-existent Input");
        
        // Act - should throw exception
        nonExistentTextBox.setText("Some text");
    }
}
