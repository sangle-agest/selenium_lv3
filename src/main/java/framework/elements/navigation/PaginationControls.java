package framework.elements.navigation;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Pagination controls element wrapper
 */
public class PaginationControls extends BaseElement {
    private final String pageButtonsLocator;
    private final String nextButtonLocator;
    private final String previousButtonLocator;
    private final String activePageLocator;
    
    /**
     * Constructor with all pagination elements
     */
    public PaginationControls(String containerLocator, String pageButtonsLocator, 
                            String nextButtonLocator, String previousButtonLocator,
                            String activePageLocator, String name) {
        super(containerLocator, name);
        this.pageButtonsLocator = pageButtonsLocator;
        this.nextButtonLocator = nextButtonLocator;
        this.previousButtonLocator = previousButtonLocator;
        this.activePageLocator = activePageLocator;
    }
    
    /**
     * Navigate to specific page number
     */
    public void goToPage(int pageNumber) {
        LogUtils.logAction(toString(), "Going to page " + pageNumber);
        try {
            ElementsCollection pageButtons = $$(pageButtonsLocator);
            
            if (pageNumber <= 0 || pageNumber > pageButtons.size()) {
                LogUtils.logWarning(toString(), "Invalid page number: " + pageNumber);
                throw new IllegalArgumentException("Invalid page number: " + pageNumber);
            }
            
            // Page numbers in UI are usually 1-based, but collection is 0-based
            pageButtons.get(pageNumber - 1).click();
            LogUtils.logSuccess(toString(), "Navigated to page " + pageNumber);
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to go to page " + pageNumber, e);
            throw e;
        }
    }
    
    /**
     * Go to next page
     */
    public void nextPage() {
        LogUtils.logAction(toString(), "Going to next page");
        try {
            SelenideElement nextButton = getElement().$(nextButtonLocator);
            
            if (!nextButton.isEnabled()) {
                LogUtils.logWarning(toString(), "Next button is disabled - already on last page");
                return;
            }
            
            int currentPage = getCurrentPage();
            nextButton.click();
            LogUtils.logSuccess(toString(), "Navigated from page " + currentPage + " to page " + (currentPage + 1));
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to go to next page", e);
            throw e;
        }
    }
    
    /**
     * Go to next page with method chaining
     * @return this PaginationControls for method chaining
     */
    public PaginationControls nextPageAndChain() {
        nextPage();
        return this;
    }
    
    /**
     * Go to previous page
     */
    public void previousPage() {
        LogUtils.logAction(toString(), "Going to previous page");
        try {
            SelenideElement prevButton = getElement().$(previousButtonLocator);
            
            if (!prevButton.isEnabled()) {
                LogUtils.logWarning(toString(), "Previous button is disabled - already on first page");
                return;
            }
            
            int currentPage = getCurrentPage();
            prevButton.click();
            LogUtils.logSuccess(toString(), "Navigated from page " + currentPage + " to page " + (currentPage - 1));
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to go to previous page", e);
            throw e;
        }
    }
    
    /**
     * Go to previous page with method chaining
     * @return this PaginationControls for method chaining
     */
    public PaginationControls previousPageAndChain() {
        previousPage();
        return this;
    }
    
    /**
     * Get current page number
     */
    public int getCurrentPage() {
        LogUtils.logAction(toString(), "Getting current page number");
        try {
            String pageText = getElement().$(activePageLocator).getText().trim();
            int currentPage;
            
            // Try to parse the page number from text
            try {
                currentPage = Integer.parseInt(pageText);
            } catch (NumberFormatException e) {
                // If the active page element contains text like "Page 3 of 10"
                String[] parts = pageText.split("\\s+");
                for (String part : parts) {
                    try {
                        currentPage = Integer.parseInt(part);
                        break;
                    } catch (NumberFormatException ex) {
                        // Continue trying other parts
                    }
                }
                // If still not found, get the index of the active element
                ElementsCollection pageButtons = $$(pageButtonsLocator);
                for (int i = 0; i < pageButtons.size(); i++) {
                    if (pageButtons.get(i).getAttribute("class").contains("active")) {
                        currentPage = i + 1; // Convert to 1-based index
                        break;
                    }
                }
                // Default to 1 if nothing else works
                currentPage = 1;
            }
            
            LogUtils.logSuccess(toString(), "Current page: " + currentPage);
            return currentPage;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get current page", e);
            throw e;
        }
    }
    
    /**
     * Get total number of pages
     */
    public int getTotalPages() {
        LogUtils.logAction(toString(), "Getting total number of pages");
        try {
            ElementsCollection pageButtons = $$(pageButtonsLocator);
            int totalPages = pageButtons.size();
            LogUtils.logSuccess(toString(), "Total pages: " + totalPages);
            return totalPages;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get total pages", e);
            throw e;
        }
    }
    
    /**
     * Check if has next page
     */
    public boolean hasNextPage() {
        LogUtils.logAction(toString(), "Checking if has next page");
        try {
            SelenideElement nextButton = getElement().$(nextButtonLocator);
            boolean hasNext = nextButton.isDisplayed() && nextButton.isEnabled();
            LogUtils.logSuccess(toString(), "Has next page: " + hasNext);
            return hasNext;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check if has next page", e);
            throw e;
        }
    }
    
    /**
     * Check if has previous page
     */
    public boolean hasPreviousPage() {
        LogUtils.logAction(toString(), "Checking if has previous page");
        try {
            SelenideElement prevButton = getElement().$(previousButtonLocator);
            boolean hasPrev = prevButton.isDisplayed() && prevButton.isEnabled();
            LogUtils.logSuccess(toString(), "Has previous page: " + hasPrev);
            return hasPrev;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to check if has previous page", e);
            throw e;
        }
    }
    
    /**
     * Go to first page
     */
    public void goToFirstPage() {
        LogUtils.logAction(toString(), "Going to first page");
        try {
            goToPage(1);
            LogUtils.logSuccess(toString(), "Navigated to first page");
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to go to first page", e);
            throw e;
        }
    }
    
    /**
     * Go to first page with method chaining
     * @return this PaginationControls for method chaining
     */
    public PaginationControls goToFirstPageAndChain() {
        goToFirstPage();
        return this;
    }
    
    /**
     * Go to last page
     */
    public void goToLastPage() {
        LogUtils.logAction(toString(), "Going to last page");
        try {
            int totalPages = getTotalPages();
            goToPage(totalPages);
            LogUtils.logSuccess(toString(), "Navigated to last page: " + totalPages);
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to go to last page", e);
            throw e;
        }
    }
    
    /**
     * Go to last page with method chaining
     * @return this PaginationControls for method chaining
     */
    public PaginationControls goToLastPageAndChain() {
        goToLastPage();
        return this;
    }
    
    @Override
    public String toString() {
        try {
            return String.format("PaginationControls '%s' [%s] {current: %d, total: %d}", 
                getName(), getLocator(), getCurrentPage(), getTotalPages());
        } catch (Exception e) {
            return String.format("PaginationControls '%s' [%s]", getName(), getLocator());
        }
    }
}
