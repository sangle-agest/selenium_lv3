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
        $("[name='q']").shouldBe(Condition.visible).setValue("Selenide").pressEnter();
        
        // Add explicit wait and more robust selector
        $$("#search a").findBy(Condition.text("selenide.org"))
            .shouldBe(Condition.visible, Condition.exist);
    }
}
