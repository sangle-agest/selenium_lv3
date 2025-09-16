package framework.elements;

import framework.elements.selectable.CheckBox;
import org.testng.annotations.Test;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$;

/**
 * Tests for CheckBox element using The Internet Herokuapp "Checkboxes" page
 */
public class CheckBoxTest extends ElementsTestBase {
    
    @Test
    public void testCheckBoxCheck() {
        // Navigate to the checkboxes example
        navigateToExample("/checkboxes");
        
        // Initialize the first checkbox which is unchecked by default
        CheckBox checkbox1 = new CheckBox("input[type='checkbox']:nth-of-type(1)", "Checkbox 1");
        
        // Verify initial state
        Assert.assertFalse(checkbox1.isChecked(), "Checkbox 1 should be unchecked initially");
        
        // Check the checkbox
        checkbox1.check();
        
        // Verify checkbox is checked
        Assert.assertTrue(checkbox1.isChecked(), "Checkbox 1 should be checked after check() operation");
        
        // Try to check again (should be idempotent)
        checkbox1.check();
        
        // Verify still checked
        Assert.assertTrue(checkbox1.isChecked(), "Checkbox 1 should still be checked after second check() operation");
    }
    
    @Test
    public void testCheckBoxUncheck() {
        // Navigate to the checkboxes example
        navigateToExample("/checkboxes");
        
        // Initialize the second checkbox which is checked by default
        CheckBox checkbox2 = new CheckBox("input[type='checkbox']:nth-of-type(2)", "Checkbox 2");
        
        // Verify initial state
        Assert.assertTrue(checkbox2.isChecked(), "Checkbox 2 should be checked initially");
        
        // Uncheck the checkbox
        checkbox2.uncheck();
        
        // Verify checkbox is unchecked
        Assert.assertFalse(checkbox2.isChecked(), "Checkbox 2 should be unchecked after uncheck() operation");
        
        // Try to uncheck again (should be idempotent)
        checkbox2.uncheck();
        
        // Verify still unchecked
        Assert.assertFalse(checkbox2.isChecked(), "Checkbox 2 should still be unchecked after second uncheck() operation");
    }
    
    @Test
    public void testCheckBoxToggle() {
        // Navigate to the checkboxes example
        navigateToExample("/checkboxes");
        
        // Initialize both checkboxes
        CheckBox checkbox1 = new CheckBox("input[type='checkbox']:nth-of-type(1)", "Checkbox 1");
        CheckBox checkbox2 = new CheckBox("input[type='checkbox']:nth-of-type(2)", "Checkbox 2");
        
        // Verify initial states
        boolean initialState1 = checkbox1.isChecked();
        boolean initialState2 = checkbox2.isChecked();
        
        // Toggle both checkboxes
        checkbox1.toggle();
        checkbox2.toggle();
        
        // Verify states are toggled
        Assert.assertNotEquals(initialState1, checkbox1.isChecked(), 
                "Checkbox 1 state should be toggled");
        Assert.assertNotEquals(initialState2, checkbox2.isChecked(), 
                "Checkbox 2 state should be toggled");
    }
    
    @Test
    public void testCheckBoxMethodChaining() {
        // Navigate to the checkboxes example
        navigateToExample("/checkboxes");
        
        // Initialize both checkboxes
        CheckBox checkbox1 = new CheckBox("input[type='checkbox']:nth-of-type(1)", "Checkbox 1");
        
        // Use method chaining to check and then uncheck
        checkbox1.checkAndChain().uncheckAndChain();
        
        // Verify final state
        Assert.assertFalse(checkbox1.isChecked(), 
                "Checkbox 1 should be unchecked after chain operations");
    }
}
