/**
 * File: ExtentReportManager.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: A class to manage the ExtentReports instance
 */
package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * This class manages the creation and retrieval of ExtentReports objects.
 */
public class ExtentReportManager {

    private static ExtentReports extent;

    /**
     * Returns an ExtentReports object with the specified file path.
     * If an ExtentReports object doesn't exist, it's created and an ExtentSparkReporter is attached.
     *
     * @param filePath The file path where the Extent report will be saved.
     * @return An ExtentReports object.
     */
    public static ExtentReports getReporter(String filePath) {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filePath);
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }
}