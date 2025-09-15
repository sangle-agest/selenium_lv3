# Ultra-Detailed Kanban Plan – Selenide Framework (Java 17)

## 🟦 Epic 1: Project Setup & Configuration
### Task 1: Initialize Maven project with Java 17
- **Subtasks:**
  - Create new Maven project (mvn archetype:generate or IntelliJ wizard).
  - Set Java 17 in pom.xml (maven-compiler-plugin source/target).
  - Verify build runs (mvn clean install).
- **Acceptance Criteria:**
  - Running mvn -v shows Java 17.
  - mvn clean install succeeds with no errors.
  - Empty project with src/main/java and src/test/java structure ready.

### Task 2: Add dependencies
- **Subtasks:**
  - Add Selenide (com.codeborne:selenide).
  - Add TestNG (org.testng:testng).
  - Add Allure (io.qameta.allure:allure-testng).
  - Add SLF4J + Log4j2 for logging.
  - Add JUnit (optional, for utility tests).
- **Acceptance Criteria:**
  - All dependencies resolve (mvn dependency:tree).
  - Can write a dummy TestNG test using Selenide and run successfully.

### Task 3: Setup build automation
- **Subtasks:**
  - Configure Maven Surefire plugin (unit tests).
  - Configure Maven Failsafe plugin (integration tests, optional).
  - Ensure mvn test runs TestNG tests.
- **Acceptance Criteria:**
  - Running mvn test executes TestNG test cases.
  - Test reports are generated under target/surefire-reports/.

### Task 4: Create config.properties
- **Subtasks:**
  - Add file src/test/resources/config.properties.
  - Keys: browser, baseUrl, timeout, headless, remoteGridUrl.
  - Provide default values (browser=chrome, timeout=5000).
- **Acceptance Criteria:**
  - File loads correctly at runtime.
  - Framework can read baseUrl when opening tests.

### Task 5: Implement config loader utility
- **Subtasks:**
  - Create ConfigManager class.
  - Load properties via Properties class.
  - Provide static getters like ConfigManager.getBrowser().
- **Acceptance Criteria:**
  - Reading config values works in tests.
  - Missing property falls back to default.

### Task 6: Setup parallel execution support
- **Subtasks:**
  - Update TestNG XML to allow parallel="tests".
  - Make WebDriver factory thread-safe (ThreadLocal driver).
  - Run 2 tests in parallel and verify separate browser instances.
- **Acceptance Criteria:**
  - Running mvn test -DsuiteXmlFile=parallel.xml executes multiple tests simultaneously.
  - Logs/screenshots show independent sessions.

### Task 7: Write README
- **Subtasks:**
  - Explain project setup, dependencies, run commands.
  - Document single vs. parallel execution.
  - Include sample commands (mvn clean test).
- **Acceptance Criteria:**
  - New developer can clone repo, run mvn test, and see results by following README.

## 🟦 Epic 2: Core Framework (Wrapper & Utilities)
### Task 1: Create WebDriver factory
- **Subtasks:**
  - Build DriverFactory with support for Chrome, Firefox, Edge.
  - Add options (headless, incognito).
  - Support remote driver (Selenium Grid).
- **Acceptance Criteria:**
  - ConfigManager.getBrowser() decides which driver launches.
  - Running with -Dbrowser=firefox starts Firefox test.

### Task 2: Implement wrapper methods
- **Subtasks:**
  - Create UIActions helper class.
  - Methods: click(element), type(element, text), selectDropdown(element, value), dragAndDrop(src, target).
  - Each method logs step + applies wait before action.
- **Acceptance Criteria:**
  - Wrapper methods replace direct element.click().
  - Logs show step info like [INFO] Clicking button: Login.

### Task 3: Add wait utility
- **Subtasks:**
  - Create WaitUtils with methods: waitForVisible, waitForClickable, waitForText.
  - Use WebDriverWait and Selenide conditions.
- **Acceptance Criteria:**
  - Tests do not use Thread.sleep.
  - Flaky timing issues reduced by explicit waits.

### Task 4: Handle popups & frames
- **Subtasks:**
  - Implement switchToFrame(nameOrId), switchToDefault().
  - Implement acceptAlert(), dismissAlert().
- **Acceptance Criteria:**
  - Popup and iframe handling works in at least one test.

### Task 5: Implement logging
- **Subtasks:**
  - Configure Log4j2 XML (resources/log4j2.xml).
  - Define appenders (console + rolling file).
  - Use Logger in wrapper methods and tests.
- **Acceptance Criteria:**
  - Logs written to logs/test.log.
  - Console shows [INFO] Step: Login successful.

### Task 6: Screenshot capture on failure
- **Subtasks:**
  - Implement TestNG IReporter or ITestListener.
  - Capture screenshot in onTestFailure.
  - Save file under target/screenshots/.
- **Acceptance Criteria:**
  - On failure, screenshot is attached in Allure report.
  - Screenshot file name includes test name + timestamp.

## 🟦 Epic 3: Page Object Model (POM)
### Task 1: Create BasePage
- **Subtasks:**
  - Define common driver reference.
  - Add reusable actions like open(url) and waitForPageLoad().
- **Acceptance Criteria:**
  - All page objects extend BasePage.

### Task 2: Define clean locators
- **Subtasks:**
  - Use descriptive names (btnLogin, txtUsername).
  - Prefer CSS selectors; only use XPath when needed.
