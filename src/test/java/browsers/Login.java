package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class Login {
    @Test
    void setUpDriver(){
        ChromeOptions options = new ChromeOptions();

        WebDriver driver = new ChromeDriver(options);
        WebDriverManager.chromedriver().setup();
       // System.out.println(driver);

    }
    public String driver(){
        return driver();
    }


    public  void logIn(String url){
        setUpDriver();
        new ChromeDriver(new ChromeOptions()).get(url);
    }
}
