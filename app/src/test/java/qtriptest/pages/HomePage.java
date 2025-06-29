package qtriptest.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.net.UrlChecker.TimeoutException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static qtriptest.utils.Wrappers.*;
import java.time.Duration;

public class HomePage {

    // @FindBy(xpath = "//div[@id='navbarNavDropdown']/ul/li[4]/a")
    // WebElement registerButton;
    @FindBy(css =".register")
    WebElement registerButton;

    @FindBy(xpath = "//button[normalize-space(text())='Login Here']")
    WebElement loginButton;

    @FindBy(xpath = "//button[normalize-space(text())='Logout']")
    WebElement logoutButton;

    @FindBy(id = "autocomplete")
    WebElement searchBar;

    @FindBy(xpath = "//div[@class='container']/ul[@id='results']")
    private WebElement cityResult;

    @FindBy(xpath = "//div[@id='navbarNavDropdown']//a[text()='Reservations']")
    WebElement ReservationBtn;


    WebDriver driver;

    private static final int TIMEOUT = 20;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    public void navigateToHomePage() {
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
    }

    public void navigateToRegisterPage() {
        registerButton.click();
    }

    public boolean isLogoutButtonVisible() {
        return logoutButton.isDisplayed();
    }

    public boolean isLoginButtonVisible() {
        return loginButton.isDisplayed();
    }

    public void searchCity(String city) {
        // searchBar.sendKeys(city);

        enterText(searchBar, city);
        searchBar.sendKeys(Keys.SPACE);
        //return true;
    }
    public boolean isNoCityFoundDisplayed() throws InterruptedException {
        try {
            // Ensure the element is initialized and visible, then verify its text
            Thread.sleep(3000);
            if (cityResult.isDisplayed()) {
                return cityResult.getText().trim().equalsIgnoreCase("No City found");
            }
            return false; // If element is not displayed, return false
        } catch (NoSuchElementException e) {
            return false; // Return false if the element is not found
        }
    }

    // Method to verify if the city is displayed in the autocomplete suggestions and click it
    public boolean isCityDisplayedInSuggestionsAndClick(String City) throws InterruptedException {
        try {
            // Wait for city results to appear (considering thread sleep for simplicity, better with explicit wait)
            Thread.sleep(2000);

            // Check if the cityResult element is displayed
            if (cityResult.isDisplayed()) {
                // Verify if the displayed city matches the expected city and click on it
                if (cityResult.getText().trim().equalsIgnoreCase(City)) {
                    cityResult.click();
                    return true; // Successfully clicked on the city
                }
            }
            return false; // If element not displayed or city doesn't match
        } catch (NoSuchElementException e) {
            return false; // Return false if the element is not found
        }
    }

    public boolean assertAutoCompleteText(String city){
        String res="";
        try{
         res=cityResult.getText();
        }
        catch(Exception e){
            searchBar.sendKeys(Keys.SPACE);
                res=cityResult.getText();
        }
        if(res.equalsIgnoreCase(city)){
                return true;
        }
        return false;
    }

    public void ClickReservation(){
        ReservationBtn.click();
    }
}