- **Acceptance Criteria:**
  - No duplicate locators across classes.
  - Locators are resilient against minor UI changes.

### Task 3: Support dynamic locators
- **Subtasks:**
  - Create helper: By.xpath(String.format("//td[%d]/td[%d]", row, col)).
  - Build methods for dynamic dropdown or table handling.
- **Acceptance Criteria:**
  - Tests can locate elements by dynamic values (e.g., booking row 3, col 2).

### Task 4: Implement Page Objects
- **Subtasks:**
  - Build PO for: HomePage, RegisterPage, BookingPage, TimetablePage.
  - Add methods like login(user, pass), bookTicket(details).
- **Acceptance Criteria:**
  - Test classes never call driver.findElement() directly.
  - All actions go through Page Object methods.

### Task 5: Add DAO for test data
- **Subtasks:**
  - Create testdata/users.json with accounts.
  - Build DataManager to load users/bookings.
- **Acceptance Criteria:**
  - Tests can pull user credentials via DAO instead of hardcoding.

## 🟦 Epic 4: Test Automation Design
### Task 1: Define test case structure
- **Subtasks:**
  - Setup @BeforeSuite → init reports.
  - Setup @BeforeMethod → open browser.
  - Setup @AfterMethod → close browser.
- **Acceptance Criteria:**
  - Each test starts with a fresh session unless reused.

### Task 2: Implement test data setup/cleanup
- **Subtasks:**
  - Before test: ensure environment clean (e.g., no existing booking for same user).
  - After test: remove created test data.
- **Acceptance Criteria:**
  - Re-running same test does not fail due to leftover data.

### Task 3: Write TC1 (Simple – Login Test)
- **Steps:**
  - Open homepage.
  - Navigate to login.
  - Enter valid username & password.
  - Verify successful login (assert welcome message).
- **Acceptance Criteria:**
  - Test passes with correct credentials.
  - Test fails with incorrect credentials.

### Task 4: Write TC2 (Complex – Booking Test)
- **Steps:**
  - Login.
  - Navigate to booking page.
  - Fill booking form (from DAO test data).
  - Submit booking.
  - Navigate to Timetable page.
  - Verify booking appears in timetable.
- **Acceptance Criteria:**
  - Test creates booking successfully.
  - Assertion matches booking details in Timetable.

### Task 5: Add checkpoints (assertions)
- **Subtasks:**
  - Use assertEquals, assertTrue.
  - Provide meaningful failure messages.
- **Acceptance Criteria:**
  - No false positives (test only passes if AUT is correct).

### Task 6: Ensure TC1 & TC2 run in same browser session
- **Subtasks:**
  - Use dependsOnMethods in TestNG.
  - Reuse driver instance across dependent tests.
- **Acceptance Criteria:**
  - TC1 login is reused in TC2 booking flow.

### Task 7: Organize tests into suites
- **Subtasks:**
  - Create smoke.xml for fast runs.
  - Create regression.xml for full suite.
- **Acceptance Criteria:**
  - Running mvn test -DsuiteXmlFile=smoke.xml executes smoke tests only.

## 🟦 Epic 5: Reporting & Documentation
### Task 1: Integrate Allure Reports
- **Subtasks:**
  - Add Allure Maven plugin.
  - Annotate tests with @Description.
- **Acceptance Criteria:**
  - After test run, mvn allure:serve shows reports.

### Task 2: Configure TestNG listeners
- **Subtasks:**
  - Implement ITestListener for logs & screenshots.
  - Attach to testng.xml.
- **Acceptance Criteria:**
  - Failures show logs + screenshots in reports.

### Task 3: Add logging in test cases
- **Subtasks:**
  - Use log.info("Step: Open login page").
- **Acceptance Criteria:**
  - Logs readable in console & file.

### Task 4: Document test case descriptions
- **Subtasks:**
  - JavaDoc above each test method.
  - Inline comments explaining tricky steps.
- **Acceptance Criteria:**
  - Reviewer can understand test intent without asking.

### Task 5: Create final README
- **Subtasks:**
  - Document execution commands.
  - Document reporting (allure:serve).
  - Document CI/CD instructions.
- **Acceptance Criteria:**
  - README covers setup, run, reports, troubleshooting.

## 🟦 Epic 6: CI/CD & Maintenance
### Task 1: Setup Git repo
- **Subtasks:**
  - Initialize repo.
  - Add .gitignore.
- **Acceptance Criteria:**
  - Repo builds on fresh clone.

### Task 2: Configure GitHub Actions pipeline
- **Subtasks:**
  - YAML with mvn test.
  - Upload Allure/HTML report artifact.
- **Acceptance Criteria:**
  - Every push triggers test run + report artifact.

### Task 3: Enable parallel execution on Grid
- **Subtasks:**
  - Start Selenium Grid (Docker or standalone).
  - Update config to point to remoteGridUrl.
- **Acceptance Criteria:**
  - Tests run in parallel on Chrome + Firefox.

### Task 4: Verify test stability
- **Subtasks:**
  - Run tests 3x.
  - Record pass/fail results.
- **Acceptance Criteria:**
  - ≥ 2/3 runs pass for same OS/browser.

### Task 5: Backlog/future items
- **Possible Enhancements:**
  - Integrate DB setup.
  - Add API testing module.
  - Dockerize framework.
