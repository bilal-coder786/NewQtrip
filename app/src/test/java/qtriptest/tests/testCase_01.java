package qtriptest.tests;

import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.utils.ExternalDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

public class testCase_01 extends BaseTest {
    public static String lastGeneratedUserName;

    @Test(dataProvider = "qtripData", dataProviderClass = ExternalDataProvider.class, priority = 1, groups = {"Login Flow"})
    public void TestCase01(String UserName, String Password) throws InterruptedException {
        extentTest.get().log(Status.INFO, "Starting TestCase01 with username: " + UserName);

        Boolean status;

        // Navigate to Home Page
        HomePage home = new HomePage(driver);
        home.navigateToHomePage();
        extentTest.get().log(Status.INFO, "Navigated to Home Page");

        home.navigateToRegisterPage();
        extentTest.get().log(Status.INFO, "Navigated to Register Page");

        // Register a new user
        RegisterPage registration = new RegisterPage(driver);
        Assert.assertTrue(registration.isRegisterPageNavigationSucceeded(), "Registration page not displayed");
        extentTest.get().log(Status.INFO, "Registration page verified");

        status = registration.registerUser(UserName, Password, true);
        Assert.assertTrue(status, "Failed to register new user");
        extentTest.get().log(Status.PASS, "User registered successfully");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Login with the registered user
        LoginPage login = new LoginPage(driver);
        login.navigateToLoginPage();
        extentTest.get().log(Status.INFO, "Navigated to Login Page");

        status = login.PerformLogin(lastGeneratedUserName, Password);
        Assert.assertTrue(status, "Failed to login with registered user");
        extentTest.get().log(Status.PASS, "User login successful");

        // Logout the logged-in user
        status = login.PerformLogout();
        Assert.assertTrue(status, "Failed to logout after login");
        extentTest.get().log(Status.PASS, "User logout successful");

        extentTest.get().log(Status.INFO, "TestCase01 completed successfully");
    }
}

