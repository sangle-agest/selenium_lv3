package framework.pages.agoda;

import framework.base.BasePage;
import framework.elements.core.*;
import framework.utils.LogUtils;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

/**
 * Agoda Home Page Object
 * Contains all the elements and methods specific to the Agoda home page
 */
public class AgodaHomePage extends BasePage {
    // Constructor with page name
    public AgodaHomePage() {
        super("Agoda Home Page");
    }
    
    // Page elements using improved element wrapper classes
    private final TextBox searchBox = new TextBox("[data-selenium='textInput']", "Search Box");
    
    // Date picker components
    private final Button checkInButton = new Button("[data-element-name='check-in-box']", "Check-in Button");
    
    // Room occupancy components
    private final Button occupancyButton = new Button("[data-element-name='occupancy-box']", "Occupancy Button");
    
    // Search button
    private final Button searchButton = new Button("[data-selenium='searchButton']", "Search Button");

    /**
     * Search for a destination
     * @param destinationText Name of the destination
     */
    public void searchDestination(String destinationText) {
        LogUtils.logAction(this.toString(), "Searching for destination: " + destinationText);
        
        try {
            // First clear and enter the text
            searchBox.clearAndType(destinationText);
            
            // Wait for suggestions to appear
            Selenide.sleep(1000);
            
            // Click the first suggestion
            SelenideElement firstSuggestion = $("[data-selenium='autosuggest-item']");
            firstSuggestion.click();
            
            LogUtils.logSuccess(this.toString(), "Selected destination: " + destinationText);
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to search for destination: " + destinationText, e);
            throw e;
        }
    }

    /**
     * Set check-in and check-out dates
     * @param checkInOffset Days from today for check-in
     * @param checkOutOffset Days from today for check-out
     */
    public void setDates(int checkInOffset, int checkOutOffset) {
        LogUtils.logAction(this.toString(), "Setting dates with check-in offset: " + checkInOffset + ", check-out offset: " + checkOutOffset);
        
        try {
            // Click on the check-in box to open the date picker
            checkInButton.click();
            Selenide.sleep(1000);
            
            // Try different selectors for the calendar since the website might have changed
            // Find all days that are clickable (not disabled)
            ElementCollection availableDays = new ElementCollection("[data-selenium='calendar-day']:not([data-selenium-disable])", "Available Days");
            if (availableDays.size() == 0) {
                availableDays = new ElementCollection(".DayPicker-Day:not(.DayPicker-Day--disabled)", "Available Days");
            }
            
            if (availableDays.size() == 0) {
                LogUtils.logWarning(this.toString(), "No available days found using common selectors, trying generic approach");
                // Just try to click any visible date elements that look like dates
                executeJavaScript(
                    "document.querySelectorAll('[data-date]:not([disabled])').length > " + (checkInOffset + 1) + 
                    " ? document.querySelectorAll('[data-date]:not([disabled])')[" + checkInOffset + "].click() : null"
                );
                Selenide.sleep(500);
                executeJavaScript(
                    "document.querySelectorAll('[data-date]:not([disabled])').length > " + (checkOutOffset + 1) + 
                    " ? document.querySelectorAll('[data-date]:not([disabled])')[" + checkOutOffset + "].click() : null"
                );
            } else {
                // Click check-in date
                if (checkInOffset < availableDays.size()) {
                    availableDays.get(checkInOffset).click();
                    Selenide.sleep(500);
                    
                    // Click check-out date
                    if (checkOutOffset < availableDays.size()) {
                        availableDays.get(checkOutOffset).click();
                    } else {
                        LogUtils.logWarning(this.toString(), "Check-out offset exceeds available days, using last available date");
                        availableDays.last().click();
                    }
                } else {
                    LogUtils.logWarning(this.toString(), "Check-in offset exceeds available days, using first available date");
                    availableDays.first().click();
                    Selenide.sleep(500);
                    int lastIndex = Math.min(5, availableDays.size() - 1);
                    availableDays.get(lastIndex > 0 ? lastIndex : 0).click();
                }
            }
            
            LogUtils.logSuccess(this.toString(), "Dates set successfully");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to set dates", e);
            throw e;
        }
    }

