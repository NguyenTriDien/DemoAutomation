package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

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
        driver.quit();
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
       List<WebElement> checkBox = driver.findElements(By.xpath("//input[@type='checkbox']"));
        System.out.println(checkBox.size());
        driver.quit();
        //Khi lick vào checkbox sẽ trả ra một attribuil có giá trị là Checked
    }
    @Test
    void verifyDropdowList(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/dropdown");
        driver.manage().window().maximize();
        WebElement selectOption = driver.findElement(By.id("dropdown"));
        Select selectObject = new Select(selectOption);
        selectObject.selectByValue("1");
        selectObject.selectByVisibleText("Option 1");
        WebElement option1 = driver.findElement(By.xpath("//*[@value='1'and@selected='selected']"));
        Assert.assertEquals(option1.getAttribute("value"),"1");
        driver.quit();
        //Chỉ áp dụng được với dropdown có dạng
        // <select id="dropdown">
        //    <option value="" disabled="disabled" selected="selected">Please select an option</option>
        //    <option value="1">Option 1</option>
        //    <option value="2">Option 2</option>
        //  </select>
    }
}

