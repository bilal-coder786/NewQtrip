package qtriptest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;
import java.sql.Timestamp;
import java.time.Duration;

import static qtriptest.utils.Wrappers.*;

public class RegisterPage {
    WebDriver driver;
    public String lastGeneratedUsername = "";
    private static final int TIMEOUT = 20;

    private static final String registerPageEndpoint = "/pages/register";
    public String generatedEmail;

    @FindBy(css = "h2.formtitle")
    WebElement registerCardHeader;

    @FindBy(name = "email")
    WebElement emailInput;

    @FindBy(name = "password")
    WebElement passwordInput;

    @FindBy(name = "confirmpassword")
    WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[normalize-space(text())='Register Now']")
    WebElement registerNowButton;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    public boolean isRegisterPageNavigationSucceeded() {
        return isPageNavigationSucceeded(driver, registerPageEndpoint, registerCardHeader,
                "Register");
    }

    public Boolean registerUser(String Username, String Password, Boolean makeUsernameDynamic)
            throws InterruptedException {
                // Get time stamp for generating a unique username
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String test_data_username;

        // Check if dynamic username creation is required
        if (makeUsernameDynamic)
        // Concatenate the timestamp to string to form unique timestamp
        test_data_username = Username + String.valueOf(timestamp.getTime());
    else
        test_data_username = Username;

        // Continue with registration
        emailInput.clear();
        emailInput.sendKeys(test_data_username);
        passwordInput.clear();
        passwordInput.sendKeys(Password);
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(Password);

        registerNowButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions
                .urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/login"));

        this.lastGeneratedUsername = test_data_username;

        return this.driver.getCurrentUrl().endsWith("/login");
    }

}

