package qtriptest.tests;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import qtriptest.utils.DriverSingleton;
import qtriptest.utils.ExtentManager;
import qtriptest.utils.ScreenshotUtil;

public class BaseTest {

    protected static WebDriver driver;
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() throws MalformedURLException {
        extent = ExtentManager.getInstance();
        //String browser = System.getProperty("browser", "chrome");
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
}
