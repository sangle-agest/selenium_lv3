package framework.elements.control;

import framework.elements.core.BaseElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

/**
 * DatePicker element wrapper
 */
public class DatePicker extends BaseElement {
    private final String dateFormat;
    private final String dayLocatorFormat;
    
    public DatePicker(String locator, String dayLocatorFormat, String dateFormat, String name) {
        super(locator, name);
        this.dayLocatorFormat = dayLocatorFormat;
        this.dateFormat = dateFormat;
    }

    /**
     * Set date by string (void version)
     * @param dateStr Date string in specified format
     */
    public void setDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormat));
        selectDate(date);
    }
    
    /**
     * Set date by string with method chaining
     * @param dateStr Date string in specified format
     * @return this date picker for method chaining
     */
    public DatePicker setDateAndChain(String dateStr) {
        setDate(dateStr);
        return this;
    }

    /**
     * Set date by LocalDate (void version)
     * @param date LocalDate object
     */
    public void selectDate(LocalDate date) {
        getElement().click(); // Open date picker
        String dayLocator = String.format(dayLocatorFormat, 
            date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        $(dayLocator).click();
    }
    
    /**
     * Set date by LocalDate with method chaining
     * @param date LocalDate object
     * @return this date picker for method chaining
     */
    public DatePicker selectDateAndChain(LocalDate date) {
        selectDate(date);
        return this;
    }

    /**
     * Get selected date as string
     */
    public String getSelectedDate() {
        return getElement().getValue();
    }

    /**
     * Get selected date as LocalDate
     */
    public LocalDate getSelectedLocalDate() {
        return LocalDate.parse(getSelectedDate(), DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * Clear selected date (void version)
     */
    public void clear() {
        getElement().clear();
    }
    
    /**
     * Clear selected date with method chaining
     * @return this date picker for method chaining
     */
    public DatePicker clearAndChain() {
        clear();
        return this;
    }

    /**
     * Check if date is enabled
     */
    public boolean isDateEnabled(LocalDate date) {
        String dayLocator = String.format(dayLocatorFormat, 
            date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        return !$(dayLocator).is(com.codeborne.selenide.Condition.disabled);
    }

    /**
     * Navigate to next month (void version)
     */
    public void nextMonth() {
        getElement().$("[data-action='next']").click();
    }
    
    /**
     * Navigate to next month with method chaining
     * @return this date picker for method chaining
     */
    public DatePicker nextMonthAndChain() {
        nextMonth();
        return this;
    }

    /**
     * Navigate to previous month (void version)
     */
    public void previousMonth() {
        getElement().$("[data-action='previous']").click();
    }
    
    /**
     * Navigate to previous month with method chaining
     * @return this date picker for method chaining
     */
    public DatePicker previousMonthAndChain() {
        previousMonth();
        return this;
    }
}
