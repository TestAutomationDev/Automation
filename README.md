# Automation Framework using Java, Selenium, TestNG, and Extent Reports

## Overview
I'm Waruna, and I have created this project as a solution to the problem mentioned in the
assignment. I have also added a few more additional functionalities to make it robust. This
framework utilizes Java, Selenium, TestNG, Maven and Extent Reports. Adopting the Page
Object Model (POM) using PageFactory, it enables efficient design and maintenance of
automated test scripts.

## Key Features

## Multi-Browser Support
The framework is designed to run tests on multiple browsers. Currently, it supports:
- Chrome
- Edge
- Firefox
- Chrome Headless
- Edge Headless
- Firefox Headless

## Automated Test Data Providers
The framework includes automated data providing functionality for commonly needed data
types. Users can easily utilize this data in their tests and add new functions as per their
requirements.

## Test Data Storage and Retrieval
The framework enables data fetching from APIs. For demonstration purposes, currently, local
JSON files are used. However, with minimal changes, this can be switched to APIs directly.
In addition, constants, success messages, error messages, text validations can be stored in
property files, making them readily available throughout any test.

## Graphical Test Reports
Extent Reports are used to provide graphical reports that include screenshots of failures. Each
test suite, test, and test steps are systematically organized in the final report, along with test
execution time, pass/failure status, screenshots, and other metrics.

## Automated Test Report Organization
The framework automatically generates a test reports folder for each day's test runs, allowing
users to review the test status of any given day in the archived reports. Each report file is neatly
organized by the suite name and execution start time.

## Test Organization using XML files
Users can effectively organize tests using XML files and configure how to trigger the tests as
needed.

## CI/CD Pipeline Integration and Maven Support
This framework can be integrated into any CI/CD pipeline, and tests can be triggered using
Maven.

## Ease of Adding New Tests
Adopting the Page Object Model (POM) approach facilitates the creation and maintenance of
new tests with ease.

The aforementioned features make this framework a versatile tool for any team aiming to
enhance their software testing capabilities while maintaining simplicity and efficiency.

## Fast & Reliable Test Execution
The framework incorporates a waiting mechanism that enhances the reliability of test runs and
minimizes false negatives due to timing issues. This assures smooth execution of tests,
promoting overall testing efficiency.

## How to use the framework.

### Set up Java & Maven
Ensure that you have the required Java & Maven version installed and properly set up on your
system.

### Clone the Git Repo
``` 
git clone https://github.com/TestAutomationDev/Automation.git
```
### Build and Execute Tests
Navigate to the project directory and execute the following Maven command to build and
execute the tests:

``` 
mvn test
```

### Test Reports
The test report will be generated under test-output/reports/Test_Reports_[Current Date].