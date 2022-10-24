package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FrameTest {
    @Test
    void frameTest01(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/nested_frames");
        driver.manage().window().fullscreen();
        // Selenium tại một thời điểm chỉ tương tác được với 1 html duy nhất
        driver.switchTo().frame("frame-top");// Chuyển đến frame đầu tiên
        driver.switchTo().frame("frame-middle");// Đi tiếp vào frame tiếp theo
        String content = driver.findElement(By.id("content")).getText();
        Assert.assertEquals(content,"MIDDLE");
        driver.switchTo().defaultContent();// Quay trở lại mặc định
        driver.switchTo().parentFrame();// Trở về frame trước nó
        driver.quit();
    }
}
