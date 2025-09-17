package framework.elements;

import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Debug test for dropdown to understand the issue
 */
public class DropdownDebugTest extends ElementsTestBase {
    
    @Test
    public void debugDropdownSelection() {
        // Navigate to the dropdown example
        navigateToExample("/dropdown");
        
        // Get the initial selection text
        String initialText = $("#dropdown").getSelectedOption().getText();
        System.out.println("Before selection: " + initialText);
        
        // Select option 1
        $("#dropdown").selectOption("Option 1");
        
        // Get the selected value
        String selectedValue = $("#dropdown").getValue();
        System.out.println("Selected value: " + selectedValue);
        
        // Get the selected text
        String selectedText = $("#dropdown").getSelectedOption().getText();
        System.out.println("Selected text: " + selectedText);
    }
}
