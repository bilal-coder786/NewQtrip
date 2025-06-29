package qtriptest.pages;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import static qtriptest.utils.Wrappers.*;
import java.time.Duration;
import java.util.NoSuchElementException;

public class LoginPage {
    WebDriver driver;
    private static final int TIMEOUT = 15;

    private static final String loginPageEndpoint = "/pages/login";

    @FindBy(xpath = "//h2[text()='Login']")
    WebElement loginCardHeader;

    @FindBy(name = "email")
    WebElement usernameInput;

    @FindBy(name = "password")
    WebElement passwordInput;

    @FindBy(xpath = "//button[normalize-space(text())='Login to QTrip']")
    WebElement loginButton;

    @FindBy(xpath = "//div[text()='Logout']")
    WebElement logoutButton;

    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    public boolean isLoginPageNavigationSucceeded() {
        return isPageNavigationSucceeded(driver, loginPageEndpoint, loginCardHeader, "Login");
    }

    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";

    public void navigateToLoginPage() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    

    public Boolean PerformLogin(String Username, String Password) throws InterruptedException {
        // Enter the username
        usernameInput.sendKeys(Username);

        // Wait for user name to be entered
        Thread.sleep(1000);
        // Enter the password
        passwordInput.sendKeys(Password);
       // Click the login Button
        loginButton.click();

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(600)).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.invisibilityOf(loginButton));

        synchronized (driver) {
            driver.wait(2000);
        }
        
        return this.VerifyUserLoggedIn();
    }

    public Boolean VerifyUserLoggedIn() {
        try {
            return logoutButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean PerformLogout(){
        logoutButton.click();
        return true;
    }
    
}


