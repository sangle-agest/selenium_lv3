package framework.elements;

import framework.elements.core.Button;
import org.testng.annotations.*;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import static org.testng.Assert.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.disappear;

/**
 * Unit tests for Button element using The Internet Herokuapp site
 */
public class ButtonTest extends ElementsTestBase {
    
    @Test
    public void testButtonClick() {
        // Navigate to the Add/Remove Elements page
        navigateToExample("/add_remove_elements/");
        
        // Arrange - the "Add Element" button
        Button addButton = new Button("button[onclick='addElement()']", "Add Element Button");
        
        // Act
        addButton.click();
        
        // Assert - verify a new delete button was added
        assertTrue($(".added-manually").isDisplayed(), "Add button click did not create a new element");
        
        // Click a second time and verify we have two buttons
        addButton.click();
        assertEquals($$(".added-manually").size(), 2, "Second add button click should create another element");
    }
    
    @Test
    public void testButtonDynamicControl() {
        // Navigate to Dynamic Controls page
        navigateToExample("/dynamic_controls");
        
        // Arrange - the "Remove" button for the checkbox
        Button removeButton = new Button("#btn", "Remove Button");
        
        // Verify checkbox is visible initially
        assertTrue($("#checkbox").isDisplayed(), "Checkbox should be visible initially");
        
        // Act - click remove
        removeButton.click();
        
        // Wait for the loading animation to complete
        $("#loading").should(disappear);
        
        // Assert - verify checkbox is gone
        $("#checkbox").shouldNot(exist);
        
        // Now the button should say "Add"
        assertEquals($("#btn").getText(), "Add", "Button text should change to 'Add'");
    }
    
    @Test
    public void testButtonSubmit() {
        // Navigate to the Login page
        navigateToExample("/login");
        
        // Fill in login form
        $("#username").setValue("tomsmith");
        $("#password").setValue("SuperSecretPassword!");
        
        // Arrange - the login button (which is a submit button)
        Button loginButton = new Button("button[type='submit']", "Login Button");
        
        // Act
        loginButton.submit();
        
        // Assert - verify successful login
        assertTrue($(".flash.success").isDisplayed(), "Login form was not submitted successfully");
        assertTrue($("a.button.secondary").isDisplayed(), "Logout button should be visible after login");
    }
    
    @Test
    public void testButtonFocus() {
        // Navigate to the key presses page to test focus
        navigateToExample("/key_presses");
        
        // Create a button element (using the submit button on this page)
        Button button = new Button("button", "Button");
        
        // Focus the button
        button.focus();
        
        // Verify that the button is now the active element
        // Send a key press to verify that the element has focus
        $("#target").sendKeys(" "); // Send a space character
        
        // Verify the result shows SPACE was pressed
        assertTrue($("#result").getText().contains("SPACE"), "Button focus didn't work correctly");
    }
    
    @Test
    public void testButtonMethodChaining() {
        // Navigate to the Add/Remove Elements page
        navigateToExample("/add_remove_elements/");
        
        // Arrange - the "Add Element" button
        Button addButton = new Button("button[onclick='addElement()']", "Add Element Button");
        
        // Act - use method chaining
        addButton.clickAndChain().clickAndChain();
        
        // Assert - verify we have two delete buttons
        assertEquals($$(".added-manually").size(), 2, "Method chaining should add two elements");
    }
    
    @Test
    public void testButtonIsEnabled() {
        // Navigate to the Dynamic Controls page
        navigateToExample("/dynamic_controls");
        
        // The "Enable" button should be enabled
        Button enableButton = new Button("button:contains('Enable')", "Enable Button");
        assertTrue(enableButton.isEnabled(), "Enable button should be enabled");
        
        // The input field should be disabled initially
        assertFalse($("#input-example input").isEnabled(), "Input field should be disabled initially");
        
        // Click the enable button
        enableButton.click();
        
        // Wait for the loading animation to complete
        $("#loading").should(disappear);
        
        // Verify the input is now enabled
        assertTrue($("#input-example input").isEnabled(), "Input field should be enabled after clicking button");
    }
    
    @Test(expectedExceptions = ElementNotFound.class)
    public void testClickNonExistentButton() {
        // Navigate to any page
        navigateToExample("/");
        
        // Arrange - a button that doesn't exist
        Button nonExistentButton = new Button("#non-existent-button", "Non-existent Button");
        
        // Act - should throw exception
        nonExistentButton.click();
    }
}
