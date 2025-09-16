package framework.elements;

import framework.elements.dropdown.Dropdown;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.util.List;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Tests for Dropdown element using The Internet Herokuapp "Dropdown" page
 */
public class DropdownTest extends ElementsTestBase {
    
    @Test
    public void testDropdownSelection() {
        // Navigate to the dropdown example
        navigateToExample("/dropdown");
        
        // Initialize the dropdown using the exact id from HTML
        Dropdown dropdown = new Dropdown("#dropdown", "Dropdown List");
        
        // Get available options and verify count
        List<String> options = dropdown.getAllOptions();
        Assert.assertEquals(options.size(), 3, "Should have 3 options including prompt text");
        
        // Verify the options text is correct
        Assert.assertTrue(options.contains("Please select an option"), "Should contain prompt text");
        Assert.assertTrue(options.contains("Option 1"), "Should contain Option 1");
        Assert.assertTrue(options.contains("Option 2"), "Should contain Option 2");
        
        // Select by visible text
        dropdown.selectByVisibleText("Option 1");
        
        // Add small wait to ensure selection is complete
        sleep(200);
        
        // Verify selection
        String selectedText = dropdown.getSelectedText();
        Assert.assertEquals(selectedText, "Option 1", "Option 1 should be selected");
        
        // Select by value
        dropdown.selectByValue("2");
        
        // Add small wait to ensure selection is complete
        sleep(200);
        
        // Verify selection
        selectedText = dropdown.getSelectedText();
        Assert.assertEquals(selectedText, "Option 2", "Option 2 should be selected");
        
        // Select by index (Option 1 is index 1, as index 0 is the prompt)
        dropdown.selectByIndex(1);
        
        // Add small wait to ensure selection is complete
        sleep(200);
        
        // Verify selection
        selectedText = dropdown.getSelectedText();
        Assert.assertEquals(selectedText, "Option 1", "Option 1 should be selected after selecting by index");
    }
    
    @Test
    public void testDropdownDefaultSelection() {
        // Navigate to the dropdown example
        navigateToExample("/dropdown");
        
        // Initialize the dropdown
        Dropdown dropdown = new Dropdown("#dropdown", "Dropdown List");
        
        // Verify initial selected option
        String initialText = dropdown.getSelectedText();
        Assert.assertEquals(initialText, "Please select an option", 
                "Initial selection should be the prompt text");
    }
    
    @Test
    public void testDropdownMethodChaining() {
        // Navigate to the dropdown example
        navigateToExample("/dropdown");
        
        // Initialize the dropdown
        Dropdown dropdown = new Dropdown("#dropdown", "Dropdown List");
        
        // Use method chaining to select options
        String selectedText = dropdown.selectByIndexAndChain(1).getSelectedText();
        
        // Verify selection
        Assert.assertEquals(selectedText, "Option 1", "Option 1 should be selected after chaining");
        
        // Chain another selection
        selectedText = dropdown.selectByValueAndChain("2").getSelectedText();
        
        // Verify updated selection
        Assert.assertEquals(selectedText, "Option 2", "Option 2 should be selected after second chaining");
    }
    
    @Test
    public void testDropdownValues() {
        // Navigate to the dropdown example
        navigateToExample("/dropdown");
        
        // Initialize the dropdown
        Dropdown dropdown = new Dropdown("#dropdown", "Dropdown List");
        
        // Get all values
        List<String> values = dropdown.getAllValues();
        
        // Verify values
        Assert.assertEquals(values.size(), 3, "Should have 3 values including prompt");
        Assert.assertTrue(values.contains(""), "Should contain empty value for prompt");
        Assert.assertTrue(values.contains("1"), "Should contain value '1'");
        Assert.assertTrue(values.contains("2"), "Should contain value '2'");
        
        // Select by value and verify selected value
        dropdown.selectByValue("1");
        String selectedValue = dropdown.getSelectedValue();
        Assert.assertEquals(selectedValue, "1", "Selected value should be '1'");
    }
}
