# Selenium Test Automation Framework

A comprehensive and modular Selenium/Selenide-based test automation framework with enhanced element wrappers, utilities, and logging capabilities.

## Overview

This framework is built on top of Selenide and provides additional layers of abstraction, logging, and utility functions to make test automation more robust, maintainable, and readable.

## Key Features

- **Enhanced Element Wrappers**: Custom element classes with built-in logging and error handling
- **Page Object Model**: Well-structured page objects with intuitive APIs
- **Comprehensive Logging**: Four-level logging system (Action, Success, Warning, Error)
- **Utility Classes**: Reusable utilities for common operations
- **Modular Architecture**: Clean separation of concerns and easy extensibility
- **TestNG Integration**: Flexible test configuration and parallelization

## Framework Structure

```
src/
├── main/
│   └── java/
│       └── framework/
│           ├── base/
│           │   └── BasePage.java
│           ├── elements/
│           │   ├── core/
│           │   │   ├── BaseElement.java
│           │   │   ├── Button.java
│           │   │   ├── ElementCollection.java
│           │   │   ├── Label.java
│           │   │   ├── Link.java
│           │   │   └── TextBox.java
│           │   ├── container/
│           │   │   ├── Form.java
│           │   │   ├── Frame.java
│           │   │   ├── Modal.java
│           │   │   └── Panel.java
│           │   ├── control/
│           │   │   ├── Counter.java
│           │   │   ├── DatePicker.java
│           │   │   └── ProgressBar.java
│           │   ├── dropdown/
│           │   │   └── AutoCompleteBox.java
│           │   ├── media/
│           │   │   ├── Icon.java
│           │   │   ├── Image.java
│           │   │   └── Tooltip.java
│           │   ├── navigation/
│           │   │   ├── Breadcrumbs.java
│           │   │   └── PaginationControls.java
│           │   └── selectable/
│           │       ├── CheckBox.java
│           │       └── ToggleSwitch.java
│           ├── pages/
│           │   └── agoda/
│           │       ├── AgodaHomePage.java
│           │       ├── HotelDetailsPage.java
│           │       └── SearchResultsPage.java
│           └── utils/
│               ├── ConfigManager.java
│               └── LogUtils.java
└── test/
    ├── java/
    │   ├── base/
    │   │   └── BaseTest.java
    │   └── tests/
    │       └── agoda/
    │           └── AgodaHotelSearchTest.java
    └── resources/
        ├── config.properties
        └── testng.xml
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Chrome or Firefox browser

### Setup

1. Clone the repository
2. Install dependencies: `mvn clean install -DskipTests`
3. Run tests: `mvn test`

## Writing Tests

### Step 1: Create Page Objects

```java
public class LoginPage extends BasePage {
    private final TextBox usernameField = new TextBox("#username", "Username Field");
    private final TextBox passwordField = new TextBox("#password", "Password Field");
    private final Button loginButton = new Button("#loginBtn", "Login Button");

    public void login(String username, String password) {
        usernameField.setValue(username);
        passwordField.setValue(password);
        loginButton.click();
    }
}
```

### Step 2: Create Test Classes

```java
@Test
public void validLoginTest() {
    LoginPage loginPage = new LoginPage();
    loginPage.login("validUser", "validPass");
    
    HomePage homePage = new HomePage();
    Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
}
```

## Element Wrappers

The framework provides specialized element wrappers for different types of web elements:

- **BaseElement**: Base class for all elements with common methods
- **Button**: For button elements with click operations
- **TextBox**: For input fields with text operations
- **Link**: For hyperlinks with navigation operations
- **ElementCollection**: For handling multiple elements

## Logging System

The framework has a built-in logging system with four levels:

- **Action**: Logs user actions and operations
- **Success**: Logs successful completion of operations
- **Warning**: Logs potential issues that don't fail the test
- **Error**: Logs critical failures and exceptions

## Configuration

The framework uses a configuration file (`config.properties`) for settings:

```properties
# Browser settings
browser=chrome
headless=false
timeout=10000
browserSize=1920x1080

# Application URLs
base.url=https://example.com
agoda.url=https://www.agoda.com/
```

## Demo Tests

The repository includes demo tests for Agoda hotel search functionality. These tests demonstrate the framework's capabilities but need to be updated with actual selectors before they can be run against the Agoda website.

## Extensibility

The framework is designed to be easily extensible:

- Add new element types by extending `BaseElement`
- Add new page objects by extending `BasePage`
- Add new utility classes for specific functionality
- Configure test execution through TestNG XML files

## Contributors

- [Your Name]

## License

This project is licensed under the MIT License - see the LICENSE file for details.
