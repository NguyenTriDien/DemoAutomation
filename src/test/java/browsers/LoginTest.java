package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {
    @Test
    void validCredentials() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/login");
        driver.manage().window().maximize();
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.xpath("//*[@type='submit']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/secure", "Sai url");
        WebElement succesMesseng = driver.findElement(By.xpath("//*[@class='flash success']"));
        Assert.assertTrue(succesMesseng.isDisplayed(), "CHưa vào được trang chủ vui lòng kiểm tra lại ");
    }

    @Test
    void checkBox() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        WebElement checkBox1 = driver.findElement(By.xpath("//input[@type='checkbox'][1]"));
        WebElement checkBox2 = driver.findElement(By.xpath("//input[@type='checkbox'][2]"));
        if (!checkBox1.isSelected()) {
            checkBox1.click();
        }
        if (!checkBox2.isSelected()) {
            checkBox2.click();
        }
        Assert.assertTrue(checkBox1.isSelected(),"Checkbox1 chưa được click");
        Assert.assertTrue(checkBox2.isSelected(),"Checkbox2 chưa được click");

    }
}
