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

import static qtriptest.utils.Wrappers.*;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import qtriptest.utils.Wrappers;

public class AdventureDetailsPage {

   
        WebDriver driver;
        private static final int TIMEOUT = 20;
    
        public AdventureDetailsPage(WebDriver driver) {
            this.driver = driver;
            PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
        }

        @FindBy(xpath = "//input[@name='name']")
        WebElement nameInput;

       // @FindBy(xpath = "//input[@name='date']")
        @FindBy(xpath = "//form[@id='myForm']/input[@name='date']")
        WebElement dateInput;

        @FindBy(xpath = "//input[@name='person']")
        WebElement personInput;

        @FindBy(xpath = "//button[@class='reserve-button']")
        WebElement reserveButton;

        @FindBy(xpath = "//div[@id='reserved-banner']")
        WebElement confirmBookingMsg;

        @FindBy(xpath = "//div[@id='navbarNavDropdown']//a[text()='Reservations']")
        WebElement ReservationBtn;



    public void bookAdventure(String name, String date,String count) throws InterruptedException, MalformedURLException{
        Thread.sleep(2000);
         nameInput.clear();
         nameInput.click();
         nameInput.sendKeys( name);
         Thread.sleep(2000);
         System.out.println("Is dateInput displayed? " + dateInput.isDisplayed());
System.out.println("Is dateInput enabled? " + dateInput.isEnabled());

         dateInput.clear();
         dateInput.click();
         Thread.sleep(2000);
         dateInput.sendKeys(date);
         personInput.clear();
         personInput.click();
         personInput.sendKeys(count);
         reserveButton.click();
     }

     public boolean isBookingSucessful(){
        // @FindBy(css = ".alert-success")
   // WebElement verificationPopup;
     return confirmBookingMsg.isDisplayed();
     //  return verificationPopup.isDisplayed();
   }

   public void ClickReservation(){
    ReservationBtn.click();
}

}
