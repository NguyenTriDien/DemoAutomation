package PageObjectModel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class runer {
    phptravels phptravels = new phptravels();
    public static WebDriver driver;
    private String url = " https://www.phptravels.net/login";

    @BeforeClass(alwaysRun = true)
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().fullscreen();
    }

    @Test
    void login() {
        phptravels.fillForm("user@phptravels.com", "demouser");
        phptravels.clickButtonLogin();
        phptravels.verifyLoginSuccess("Hi, Demo Welcome Back");
    }

    @Test
    void verifyPlaceholder() {
        phptravels.verifyPlaceholder("Email", "Password");
    }

    @AfterClass(alwaysRun = true)
    void teardown() {
        driver.quit();
    }

}
