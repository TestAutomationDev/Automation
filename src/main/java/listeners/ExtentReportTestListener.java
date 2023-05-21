/**
 * File: ExtentReportTestListener.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description:Listener to the test execution and generate the ExtentReports
 */

package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import core.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReportManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ExtentReportTestListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private ExtentReports extent;

    /**
     * Initializes the extent report and sets up the report path and name.
     * Creates directories if they do not exist.
     *
     * @param context Test context
     */
    public void onStart(ITestContext context) {

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"));
        String suiteName = context.getSuite().getName();
        String reportName = "Report_" + suiteName + "_" + time + ".html";
        String reportPath = System.getProperty("user.dir") + "/test-output/reports/Test_Reports_" + date;

        System.out.println("Report path: " + reportPath);
        try {
            Path path = Paths.get(reportPath);
            // Create directories if they do not exist
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            extent = ExtentReportManager.getReporter(reportPath + "/" + reportName);

            // Add suite name as a parent test
            ExtentTest parent = extent.createTest(suiteName);
            extentTest.set(parent);
        } catch (IOException e) {
            System.out.println("Error creating report directories: " + e.getMessage());
        }
    }

    /**
     * Creates a new node for each test method and sets the start time and description.
     *
     * @param result Test result
     */
    @Override
    public void onTestStart(ITestResult result) {
        // Add each test method as a node of the parent test
        ExtentTest test = extentTest.get().createNode(result.getMethod().getMethodName());
        test.getModel().setStartTime(getTime(result.getStartMillis()));
        Object[] params = result.getParameters();
        String browserInfo = "";
        if (params.length > 0) {
            browserInfo = (String) params[0];
        }
        test.getModel().setDescription(browserInfo);
        extentTest.set(test);
    }

    /**
     * Logs the test as passed and sets the end time.
     *
     * @param result Test result
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    /**
     * Logs the test as failed, captures a screenshot and sets the end time.
     *
     * @param result Test result
     */
    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
        WebDriver driver = WebDriverFactory.getWebDriver();
        String screenshotPath = captureScreenShot(driver, result, "fail");
        extentTest.get().addScreenCaptureFromPath(screenshotPath);
        extentTest.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
    }

    /**
     * Logs the test as skipped and sets the end time.
     *
     * @param result Test result
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
        extentTest.get().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
    }

    /**
     * Flushes the extent report.
     *
     * @param context Test context
     */
    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    /**
     * Converts milliseconds to date format.
     *
     * @param millis Time in milliseconds
     * @return Date object
     */
    private Date getTime(long millis) {
        return new Date(millis);
    }

    /**
     * Captures a screenshot of the current browser window and saves it to the specified directory.
     *
     * @param driver WebDriver instance
     * @param result Test result
     * @param status Test status (pass or fail)
     * @return Screenshot file path
     */
    private String captureScreenShot(WebDriver driver, ITestResult result, String status) {
        String dest = "";
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String directoryPath = System.getProperty("user.dir") + "/test-output/ScreenShots/" + status + "/" + LocalDate.now();
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            dest = directoryPath + "/" + result.getName() + "_" + System.currentTimeMillis() + ".png";
            File destination = new File(dest);
            Files.copy(source.toPath(), destination.toPath());
            System.out.println("Screenshot taken");
        } catch (IOException e) {
            System.out.println("Error capturing screenshot: " + e.getMessage());
        }
        return dest;
    }
}
