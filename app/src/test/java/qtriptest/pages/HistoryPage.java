package qtriptest.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HistoryPage {

    WebDriver driver;

    @FindBy(xpath = "//th[@scope='row']")
    WebElement transactionID;

    @FindBy(xpath = "//button[@class='cancel-button']")
    WebElement cancelBtn;


    @FindBy(xpath = "//a[text()='Reservations']")
    WebElement ReservationBtn;

    public HistoryPage(WebDriver driver) {
        this.driver = driver;

        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
    }

    public String getReservationId(){
        return transactionID.getText();
    }

    public void cancelReservation(String transactionID)
    {
        //th[text()='bf943629e76fbffd']/..//button
        String xpath="//th[text()='"+transactionID+"']/..//button";
        WebElement cancelBTN=driver.findElement(By.xpath(xpath));
        cancelBTN.click();
    }

    public boolean checkTransactioIDPresent(String transactionID){
        driver.navigate().refresh();
        String xpath="//th[text()='"+transactionID+"']";
        // WebElement cancelBTN=driver.findElement(By.xpath(xpath));
        // cancelBTN.click();
            try{
            return driver.findElement(By.xpath(xpath)).isDisplayed();
            }
            catch(Exception e){
               // System.out.println(e.getMessage());
                return true;
            }
    }

    public int getTransactionCount(){
        List<WebElement> list=driver.findElements(By.xpath("//th[@scope='row']"));
        return list.size();

    }


}