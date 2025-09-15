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
     * Set date by string
     * @param dateStr Date string in specified format
     */
    public void setDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormat));
        selectDate(date);
    }

    /**
     * Set date by LocalDate
     * @param date LocalDate object
     */
    public void selectDate(LocalDate date) {
        element.click(); // Open date picker
        String dayLocator = String.format(dayLocatorFormat, 
            date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        $(dayLocator).click();
    }

    /**
     * Get selected date as string
     */
    public String getSelectedDate() {
        return element.getValue();
    }

    /**
     * Get selected date as LocalDate
     */
    public LocalDate getSelectedLocalDate() {
        return LocalDate.parse(getSelectedDate(), DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * Clear selected date
     */
    public void clear() {
        element.clear();
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
     * Navigate to next month
     */
    public void nextMonth() {
        element.$("[data-action='next']").click();
    }

    /**
     * Navigate to previous month
     */
    public void previousMonth() {
        element.$("[data-action='previous']").click();
    }
}
