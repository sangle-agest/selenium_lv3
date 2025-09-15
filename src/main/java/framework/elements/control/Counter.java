package framework.elements.control;

import framework.elements.core.Button;

/**
 * Counter element with increment/decrement buttons
 */
public class Counter {
    private final Button incrementButton;
    private final Button decrementButton;
    private final Button valueContainer;
    private final String valueAttribute;
    
    public Counter(String plusLocator, String minusLocator, String valueLocator, 
                  String valueAttribute, String name) {
        this.incrementButton = new Button(plusLocator, name + " Plus");
        this.decrementButton = new Button(minusLocator, name + " Minus");
        this.valueContainer = new Button(valueLocator, name + " Value");
        this.valueAttribute = valueAttribute;
    }

    /**
     * Get current counter value
     */
    public int getValue() {
        return Integer.parseInt(valueContainer.getAttribute(valueAttribute));
    }

    /**
     * Set counter to specific value
     * @param targetValue Desired value
     */
    public void setValue(int targetValue) {
        int currentValue = getValue();
        
        while (currentValue < targetValue) {
            increment();
            currentValue = getValue();
        }
        
        while (currentValue > targetValue) {
            decrement();
            currentValue = getValue();
        }
    }

    /**
     * Increment counter by 1
     */
    public void increment() {
        incrementButton.waitForClickable();
        incrementButton.click();
    }

    /**
     * Decrement counter by 1
     */
    public void decrement() {
        decrementButton.waitForClickable();
        decrementButton.click();
    }

    /**
     * Check if increment is enabled
     */
    public boolean canIncrement() {
        return incrementButton.isEnabled();
    }

    /**
     * Check if decrement is enabled
     */
    public boolean canDecrement() {
        return decrementButton.isEnabled();
    }
}
