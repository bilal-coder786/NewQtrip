package qtriptest.tests;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.*;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import qtriptest.utils.DriverSingleton;
import qtriptest.utils.ExtentManager;
import qtriptest.utils.ScreenshotUtil;
import org.apache.commons.io.FileUtils;

public class BaseTest {

    protected static WebDriver driver;
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() throws MalformedURLException {

        // ✅ Archive old Extent Report and Screenshots
        archiveOldReports();

        extent = ExtentManager.getInstance();

        String browser = System.getProperty("browser", "firefox");
        driver = DriverSingleton.getDriver(browser);
        System.out.println("Driver initialized for browser: " + browser);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        extentTest.set(test);
        test.log(Status.INFO, "Starting test: " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult result) {
        ExtentTest test = extentTest.get();

        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getThrowable());

            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
            test.addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test passed successfully");
        } else {
            test.log(Status.SKIP, "Test skipped");
        }

        extent.flush();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        DriverSingleton.quitDriver();
        extent.flush();
    }

    //  Archive old test-output folder instead of deleting
   public void archiveOldReports() {
    // 👇 Debug line to confirm base path
    System.out.println("Current project path: " + System.getProperty("user.dir"));

    String folderPath = System.getProperty("user.dir") + "/test-output";
    String archiveBasePath = System.getProperty("user.dir") + "/archive-reports";
    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String archivePath = archiveBasePath + "/test-output_" + timestamp;

    File sourceDir = new File(folderPath);
    File archiveBaseDir = new File(archiveBasePath);
    File destDir = new File(archivePath);

    //  Step 1: Create archive base directory if it doesn't exist
    if (!archiveBaseDir.exists()) {
        archiveBaseDir.mkdirs();
        System.out.println("📁 Created archive base directory at: " + archiveBasePath);
    }

    // Step 2: If test-output exists, copy and clean it
    if (sourceDir.exists()) {
        try {
            FileUtils.copyDirectory(sourceDir, destDir);
            FileUtils.cleanDirectory(sourceDir);
            System.out.println(" Old reports archived to: " + archivePath);
        } catch (IOException e) {
            System.out.println("❌ Failed to archive reports: " + e.getMessage());
        }
    } else {
        System.out.println("⚠️ test-output folder does not exist at: " + folderPath);
    }
}

}

// package qtriptest.tests;

// import java.lang.reflect.Method;
// import java.net.MalformedURLException;

// import org.openqa.selenium.WebDriver;
// import org.testng.ITestResult;
// import org.testng.annotations.*;

// import com.aventstack.extentreports.ExtentReports;
// import com.aventstack.extentreports.ExtentTest;
// import com.aventstack.extentreports.Status;

// import qtriptest.utils.DriverSingleton;
// import qtriptest.utils.ExtentManager;
// import qtriptest.utils.ScreenshotUtil;

// public class BaseTest {

//     protected static WebDriver driver;
//     protected static ExtentReports extent;
//     protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

//     @BeforeSuite(alwaysRun = true)
//     public void setupSuite() throws MalformedURLException {
//         extent = ExtentManager.getInstance();
//         //String browser = System.getProperty("browser", "chrome");
//         String browser = System.getProperty("browser", "firefox");
//         driver = DriverSingleton.getDriver(browser);
//         System.out.println("Driver initialized for browser: " + browser);
//     }

//     @BeforeMethod(alwaysRun = true)
//     public void setupTest(Method method) {
//         ExtentTest test = extent.createTest(method.getName());
//         extentTest.set(test);
//         test.log(Status.INFO, "Starting test: " + method.getName());
//     }

//     @AfterMethod(alwaysRun = true)
//     public void tearDownTest(ITestResult result) {
//         ExtentTest test = extentTest.get();

//         if (result.getStatus() == ITestResult.FAILURE) {
//             test.log(Status.FAIL, result.getThrowable());

//             String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
//             test.addScreenCaptureFromPath(screenshotPath);
//         } else if (result.getStatus() == ITestResult.SUCCESS) {
//             test.log(Status.PASS, "Test passed successfully");
//         } else {
//             test.log(Status.SKIP, "Test skipped");
//         }

//         extent.flush();
//     }

//     @AfterSuite(alwaysRun = true)
//     public void tearDownSuite() {
//         DriverSingleton.quitDriver();
//         extent.flush();
//     }
// }
