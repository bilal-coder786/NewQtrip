package qtriptest.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.HashMap;

public class DriverSingleton {

    private static WebDriver driver;

    private DriverSingleton() {
        // Prevent external instantiation 
    }

    public static WebDriver getDriver(String browser) {
        if (driver == null) {
            synchronized (DriverSingleton.class) {
                if (driver == null) {
                    switch (browser.toLowerCase()) {
                        case "chrome":
                            System.out.println("Launching Chrome browser...");
                            WebDriverManager.chromedriver().setup();

                            // 👇 ChromeOptions to disable password manager popup
                            ChromeOptions options = new ChromeOptions();
                            options.addArguments("--disable-notifications");
                            HashMap<String, Object> prefs = new HashMap<>();
                            prefs.put("credentials_enable_service", false);
                            prefs.put("profile.password_manager_enabled", false);
                            options.setExperimentalOption("prefs", prefs);

                            driver = new ChromeDriver(options);
                            break;

                        case "firefox":
                            WebDriverManager.firefoxdriver().setup();
                            driver = new FirefoxDriver();
                            break;

                        case "edge":
                            WebDriverManager.edgedriver().setup();
                            driver = new EdgeDriver();
                            break;

                        default:
                            throw new IllegalArgumentException("Unsupported browser: " + browser);
                    }

                    driver.manage().window().maximize();
                    driver.manage().deleteAllCookies();
                }
            }
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}


// package qtriptest.utils;

// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.edge.EdgeDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;

// import io.github.bonigarcia.wdm.WebDriverManager;

// public class DriverSingleton {

//     private static WebDriver driver;

//     private DriverSingleton() {
//         // Prevent external instantiation 
//     }

//     public static WebDriver getDriver(String browser) {
//         if (driver == null) {
//             synchronized (DriverSingleton.class) {
//                 if (driver == null) {
//                     switch (browser.toLowerCase()) {
//                         case "chrome":
//                         System.out.println("Launching Chrome browser...");
//                             WebDriverManager.chromedriver().setup();
//                             driver = new ChromeDriver();
//                             break;
//                         case "firefox":
//                             WebDriverManager.firefoxdriver().setup();
//                             driver = new FirefoxDriver();
//                             break;
//                         case "edge":
//                             WebDriverManager.edgedriver().setup();
//                             driver = new EdgeDriver();
//                             break;
//                         default:
//                             throw new IllegalArgumentException("Unsupported browser: " + browser);
//                     }

//                     driver.manage().window().maximize();
//                     driver.manage().deleteAllCookies();
//                 }
//             }
//         }
//         return driver;
//     }

//     public static void quitDriver() {
//         if (driver != null) {
//             driver.quit();
//             driver = null;
//         }
//     }
// }
