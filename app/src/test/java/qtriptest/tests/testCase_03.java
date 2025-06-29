package qtriptest.tests;


import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.utils.ExternalDataProvider;
import java.net.MalformedURLException;
// import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class testCase_03 extends BaseTest {
  // public static RemoteWebDriver driver;
  public static String lastGeneratedUserName;

  @Test(dataProvider = "qtripNewUserData", dataProviderClass = ExternalDataProvider.class,
  groups = {"Booking and Cancellation Flow"}, priority = 3)
  public void TestCase03(String NewUserName, String Password, String SearchCity,
      String AdventureName, String GuestName, String Date, String count)
      throws InterruptedException, MalformedURLException {

    // Assertion assertion = new Assertion();
    System.out.println("UserName: " + NewUserName);
    System.out.println("Password: " + Password);

    Boolean status;
    Assertion assertion = new Assertion();

    // Navigate to Home Page
    HomePage home = new HomePage(driver);
    home.navigateToHomePage();
    Thread.sleep(2000);
    home.navigateToRegisterPage();

    // Register a new user
    RegisterPage registration = new RegisterPage(driver);
    // Assert.assertTrue(registration.isRegisterPageNavigationSucceeded(),
    // "Registration page not displayed");

    registration.registerUser(NewUserName, Password, true);
    // Assert.assertTrue(status, "Failed to register new user");

    // Save the last generated username
    lastGeneratedUserName = registration.lastGeneratedUsername;

    // Login with the registered user
    LoginPage login = new LoginPage(driver);
    login.navigateToLoginPage();
    status = login.PerformLogin(lastGeneratedUserName, Password);

    home.searchCity(SearchCity);
    // Step 2: Verify that the city is displayed in autocomplete suggestions
    Thread.sleep(3000);
    status = home.isCityDisplayedInSuggestionsAndClick(SearchCity);

    AdventurePage adventurePage = new AdventurePage(driver);

    adventurePage.selectAdventure(AdventureName);

    AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
    adventureDetailsPage.bookAdventure(GuestName, Date, count);
    Thread.sleep(4000);
    status = adventureDetailsPage.isBookingSucessful();
    // status=home.assertAutoCompleteText(SearchCity);
    System.out.println(status + "BOOKING IS CONFIRMED");
    assertion.assertTrue(status, "Not Booked ");
    adventureDetailsPage.ClickReservation();

    HistoryPage historypage = new HistoryPage(driver);
    String TransactionID = historypage.getReservationId();
    historypage.cancelReservation(TransactionID);
    Thread.sleep(4000);
    status = historypage.checkTransactioIDPresent(TransactionID);
    assertion.assertTrue(status, "Reservation is failed to cancel");

    // Logout the logged-in user
    status = login.PerformLogout();
    assertion.assertTrue(status, "Failed to logout after login");

  }
}
