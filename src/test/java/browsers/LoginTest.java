package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.*;
import java.sql.Driver;
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
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!", Keys.ENTER);//Nhập mật khẩu xong thì bấm phím Enter
        //driver.findElement(By.xpath("//*[@type='submit']")).click();// Click Submit Button
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
        Keys enter = Keys.ENTER;

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

        /**
         * Có thể dùng 2 cách này để chọn value
         * selectObject.selectByVisibleText("Option 1");
         * selectObject.selectByIndex(0);
         */
        WebElement option1 = driver.findElement(By.xpath("//*[@value='1'and@selected='selected']"));
        Assert.assertEquals(option1.getAttribute("value"),"1");
        driver.quit();
        /**Chỉ áp dụng được với dropdown có dạng
         <select id="dropdown">
            <option value="" disabled="disabled" selected="selected">Please select an option</option>
            <option value="1">Option 1</option>
            <option value="2">Option 2</option>
          </select>
         */
    }
    @Test
    void verifyHyperlinktext(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/status_codes");
        Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/status_codes");

        WebElement statusCode200 = driver.findElement(By.linkText("200"));
        statusCode200.click();
        Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/status_codes/200");
        WebElement tiTle = driver.findElement(By.xpath("//h3[text()='Status Codes']"));
        Assert.assertTrue(tiTle.isDisplayed());

        WebElement goHere = driver.findElement(By.partialLinkText("re"));
        goHere.click();
        Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/status_codes");
    }
    @Test
    void verifyWebtable() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/tables");
        WebElement table1 = driver.findElement(By.ByXPath.id("table1"));
        int[] due = new int[4];
        int j = 0;
        int index = 0;
        for (int i = 1; i <= 4; i++) {
            WebElement listDue = driver.findElement(By.xpath("//table[@id='table1']/tbody/tr[" + i + "]/td[4]"));
            String n = listDue.getText().replace(".00", "");
            int m = Integer.parseInt(n.replace("$", ""));
            due[j] = m;
            j++;
        }
        System.out.println(due.length);
        int max = 0;
        int a;
        for (a = 0; a < due.length; a++) {
            if (due[a] > max) {
                max = due[a];
            index = a+1;
            }
        }
        WebElement name = driver.findElement(By.xpath("//table[@id='table1']/tbody/tr["+index+"]/td[2]"));
        Assert.assertEquals(name.getText(),"Jason");
//          for(int numberOfCol=1; numberOfCol <=5; numberOfCol++)
//         {
//              System.out.print(driver.findElement(By.xpath("//table[@id='table1']/thead/tr/th["+numberOfCol+"]/span")).getText() +"\t");
//              }
//        System.out.println("\n");
//          for(int numberOfvalue = 1;numberOfvalue <= 4;numberOfvalue ++) {
//              for (int numberOfrow = 1; numberOfrow <= 5; numberOfrow++) {
//                  System.out.print(driver.findElement(By.xpath("//table[@id='table1']/tbody/tr[" + numberOfvalue + "]/td[" + numberOfrow + "]")).getText() + "\t");
//              }
//              System.out.println("\n");
//          }
        driver.quit();
    }}
//        System.out.println(driver.findElement(By.xpath("//*[@id='table1']/tbody/tr[1]")).getText() +"\t\"");
//        for(int numberOfrow = 1; numberOfrow <=4; numberOfrow++ ){
//                    int valueOfrow = 1;
//                    valueOfrow++;
//                    System.out.println(driver.findElement(By.xpath("//*[@id='table1']/tbody/tr[1]")).getText() +"\t\"");
//            driver.quit();






