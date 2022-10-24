package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ScreenShot {
    @Test
    void ScreenShot() throws IOException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/login");
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileInputStream fileInputStream = new FileInputStream(scrFile);
        FileUtils.copyFile(scrFile, new File("./dowload.jpg"));
        driver.manage().window().maximize();
        driver.quit();
}
}
