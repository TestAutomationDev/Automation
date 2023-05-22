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
     */
    private ExtentTest parentTest;

    public void onStart(ITestContext context) {

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"));
        String suiteName = context.getSuite().getName();
        String reportName = "Report_" + suiteName + "_" + time + ".html";
        String reportPath = System.getProperty("user.dir") + "/test-output/reports/Test_Reports_" + date;

        System.out.println("Report path: " + reportPath);
        try {
            Path path = Paths.get(reportPath);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            extent = ExtentReportManager.getReporter(reportPath + "/" + reportName);

            parentTest = extent.createTest(suiteName);
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
        ExtentTest test = parentTest.createNode(result.getMethod().getMethodName());
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
        String screenshotPath = captureScreenShot(driver, result);
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
     * @return Screenshot file path
     */
    private String captureScreenShot(WebDriver driver, ITestResult result) {
        String dest = "";
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String directoryPath = System.getProperty("user.dir") + "/test-output/ScreenShots/" + "fail" + "/" + LocalDate.now();
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
