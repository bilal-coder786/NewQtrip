package qtriptest.tests;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.utils.ExternalDataProvider;

public class testCase_04 extends BaseTest {

    public static String lastGeneratedUserName;

    @Test(dataProvider = "qtripNewUserDataSet", dataProviderClass = ExternalDataProvider.class, groups = { "Reliability Flow" }, priority = 4)
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2, String dataset3)
            throws InterruptedException, MalformedURLException {

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
        //Assert.assertTrue(registration.isRegisterPageNavigationSucceeded(), "Registration page not displayed");
        registration.registerUser(NewUserName, Password, true);

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Login with the registered user
        LoginPage login = new LoginPage(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, Password);
       // Assert.assertTrue(status, "Login failed");

        // Execute bookings
        tc04Execution(dataset1, home);
        tc04Execution(dataset2, home);
        tc04Execution(dataset3, home);

        home.ClickReservation();
        Thread.sleep(2000);

        // Logout the logged-in user
        status = login.PerformLogout();
        assertion.assertTrue(status, "Failed to logout after login");
    }

    public static void tc04Execution(String Dataset, HomePage home)
            throws InterruptedException, MalformedURLException {

        Assertion assertion = new Assertion();
        boolean status;

        String[] data = Dataset.split(";"); // Bengaluru;Niaboytown;Chicky;01-01-2025;2
        String SearchCity = data[0];
        String SearchAdventure = data[1];
        String guestName = data[2];
        String date = data[3];
        String count = data[4];
        Thread.sleep(2000);

        //System.out.println("Booking for: " + SearchCity + " -> " + SearchAdventure);

        home.searchCity(SearchCity);
        Thread.sleep(3000);


        status = home.isCityDisplayedInSuggestionsAndClick(SearchCity);
      //  Assert.assertTrue(status, "City not found in suggestions: " + SearchCity);

        AdventurePage adventurePage = new AdventurePage(driver);
        adventurePage.selectAdventure(SearchAdventure);

        AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
        adventureDetailsPage.bookAdventure(guestName, date, count);
         Thread.sleep(4000);
        status = adventureDetailsPage.isBookingSucessful();
        assertion.assertTrue(status, "Not Booked for: " );

        home.navigateToHomePage();
    }
}



// package qtriptest.tests;


// import java.net.MalformedURLException;

// import org.testng.annotations.Test;
// import org.testng.asserts.Assertion;

// import qtriptest.pages.AdventureDetailsPage;
// import qtriptest.pages.AdventurePage;
// import qtriptest.pages.HomePage;
// import qtriptest.pages.LoginPage;
// import qtriptest.pages.RegisterPage;
// import qtriptest.utils.ExternalDataProvider;

// public class testCase_04 extends BaseTest {

//   public static String lastGeneratedUserName;


//   @Test(dataProvider = "qtripNewUserDataSet", dataProviderClass = ExternalDataProvider.class, groups = {"Reliability Flow"},
//   priority = 4)
//   public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2,
//       String dataset3) throws InterruptedException, MalformedURLException {

//     System.out.println("UserName: " + NewUserName);
//     System.out.println("Password: " + Password);

//     Boolean status;
//     Assertion assertion = new Assertion();

//     // Navigate to Home Page
//     HomePage home = new HomePage(driver);
//     home.navigateToHomePage();
//     Thread.sleep(2000);
//     home.navigateToRegisterPage();

//     // Register a new user
//     RegisterPage registration = new RegisterPage(driver);
//     // Assert.assertTrue(registration.isRegisterPageNavigationSucceeded(),
//     // "Registration page not displayed");

//     registration.registerUser(NewUserName, Password, true);
//     // Assert.assertTrue(status, "Failed to register new user");

//     // Save the last generated username
//     lastGeneratedUserName = registration.lastGeneratedUsername;

//     // Login with the registered user
//     LoginPage login = new LoginPage(driver);
//     login.navigateToLoginPage();
//     status = login.PerformLogin(lastGeneratedUserName, Password);

//     tc04Execution(dataset1, home);
//     tc04Execution(dataset2, home);
//     tc04Execution(dataset3, home);

//     home.ClickReservation();
//     Thread.sleep(2000);
//     // Logout the logged-in user
//     status = login.PerformLogout();
//     assertion.assertTrue(status, "Failed to logout after login");


//   }

//   public static void tc04Execution(String Dataset, HomePage home)
//       throws InterruptedException, MalformedURLException {
//     // try {
//     Assertion assertion = new Assertion();
//     boolean status;
//     String[] data = Dataset.split(";");  //Bengaluru;Niaboytown;Chicky;01-01-2025;2
//     String SearchCity = data[0];
//     String SearchAdventure = data[1];
//     String guestName = data[2];
//     String date = data[3];
//     String count = data[4];
//     Thread.sleep(2000);
 
//     home.searchCity(SearchCity);
//     Thread.sleep(3000);
//     status = home.isCityDisplayedInSuggestionsAndClick(SearchCity);

//     AdventurePage adventurePage = new AdventurePage(driver);
//     adventurePage.selectAdventure(SearchAdventure);

//     AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
//     adventureDetailsPage.bookAdventure(guestName, date, count);
//     Thread.sleep(2000);
//     status = adventureDetailsPage.isBookingSucessful();
//     assertion.assertTrue(status, "Not Booked ");
//     home.navigateToHomePage();
//     }
// }