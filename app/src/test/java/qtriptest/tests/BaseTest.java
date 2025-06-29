package qtriptest.tests;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import qtriptest.utils.DriverSingleton;

public class BaseTest {
     protected static WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void setupDriver() throws MalformedURLException {
        // Get the browser name from system properties or default to "chrome"
        String browser = System.getProperty("browser", "chrome");
        //String browser = System.getProperty("browser", "firefox");
        //String browser = System.getProperty("browser","edge");
        driver = DriverSingleton.getDriver(browser);
        System.out.println("Driver initialized in BaseTest for browser: " + browser);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        System.out.println("Quitting driver...");
        DriverSingleton.quitDriver();
    }
}
