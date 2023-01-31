package PageObjectModel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;


public class UnitCalculatorTest {
    private By textVerify = By.xpath("//h2[contains(text(),'Hi,')]");
    private By username = By.xpath("//input[@placeholder='Email']");
    private By password = By.xpath("//input[@placeholder='Password']");
    private By submit = By.xpath("//button[@type='submit'and@class='btn btn-default btn-lg btn-block effect ladda-button waves-effect']");
    private WebDriver driver;
    @BeforeClass(alwaysRun = true)
    void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        navigateUrl("https://www.phptravels.net/login");
        driver.manage().window().fullscreen();
    }
    @Test
    void login(){
    fillForm("user@phptravels.com","demouser");
    clickButtonLogin();
        driver.findElement(textVerify).getText();
        driver.findElement(textVerify).getAttribute("pa");
    }

    private void fillForm(String email, String pass){
        driver.findElement(username).sendKeys(email);
        driver.findElement(password).sendKeys(pass);
    }
    private void clickButtonLogin(){
        driver.findElement(submit).click();
    }
    private void verifyLoginSuccess(){

    }
//    private void fillForm(String age,String height, String weight){
//        WebElement gender1 = driver.findElement(sex);
//       driver.findElement(ageTexbox).sendKeys(age);
//       driver.findElement(heightTextbox).sendKeys(height);
//       driver.findElement(weightTextbox).sendKeys(weight);
//        if(!gender1.isSelected()){
//            gender1.click();
//        }
//
//    }
//    private void SubmitForm(){
//        driver.findElement(submit).click();
//    }
//    private void verifyResufl(){
//
//    }
    private void navigateUrl(String url){
        driver.get(url);
    }

    @AfterClass(alwaysRun = true)
    void teadown(){
        driver.quit();
    }

}



