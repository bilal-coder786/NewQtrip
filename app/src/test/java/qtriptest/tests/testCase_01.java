package qtriptest.tests;

import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.utils.ExternalDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class testCase_01 extends BaseTest {
    public static String lastGeneratedUserName;

    @Test(dataProvider = "qtripData", dataProviderClass = ExternalDataProvider.class, priority = 1, groups = {"Login Flow"})
    public void TestCase01(String UserName, String Password) throws InterruptedException {
        System.out.println("UserName: " + UserName);
        System.out.println("Password: " + Password);

        Boolean status;

        // Navigate to Home Page
        HomePage home = new HomePage(driver);
        home.navigateToHomePage();
        home.navigateToRegisterPage();

        // Register a new user
        RegisterPage registration = new RegisterPage(driver);
        Assert.assertTrue(registration.isRegisterPageNavigationSucceeded(),
                "Registration page not displayed");

        status = registration.registerUser(UserName, Password, true);
        Assert.assertTrue(status, "Failed to register new user");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Login with the registered user
        LoginPage login = new LoginPage(driver);
        login.navigateToLoginPage();

        status = login.PerformLogin(lastGeneratedUserName, Password);
        Assert.assertTrue(status, "Failed to login with registered user");

        // Logout the logged-in user
        status = login.PerformLogout();
        Assert.assertTrue(status, "Failed to logout after login");

        System.out.println("TEST CASE ::END");
    
    }
}
