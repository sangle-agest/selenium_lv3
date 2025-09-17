# JSON-Based Locator Management System

## Overview

This framework enhancement stores page element locators in JSON files instead of hardcoding them in Page Object classes. The JSON files are organized by application (Agoda, Vietjet, etc.) and stored in the `src/main/resources/locators` directory.

## Directory Structure

```
src/main/resources/
└── locators/
    ├── agoda/
    │   └── agoda_locators.json
    └── vietjet/
        └── vietjet_locators.json
```

## Benefits

1. **Centralized Locator Management**: All locators are stored in organized JSON files, making them easier to maintain.
2. **Reduced Duplication**: Locators used in multiple pages can be stored in a common section of a JSON file.
3. **Easier Updates**: When a locator changes, you only need to update the JSON file, not multiple Page Object classes.
4. **Better Collaboration**: Developers and QA engineers can easily review and update locators without modifying code.
5. **Simplified Page Objects**: Page Object classes become cleaner and more focused on behavior rather than element definitions.

## JSON Structure

Locators are organized by page, with each page containing element definitions. Each element has a name, locator, and type.

```json
{
  "PageName": {
    "elementName": {
      "name": "Human-readable name",
      "locator": "css or xpath selector",
      "type": "Element type (Button, TextBox, etc.)"
    }
  }
}
```

## Using Locators in Page Objects

The `LocatorManager` utility class provides methods to load and use locators from JSON files:

```java
// Define constants for locator file and page name
private static final String LOCATOR_FILE = "locators/agoda/agoda_locators.json";
private static final String PAGE_NAME = "AgodaHomePage";

// Create LocatorManager instance and load locators
private final LocatorManager locatorManager = LocatorManager.getInstance();
private final JsonObject pageLocators = locatorManager.getPageLocators(PAGE_NAME, LOCATOR_FILE);

// Create elements from JSON definitions
private final Button loginButton = (Button) locatorManager.createElement(pageLocators, "loginButton");
```

## Example Implementations

1. **Agoda Page Objects**: Located in `framework.pages.agoda` package
   - `AgodaHomePageJSON.java`: Uses locators from `locators/agoda/agoda_locators.json`

2. **Vietjet Page Objects**: Located in `framework.pages.vietjet` package
   - `VietjetHomePageJSON.java`: Uses locators from `locators/vietjet/vietjet_locators.json`

## Adding New Applications

To add locators for a new application:

1. Create a new directory in `src/main/resources/locators/` for the application
2. Create a JSON file with the application's locators
3. Create Page Object classes that use the LocatorManager to load and use these locators

## Detailed Documentation

For more detailed information on how to use this system, please refer to:
- `JSON-LOCATOR-GUIDE.md`: Comprehensive guide on using the JSON-based locator system