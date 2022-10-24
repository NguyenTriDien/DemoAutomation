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
    private By sex = By.id("csex1");
    private By ageTexbox = By.id("cage");
    private By heightTextbox = By.id("cheightmeter");
    private By weightTextbox = By.id("ckg");
    private By submit = By.xpath("//*[@value='Calculate']");
    private WebDriver driver;
    @BeforeClass(alwaysRun = true)
    void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        navigateUrl("https://www.calculator.net/bmi-calculator.html");
        driver.manage().window().fullscreen();
    }
    @Test
    void Example(){
        fillForm("24","180","80");
        SubmitForm();
        verifyResufl();
    }

    private void fillForm(String age,String height, String weight){
        WebElement gender1 = driver.findElement(sex);
       driver.findElement(ageTexbox).sendKeys(age);
       driver.findElement(heightTextbox).sendKeys(height);
       driver.findElement(weightTextbox).sendKeys(weight);
        if(!gender1.isSelected()){
            gender1.click();
        }

    }
    private void SubmitForm(){
        driver.findElement(submit).click();
    }
    private void verifyResufl(){

    }
    private void navigateUrl(String url){
        driver.get(url);
    }

    @AfterClass(alwaysRun = true)
    void teadown(){
        driver.quit();
    }

}



