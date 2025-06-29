package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AdventurePage {
    WebDriver driver;
    private static final int TIMEOUT = 20;

    public AdventurePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    public void navigateToHomePage() {
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
    }

    @FindBy(xpath = "//div[@id='data']/div")
    List<WebElement> recordsList;

    // WebElements for category and duration filters
  //  @FindBy(xpath = "//select[@id='category-select']")
    @FindBy(xpath = "//div[@class='content']//select[@id='category-select']")
    WebElement categoryFilter;

    //@FindBy(xpath = "//select[@id='duration-select']")
    @FindBy(xpath = "//div[@class='content']//select[@id='duration-select']")
    WebElement durationFilter;

    // WebElements for the results to be verified (results containing "Cycling")
    @FindBy(xpath = "//div[@class='col-6 col-lg-3 mb-4']")
    List<WebElement> allResults;

    // WebElements for clearing filters //div[@onclick='clearDuration(event)']
    @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
    WebElement durationClearBtn;

    @FindBy(xpath = "//div[@onclick='clearCategory(event)']")
    WebElement categoryClearBtn;
    
    @FindBy(xpath = "//div[@onclick='resetAdventuresData()']")
    WebElement clearFiltersButton;

    @FindBy(xpath = "//input[@id='search-adventures']")
    WebElement AdventureInput;

    @FindBy(xpath = "//div[@class='row']/div")
    WebElement Adventureresult;


    //Save all record counts before the filter is applied
    public String getRecordCountsBeforeFilter() {
        int recordCount = recordsList.size(); // Get the actual count of records
        return String.valueOf(recordCount); // Return as String for easier comparison in the test case
    }

    // Common method to apply category and duration filters, verify results, and return filtered
    // result count
    public String applyAndVerifyFilter(String category, String duration) throws InterruptedException {
        // Step 1: Click on "Add Category" button and select the category
        durationClearBtn.click();
        categoryClearBtn.click();
        Thread.sleep(3000);
        categoryFilter.click(); // Click the "Add Category" button
        Thread.sleep(3000); // Use Thread.sleep to wait for the category filter to load
        Select selectCategory = new Select(categoryFilter);
        selectCategory.selectByVisibleText(category); // Select the category filter (Cycling, Hillside, Party)
    
       
    
        // Step 2: Apply duration filter
        durationFilter.click(); // Click the "Add Category" button
        Thread.sleep(3000); // Sleep for 3 seconds to wait for the duration filter to be clickable
        Select selectDuration = new Select(durationFilter);
        selectDuration.selectByVisibleText(duration); // Select the duration filter (e.g., 6-12 Hours)
    
        // Step 3: Verify results after applying both filters
        Thread.sleep(5000); // Sleep for 5 seconds to wait for results to load after filter is applied
        List<String> durationResult = getAllResultTexts(); // Get the list of result texts
      //  List<String> durationResult = getAllResultTexts(allResults); // Get the list of result texts
        boolean durationMatched = durationResult.stream().anyMatch(text -> isWithinDuration(text, duration)); // Check for duration match
    
        if (!durationMatched) {
            throw new AssertionError(duration + " results not found.");
        }
    
        // Step 4: Save the number of results after applying the duration filter
        int filteredResultCount = durationResult.size();
        return String.valueOf(filteredResultCount); // Return as String for easier comparison in the test case
    }
    
   // Helper method to get all result texts in a list
    private List<String> getAllResultTexts() {
        List<String> texts = new ArrayList<>();
        for (WebElement result : allResults) {
            texts.add(result.getText());
        }
        return texts;
    }
    
    // Helper method to check if a result is within the specified duration range
    private boolean isWithinDuration(String resultText, String duration) {
        if (duration.equals("6-12 Hours")) {
            return resultText.contains("6") || resultText.contains("7") || resultText.contains("8")
                    || resultText.contains("9") || resultText.contains("10")
                    || resultText.contains("11") || resultText.contains("12");
        } else if (duration.equals("2-6 Hours")) {
            return resultText.contains("2") || resultText.contains("3") || resultText.contains("4")
                    || resultText.contains("5") || resultText.contains("6");
        }
        return false;
    }

    public void selectAdventure(String adventure) throws InterruptedException{
        clearFiltersButton.click();
        durationClearBtn.click();
        categoryClearBtn.click();
        AdventureInput.click();
        AdventureInput.sendKeys(adventure);
        AdventureInput.sendKeys(Keys.SPACE);
        Thread.sleep(3000);
        Adventureresult.click();

    }

}
