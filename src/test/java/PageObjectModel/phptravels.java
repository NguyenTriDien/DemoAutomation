package PageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static PageObjectModel.runer.driver;


public class phptravels {
    private By textVerify = By.xpath("//h2[contains(text(),'Hi,')]");
    private By username = By.xpath("//input[@placeholder='Email']");
    private By password = By.xpath("//input[@placeholder='Password']");
    private By submit = By.xpath("//button[@type='submit'and@class='btn btn-default btn-lg btn-block effect ladda-button waves-effect']");


    public void fillForm(String email, String pass) {
        driver.findElement(username).sendKeys(email);
        driver.findElement(password).sendKeys(pass);
    }

    public void clickButtonLogin() {
        driver.findElement(submit).click();
        waitForElement(Duration.ofMillis(500L),textVerify);
    }

    public void verifyLoginSuccess(String text) {

        String textResult = driver.findElement(textVerify).getText();
        Assert.assertEquals(text, textResult);

    }


    public void waitForElement(Duration duration, By waitConditionLocator){
        WebDriverWait wait = new WebDriverWait(driver,duration);
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (waitConditionLocator));
    }

    public void verifyPlaceholder(String placeholderEmail, String placeholderPassword) {
        String usernamePlaceHolder = driver.findElement(username).getAttribute("placeholder");
        String passwordPlaceholder = driver.findElement(password).getAttribute("placeholder");
        Assert.assertEquals(placeholderEmail, usernamePlaceHolder);
        Assert.assertEquals(placeholderPassword, passwordPlaceholder);
    }

}



