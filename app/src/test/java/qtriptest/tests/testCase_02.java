package qtriptest.tests;

import qtriptest.utils.DP;
import qtriptest.utils.ExternalDataProvider;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testCase_02 extends BaseTest {

    // This variable will ensure the invalid city search is done only once
    private boolean isInvalidCitySearched = false;

    @BeforeMethod
    public void searchForInvalidCity() throws InterruptedException {
        if (!isInvalidCitySearched) {
            String invalidCity = "Jaipur";
            HomePage home = new HomePage(driver);
            home.navigateToHomePage();
            Thread.sleep(2000);
            home.searchCity(invalidCity); // Only search for invalid city once
            // Waiting for the invalid city result to appear
            boolean status = home.isNoCityFoundDisplayed();
            Assert.assertTrue(status, "'No City found' message not displayed as expected.");
            isInvalidCitySearched = true; // Set this flag to true to prevent repeated invalid city
                                          // search
        }
        System.out.println("searchForInvalidCity METHOD IS COMPLETE HERE");
    }

     @Test(dataProvider = "qtripCityData", dataProviderClass = ExternalDataProvider.class, priority = 2,
            groups = {"Search and Filter flow"})
    public void TestCase02(String CityName, String Category_Filter, String DurationFilter,
                           String ExpectedFilteredResults, String ExpectedUnFilteredResults)
            throws InterruptedException {

        // Step 1: Navigate and search valid city
        HomePage home = new HomePage(driver);
        home.navigateToHomePage();
        home.searchCity(CityName);

        Thread.sleep(5000);

        // // ✅ Wait for auto-suggestion (Recommended)
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".results")));

        // Step 2: Verify autocomplete suggestion & click
        Assert.assertTrue(home.isCityDisplayedInSuggestionsAndClick(CityName),
                "City not displayed in autocomplete suggestions");

        // Step 3: Adventure page actions
        AdventurePage adventurePage = new AdventurePage(driver);

        String recordsBeforeFilter = adventurePage.getRecordCountsBeforeFilter();
        System.out.println("Total records before filter: " + recordsBeforeFilter);
        System.out.println("TOTAL RECORDS BEFORE FILTER IS == EXPECTED UNFILTERED DATA");

        // Step 4: Apply filters and validate
        String filteredResultCount = adventurePage.applyAndVerifyFilter(Category_Filter, DurationFilter);
        System.out.println("Filtered Results: " + filteredResultCount);

        // ✅ Use double parse to handle 1.0 vs 1
        Assert.assertEquals(Double.parseDouble(filteredResultCount),
                Double.parseDouble(ExpectedFilteredResults),
                "Filtered results count mismatch.");
    }
}


    // @Test(dataProvider = "qtripCityData", dataProviderClass = ExternalDataProvider.class, priority = 2,
    // groups = {"Search and Filter flow"})
    // public void TestCase02(String CityName, String Category_Filter, String DurationFilter,
    //         String ExpectedFilteredResults, String ExpectedUnFilteredResults)
    //         throws InterruptedException {

    //     // Step 1: Search for a city that's present
    //     HomePage home = new HomePage(driver);
    //     home.navigateToHomePage();
    //     home.searchCity(CityName);

    //     // Step 2: Verify that the city is displayed in autocomplete suggestions
    //     Thread.sleep(5000);
    //     Assert.assertTrue(home.isCityDisplayedInSuggestionsAndClick(CityName),
    //             "City not displayed in autocomplete suggestions");

    //     AdventurePage adventurePage = new AdventurePage(driver);

    //     String recordsBeforeFilter = adventurePage.getRecordCountsBeforeFilter();
    //     System.out.println("Total records before filter: " + recordsBeforeFilter);
    //     System.out.println("TOTAL RECORDS BEFORE FILTER IS == EXPECTED UNFILTERED DATA");

    //     String filteredResultCount =
    //             adventurePage.applyAndVerifyFilter(Category_Filter, DurationFilter);
    //     System.out.println(filteredResultCount);
    //     Assert.assertEquals(filteredResultCount, ExpectedFilteredResults,
    //             "Filtered results count mismatch.");
    // }
