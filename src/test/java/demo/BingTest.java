package demo;

import base.BaseTest;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;

public class BingTest extends BaseTest {
    
    @Test
    public void openBingAndSearch() {
        open("https://bing.com");
        // Wait for page load and use alternative selectors
        $("#sb_form_q, input[name='q'], .search-box").shouldBe(Condition.visible)
            .setValue("Selenide")
            .pressEnter();
        // Update selector for results
        $$(".b_algo h2").findBy(Condition.text("selenide.org")).shouldBe(Condition.visible);
    }
}