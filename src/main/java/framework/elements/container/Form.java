package framework.elements.container;

import framework.elements.core.BaseElement;
import java.util.Map;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.Selenide;

/**
 * Form element wrapper for handling form operations
 */
public class Form extends BaseElement {
    
    public Form(String locator, String name) {
        super(locator, name);
    }

    /**
     * Fill form fields with data
     * @param data Map of field locators and values
     */
    public void fill(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            SelenideElement field = element.$(entry.getKey());
            String tagName = field.getTagName().toLowerCase();
            
            switch (tagName) {
                case "input":
                    String type = field.getAttribute("type");
                    if (type != null) {
                        switch (type.toLowerCase()) {
                            case "checkbox":
                                if (Boolean.parseBoolean(entry.getValue())) {
                                    if (!field.isSelected()) field.click();
                                } else {
                                    if (field.isSelected()) field.click();
                                }
                                break;
                            case "radio":
                                if (Boolean.parseBoolean(entry.getValue())) {
                                    field.click();
                                }
                                break;
                            default:
                                field.setValue(entry.getValue());
                        }
                    }
                    break;
                case "select":
                    field.selectOption(entry.getValue());
                    break;
                case "textarea":
                    field.setValue(entry.getValue());
                    break;
            }
        }
    }

    /**
     * Submit the form
     */
    public void submit() {
        element.submit();
    }

    /**
     * Reset form to default values
     */
    public void reset() {
        element.findAll("input, select, textarea").forEach(field -> {
            String tagName = field.getTagName().toLowerCase();
            if ("input".equals(tagName)) {
                String type = field.getAttribute("type");
                if (type != null) {
                    switch (type.toLowerCase()) {
                        case "checkbox":
                        case "radio":
                            if (field.getAttribute("checked") != null) {
                                field.click();
                            }
                            break;
                        default:
                            field.clear();
                    }
                }
            } else if ("select".equals(tagName)) {
                field.selectOption(0);
            } else if ("textarea".equals(tagName)) {
                field.clear();
            }
        });
    }

    /**
     * Get form data as map
     */
    public Map<String, String> getFormData() {
        Map<String, String> data = new java.util.HashMap<>();
        element.findAll("input, select, textarea").forEach(field -> {
            String name = field.getAttribute("name");
            if (name != null) {
                String value = field.getValue();
                if (value != null) {
                    data.put(name, value);
                }
            }
        });
        return data;
    }

    /**
     * Check if form is valid
     */
    public boolean isValid() {
        return Boolean.parseBoolean(Selenide.executeJavaScript("return arguments[0].checkValidity()", element).toString());
    }

    /**
     * Get validation message
     */
    public String getValidationMessage() {
        return Selenide.executeJavaScript("return arguments[0].validationMessage", element).toString();
    }
}
