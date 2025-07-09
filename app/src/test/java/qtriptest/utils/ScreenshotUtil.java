package qtriptest.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String folderPath = "test-output/screenshots";
        String fullPath = folderPath + "/" + testName + "_" + timestamp + ".png";

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(fullPath);
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }

        return fullPath;
    }
}
