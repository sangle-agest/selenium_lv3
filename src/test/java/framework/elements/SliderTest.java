package framework.elements;

import framework.elements.control.Slider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static com.codeborne.selenide.Selenide.$;

/**
 * Tests for Slider element using The Internet Herokuapp "Horizontal Slider" page
 */
public class SliderTest extends ElementsTestBase {
    
    @Test
    public void testSliderSlideTo() {
        // Navigate to the horizontal slider example
        navigateToExample("/horizontal_slider");
        
        // Arrange
        Slider slider = new Slider("input[type='range']", "Horizontal Slider");
        
        // Act - move slider to 80% (range is 0 to 5, so 80% is about 4)
        slider.slideTo(80);
        
        // Assert - verify the slider value through the displayed range value
        String sliderValue = $("#range").getText();
        assertEquals(sliderValue, "4", "Slider should be at value 4");
    }
    
    @Test
    public void testSliderMinMax() {
        // Navigate to the horizontal slider example
        navigateToExample("/horizontal_slider");
        
        // Arrange
        Slider slider = new Slider("input[type='range']", "Horizontal Slider");
        
        // Act - move slider to minimum value (0%)
        slider.slideTo(0);
        
        // Assert - verify the slider value
        String minValue = $("#range").getText();
        assertEquals(minValue, "0", "Slider should be at minimum value");
        
        // Act - move slider to maximum value (100%)
        slider.slideTo(100);
        
        // Assert - verify the slider value
        String maxValue = $("#range").getText();
        assertEquals(maxValue, "5", "Slider should be at maximum value");
    }
    
    @Test
    public void testSliderMoveByOffset() {
        // Navigate to the horizontal slider example
        navigateToExample("/horizontal_slider");
        
        // Arrange
        Slider slider = new Slider("input[type='range']", "Horizontal Slider");
        
        // First set to middle position
        slider.slideTo(50);
        String initialValue = $("#range").getText();
        
        // Act - move slider by positive offset
        slider.moveByOffset(30);
        
        // Assert - verify the value increased
        String newValue = $("#range").getText();
        assertTrue(Double.parseDouble(newValue) > Double.parseDouble(initialValue), 
                "Slider value should increase after positive offset");
        
        // Act - move slider by negative offset
        slider.moveByOffset(-60);
        
        // Assert - verify the value decreased
        String finalValue = $("#range").getText();
        assertTrue(Double.parseDouble(finalValue) < Double.parseDouble(newValue), 
                "Slider value should decrease after negative offset");
    }
    
    @Test
    public void testSliderMethodChaining() {
        // Navigate to the horizontal slider example
        navigateToExample("/horizontal_slider");
        
        // Arrange
        Slider slider = new Slider("input[type='range']", "Horizontal Slider");
        
        // Act - use method chaining
        slider.slideToAndChain(30).moveByOffsetAndChain(20);
        
        // Assert - verify the slider has moved
        String value = $("#range").getText();
        assertTrue(Double.parseDouble(value) > 0, 
                "Slider value should be greater than 0 after chained operations");
    }
    
    @Test
    public void testSliderValueRetrieval() {
        // Navigate to the horizontal slider example
        navigateToExample("/horizontal_slider");
        
        // Arrange
        Slider slider = new Slider("input[type='range']", "Horizontal Slider");
        
        // Act - set to a specific value
        slider.slideTo(50);
        
        // Assert - verify getValue matches the displayed range
        String sliderValue = slider.getValue();
        String displayedValue = $("#range").getText();
        assertEquals(sliderValue, displayedValue, 
                "Slider getValue() should match displayed range value");
        
        // Verify min/max values
        assertEquals(slider.getMin(), "0", "Minimum value should be 0");
        assertEquals(slider.getMax(), "5", "Maximum value should be 5");
    }
}
