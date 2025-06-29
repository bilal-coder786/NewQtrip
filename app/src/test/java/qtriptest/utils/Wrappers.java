package qtriptest.utils;

import java.time.Duration;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {
    //WebDriver driver;
    public static void clickonElement(WebDriver driver, WebElement element) {
        try {
           
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } else {
                element.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enterText(WebElement element, String text) {
        try {
            element.clear();
            Thread.sleep(1000);
            element.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isPageNavigationSucceeded(WebDriver driver, String expectedEndpoint, WebElement pageHeader, String expectedHeaderText) {
        return driver.getCurrentUrl().contains(expectedEndpoint) &&
               pageHeader.getText().equalsIgnoreCase(expectedHeaderText);
    }

    
    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }
    
    public static boolean sendKeys(WebElement inputBox, String keysToSend) {
        if (inputBox.isDisplayed()) {
            inputBox.clear();
            inputBox.sendKeys(keysToSend);
            return true;
        }

        else
            return false;

    }
    
  
}



