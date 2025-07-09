// package qtriptest.utils;

// import com.aventstack.extentreports.ExtentReports;
// import com.aventstack.extentreports.reporter.ExtentSparkReporter;

// public class ExtentManager {
//     private static ExtentReports extent;

//     public static ExtentReports getInstance() {
//         if (extent == null) {
//             ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
//             extent = new ExtentReports();
//             extent.attachReporter(spark);
//             extent.setSystemInfo("Environment", "QA");
//             extent.setSystemInfo("User", "Mohammad Bilal");
//         }
//         return extent;
//     }
// }
package qtriptest.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Get current working directory
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";

            // Print path to console for debugging
            System.out.println(" Extent Report will be generated at: " + reportPath);

            // Create reporter and attach to ExtentReports
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Add system info
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("User", "Mohammad Bilal");
        }
        return extent;
    }
}
