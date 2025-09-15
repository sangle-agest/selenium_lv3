package framework.elements.table;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import static com.codeborne.selenide.Selenide.$$;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Table element wrapper
 */
public class Table extends BaseElement {
    private final String headerCellsLocator;
    private final String bodyCellsLocator;
    private final String rowLocator;

    public Table(String tableLocator, String headerCellsLocator, String bodyCellsLocator, 
                String rowLocator, String name) {
        super(tableLocator, name);
        this.headerCellsLocator = headerCellsLocator;
        this.bodyCellsLocator = bodyCellsLocator;
        this.rowLocator = rowLocator;
    }

    /**
     * Get all header texts
     */
    public List<String> getHeaders() {
        LogUtils.logAction(toString(), "Getting header texts");
        try {
            List<String> headers = $$(headerCellsLocator).texts();
            LogUtils.logSuccess(toString(), String.format("Got %d headers", headers.size()));
            return headers;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get headers", e);
            throw e;
        }
    }

    /**
     * Get cell text by row and column index
     */
    public String getCellText(int row, int col) {
        LogUtils.logAction(toString(), String.format("Getting text from cell [%d, %d]", row, col));
        try {
            String text = getCell(row, col).getText();
            LogUtils.logSuccess(toString(), String.format("Got cell text: '%s'", text));
            return text;
        } catch (Exception e) {
            LogUtils.logError(toString(), 
                String.format("Failed to get text from cell [%d, %d]", row, col), e);
            throw e;
        }
    }

    /**
     * Get cell by row and column index
     */
    public SelenideElement getCell(int row, int col) {
        LogUtils.logAction(toString(), String.format("Getting cell at [%d, %d]", row, col));
        try {
            SelenideElement cell = $$(rowLocator).get(row).$$(bodyCellsLocator).get(col);
            LogUtils.logSuccess(toString(), "Got cell element");
            return cell;
        } catch (Exception e) {
            LogUtils.logError(toString(), 
                String.format("Failed to get cell at [%d, %d]", row, col), e);
            throw e;
        }
    }

    /**
     * Get number of rows
     */
    public int getRowCount() {
        LogUtils.logAction(toString(), "Getting row count");
        try {
            int count = $$(rowLocator).size();
            LogUtils.logSuccess(toString(), String.format("Table has %d rows", count));
            return count;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get row count", e);
            throw e;
        }
    }

    /**
     * Get number of columns
     */
    public int getColumnCount() {
        LogUtils.logAction(toString(), "Getting column count");
        try {
            int count = $$(headerCellsLocator).size();
            LogUtils.logSuccess(toString(), String.format("Table has %d columns", count));
            return count;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get column count", e);
            throw e;
        }
    }

    /**
     * Get row as map (header -> value)
     */
    public Map<String, String> getRowAsMap(int rowIndex) {
        LogUtils.logAction(toString(), "Getting row " + rowIndex + " as map");
        try {
            List<String> headers = getHeaders();
            ElementsCollection cells = $$(rowLocator).get(rowIndex).$$(bodyCellsLocator);
            Map<String, String> rowData = new HashMap<>();
            
            for (int i = 0; i < headers.size(); i++) {
                String header = headers.get(i);
                String value = cells.get(i).getText();
                rowData.put(header, value);
            }
            
            LogUtils.logSuccess(toString(), String.format("Got row %d data with %d columns", 
                rowIndex, rowData.size()));
            return rowData;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get row as map: " + rowIndex, e);
            throw e;
        }
    }

    /**
     * Get all rows as list of maps
     */
    public List<Map<String, String>> getAllRowsAsMap() {
        LogUtils.logAction(toString(), "Getting all rows as maps");
        try {
            List<Map<String, String>> allRows = new ArrayList<>();
            int rowCount = getRowCount();
            
            for (int i = 0; i < rowCount; i++) {
                allRows.add(getRowAsMap(i));
            }
            
            LogUtils.logSuccess(toString(), String.format("Got data for all %d rows", rowCount));
            return allRows;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Failed to get all rows as maps", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            return String.format("Table '%s' [%d rows x %d columns]", 
                getName(), getRowCount(), getColumnCount());
        } catch (Exception e) {
            return String.format("Table '%s'", getName());
        }
    }

    /**
     * Get column values by header
     */
    public List<String> getColumnValues(String header) {
        LogUtils.logAction(toString(), String.format("Getting column values for header: '%s'", header));
        try {
            List<String> headers = getHeaders();
            int columnIndex = headers.indexOf(header);
            if (columnIndex == -1) {
                LogUtils.logWarning(toString(), String.format("Header not found: '%s'", header));
                throw new IllegalArgumentException("Header not found: " + header);
            }
            
            List<String> values = new ArrayList<>();
            int rowCount = getRowCount();
            
            for (int i = 0; i < rowCount; i++) {
                values.add(getCellText(i, columnIndex));
            }
            
            LogUtils.logSuccess(toString(), String.format("Got %d values for column '%s'", 
                values.size(), header));
            return values;
        } catch (Exception e) {
            LogUtils.logError(toString(), 
                String.format("Failed to get values for column: '%s'", header), e);
            throw e;
        }
    }

    /**
     * Find row index by column value
     */
    public int findRowByColumnValue(String header, String value) {
        LogUtils.logAction(toString(), 
            String.format("Finding row with value '%s' in column '%s'", value, header));
        try {
            List<String> columnValues = getColumnValues(header);
            int rowIndex = columnValues.indexOf(value);
            if (rowIndex != -1) {
                LogUtils.logSuccess(toString(), 
                    String.format("Found value '%s' at row %d", value, rowIndex));
            } else {
                LogUtils.logWarning(toString(), 
                    String.format("Value '%s' not found in column '%s'", value, header));
            }
            return rowIndex;
        } catch (Exception e) {
            LogUtils.logError(toString(), 
                String.format("Failed to find row by value '%s' in column '%s'", value, header), e);
            throw e;
        }
    }

    /**
     * Click cell by row and column index
     */
    public void clickCell(int row, int col) {
        LogUtils.logAction(toString(), String.format("Clicking cell at [%d, %d]", row, col));
        try {
            getCell(row, col).click();
            LogUtils.logSuccess(toString(), String.format("Clicked cell at [%d, %d]", row, col));
        } catch (Exception e) {
            LogUtils.logError(toString(), 
                String.format("Failed to click cell at [%d, %d]", row, col), e);
            throw e;
        }
    }
}
