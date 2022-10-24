package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.KeyInput;
import org.openqa.selenium.interactions.SendKeysAction;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HandleJsAlert {
    static WebDriver driver;
    ChromeOptions options;
    @Test
    void clickForjsAlert() {
       driver = new ChromeDriver(options);
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//button[text()='']")).click();
        driver.switchTo().alert().sendKeys("dddd5");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        driver.switchTo().alert().dismiss();
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE);
        String result = driver.findElement(By.xpath("//*[@id='result']")).getText();
        Assert.assertEquals(result,"You clicked: Cancel");

        }
    }

