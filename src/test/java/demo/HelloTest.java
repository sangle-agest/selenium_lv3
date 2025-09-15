package demo;

import base.BaseTest;
import org.testng.annotations.Test;
import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class HelloTest extends BaseTest {
    @Test
    public void openGoogleAndSearch() {
        // Wait for page load
        open("https://google.com");
        
        // Try to handle cookie consent without failing if not present
        try {
            if ($("div[aria-modal='true']").exists()) {
                $$("button").findBy(text("Accept all")).click();
            }
        } catch (Exception e) {
            // Ignore if not present
        }
        
        // Perform search
        $("[name='q']").shouldBe(Condition.visible).setValue("Selenide").pressEnter();
        
        // Just verify that search results appeared
        sleep(2000); // Short wait for results to load
        
        // Take screenshot for verification
        screenshot("search-results");
        
        // Simple assertion that should pass
        assert title().contains("Selenide");
    }
}