    /**
     * Set room occupancy
     * @param adults Number of adults
     * @param children Number of children
     */
    public void setOccupancy(int adults, int children) {
        LogUtils.logAction(this.toString(), "Setting occupancy: " + adults + " adults, " + children + " children");
        
        try {
            // Open occupancy panel
            occupancyButton.click();
            Selenide.sleep(1000);
            
            // For the new Agoda interface, we need to interact with the popup directly
            // We'll use JavaScript to set the values since the interface might be complex
            String setOccupancyScript = 
                "function setOccupancy(adults, children) {\n" +
                "    // First find all buttons in the occupancy popup\n" +
                "    const adultPlusBtn = Array.from(document.querySelectorAll('button')).find(btn => \n" +
                "        btn.textContent.includes('+') && \n" +
                "        (btn.closest('[aria-label*=\"adult\"]') || btn.closest('[data-selenium*=\"adult\"]')));\n" +
                "    const adultMinusBtn = Array.from(document.querySelectorAll('button')).find(btn => \n" +
                "        btn.textContent.includes('-') && \n" +
                "        (btn.closest('[aria-label*=\"adult\"]') || btn.closest('[data-selenium*=\"adult\"]')));\n" +
                "    const childPlusBtn = Array.from(document.querySelectorAll('button')).find(btn => \n" +
                "        btn.textContent.includes('+') && \n" +
                "        (btn.closest('[aria-label*=\"child\"]') || btn.closest('[data-selenium*=\"child\"]')));\n" +
                "    \n" +
                "    // Get current adults value\n" +
                "    let currentAdults = 2; // Default\n" +
                "    const adultValueEl = document.querySelector('[data-selenium=\"adultValue\"]');\n" +
                "    if (adultValueEl && adultValueEl.getAttribute('data-value')) {\n" +
                "        currentAdults = parseInt(adultValueEl.getAttribute('data-value'));\n" +
                "    }\n" +
                "    \n" +
                "    // Adjust adults\n" +
                "    if (currentAdults < adults) {\n" +
                "        for (let i = currentAdults; i < adults; i++) {\n" +
                "            if (adultPlusBtn) adultPlusBtn.click();\n" +
                "        }\n" +
                "    } else if (currentAdults > adults) {\n" +
                "        for (let i = currentAdults; i > adults; i--) {\n" +
                "            if (adultMinusBtn) adultMinusBtn.click();\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    // Add children\n" +
                "    if (children > 0 && childPlusBtn) {\n" +
                "        for (let i = 0; i < children; i++) {\n" +
                "            childPlusBtn.click();\n" +
                "        }\n" +
                "        \n" +
                "        // Handle child age selection if needed\n" +
                "        setTimeout(() => {\n" +
                "            const ageSelects = document.querySelectorAll('select[aria-label*=\"child\"], select[data-selenium*=\"child\"]');\n" +
                "            if (ageSelects.length > 0) {\n" +
                "                Array.from(ageSelects).forEach(select => {\n" +
                "                    select.value = '7';\n" +
                "                    select.dispatchEvent(new Event('change'));\n" +
                "                });\n" +
                "            }\n" +
                "        }, 500);\n" +
                "    }\n" +
                "    \n" +
                "    // Click Done/Apply button\n" +
                "    setTimeout(() => {\n" +
                "        const doneBtn = Array.from(document.querySelectorAll('button')).find(btn => \n" +
                "            btn.textContent.includes('Done') || \n" +
                "            btn.textContent.includes('Apply') || \n" +
                "            btn.closest('[data-selenium=\"occupancy-apply\"]'));\n" +
                "        if (doneBtn) doneBtn.click();\n" +
                "    }, 1000);\n" +
                "}\n" +
                "\n" +
                "setOccupancy(" + adults + ", " + children + ");";
            
            // Execute the JavaScript to set occupancy
            executeJavaScript(setOccupancyScript);
            
            // Wait for the occupancy popup to close
            Selenide.sleep(2000);
            
            LogUtils.logSuccess(this.toString(), "Occupancy set successfully using JavaScript");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to set occupancy", e);
            throw e;
        }
    }
    
    /**
     * Perform search with current criteria
     */
    public void clickSearch() {
        LogUtils.logAction(this.toString(), "Clicking search button");
        try {
            // The search button might be different in the actual website
            // So let's try a few options
            SelenideElement searchBtn = $("[data-selenium='searchButton']");
            if (!searchBtn.exists()) {
                searchBtn = $("[data-element-name='search-button']");
                if (!searchBtn.exists()) {
                    searchBtn = $("button[type='submit']");
                }
            }
            
            searchBtn.click();
            LogUtils.logSuccess(this.toString(), "Search button clicked successfully");
        } catch (Exception e) {
            LogUtils.logError(this.toString(), "Failed to click search button", e);
            throw e;
        }
    }

    /**
     * Get current adult count
     */
    public int getAdultCount() {
        try {
            String adultText = $("[data-selenium='adultValue']").getAttribute("data-value");
            if (adultText != null && !adultText.isEmpty()) {
                return Integer.parseInt(adultText);
            }
            return 2; // Default value
        } catch (Exception e) {
            LogUtils.logWarning(this.toString(), "Failed to get adult count, returning default value");
            return 2; // Default value
        }
    }

    /**
     * Get current child count
     */
    public int getChildCount() {
        try {
            String childText = $("[data-selenium='childValue']").getAttribute("data-value");
            if (childText != null && !childText.isEmpty()) {
                return Integer.parseInt(childText);
            }
            return 0; // Default value
        } catch (Exception e) {
            LogUtils.logWarning(this.toString(), "Failed to get child count, returning default value");
            return 0; // Default value
        }
    }
}
