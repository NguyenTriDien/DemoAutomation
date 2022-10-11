package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.performance.Performance;
import org.openqa.selenium.devtools.v85.performance.model.Metric;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Thread.sleep;

public class ChromeBrowser {
    @Test
    void creatOrder() throws InterruptedException, ParseException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=vi_VN");
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to("https://uat.unicorn.yody.io/admin");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("yd190598");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("19051998");
        WebElement logIn = driver.findElement(By.xpath("//button[@type='submit']"));
        logIn.click();
        Thread.sleep(2000);
        //Assert.assertEquals(driver.getCurrentUrl(), "https://uat.unicorn.yody.io/admin/");
        // Verify phần tử có đang được hiển thị không
        WebElement susscessMessage = driver.findElement(By.xpath("//span[@class='name']"));
        //Kiểm tra xem một phần tử nào có được xuất hiện hay không
        susscessMessage.isDisplayed();
        WebElement dasboad = driver.findElement(By.xpath("//h1[@class='greeting__title']"));
        String text = dasboad.getText();
        System.out.println(text);
        //WebElement tiTle = driver.findElement(By.xpath("//span[@class='name']"));
        //tiTle.getText();
        Assert.assertEquals(text, "Tổng quan");
        WebElement pos = driver.findElement(By.xpath("//span[contains(text(),'Bán tại quầy')]"));
        pos.click();
        Thread.sleep(2000);
        WebElement choseStore = driver.findElement(By.xpath("//input[@id='store']"));
        choseStore.sendKeys("Cửa hàng B");
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER);
        action.perform();
        WebElement chose = driver.findElement(By.xpath("//button[@type='submit']"));
        chose.click();
        Thread.sleep(1000);
        WebElement Input_Variant = driver.findElement(By.xpath("//*[@id=\"search_product\"]"));
        Input_Variant.sendKeys("APM0894-RML-XL5");
        Thread.sleep(2000);
        action.sendKeys(Keys.ENTER);
        action.perform();
        Thread.sleep(2000);
        WebElement Quantyti = driver.findElement(By.xpath("//input[@value='1']"));
        Quantyti.click();
        Quantyti.sendKeys("2");
        Thread.sleep(2000);
        WebElement price = driver.findElement(By.xpath("//div[@class='t-black']"));
        // Chuyển đổi từ String qua Bigdecima để thực hiện tính toán
        DecimalFormat formatter = new DecimalFormat();
        formatter.setParseBigDecimal(true);
        Number actualPrice = formatter.parse(price.getText());
        //----------------------------------------------------------
        WebElement discount = driver.findElement(By.id("selectMoneyTypeInput-0"));
        float discountCV = Float.parseFloat(discount.getText());
        WebElement total = driver.findElement(By.xpath("//td[@class='ant-table-cell']//div[@class='t-black']"));
        float totalSale = Float.parseFloat(total.getText());
        WebElement Customer = driver.findElement(By.xpath("//*[@id=\"search_customer\"]"));
        Customer.sendKeys("0913506225");
        action.sendKeys(Keys.ENTER);
        action.perform();
        WebElement  Cgtv = driver.findElement(By.xpath("//*[@id=\"inputSelectSalesman\"]"));
        Cgtv.click();
        WebElement input_Saleman = driver.findElement(By.id("inputSelectSalesman"));
        Thread.sleep(1000);
        input_Saleman.sendKeys("YD13116");
        Thread.sleep(1000);
        action.sendKeys(Keys.ENTER);
        action.perform();
        Thread.sleep(2000);
        WebElement PayMent = driver.findElement(By.xpath("//input[@class='ant-input hide-number-handle paymentCash']"));
        PayMent.sendKeys("12412");
        PayMent.click();
        WebElement ThanhToan = driver.findElement(By.xpath("//button[@id='btnFinishOrder']"));
        ThanhToan.click();
        driver.quit();

//driver.close();
        // Close chỉ đóng session của mình thôi
        //driver.quit();
        // Quit đóng luôn trình duyệt thông thường người ta sẽ dùng Quit để đóng luôn trình duyệt
    }

    @Test
    void openNormal() throws InterruptedException, ParseException {
        // Mở chrome trên giao diện
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://uat.unicorn.yody.io/admin");
        driver.manage().window().maximize();
        driver.findElement(By.id("username")).sendKeys("YD190598");
        driver.findElement(By.id("password")).sendKeys("19051998");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"root\"]/section/section/aside/div/div/div[1]/ul/li[1]/span")).isDisplayed();
        driver.quit();

//        driver.findElement(By.className("adsd"));
//        WebElement username = driver.findElement(By.id("username"));
//        username.sendKeys("yd190598");
//        WebElement password = driver.findElement(By.id("password"));
//        password.sendKeys("19051998");
//        WebElement logIn = driver.findElement(By.xpath("//button[@type='submit']"));
//        logIn.click();
        //driver.quit();

    }

    @Test
    void openHeadlessModel() {
        //Mở chrome ẩn không hiển thị UI
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        new ChromeDriver(options).get("https://unicorn.yody.io/admin/stores/303");
        new ChromeDriver(options).quit();

    }

    @Test
    void openMobileModel() {
        //Mở modle của mobile
        WebDriverManager.chromedriver().setup();
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 390);
        deviceMetrics.put("height", 844);
        deviceMetrics.put("pixelRatio", 1.0);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.0 Mobile/14E304 Safari/602.1");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://unicorn.yody.io/admin/stores/303");
        driver.quit();

    }

    @Test
    void collectBrowserMetric(){
        //Lấy ra thông tin hiệu năng của FE
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Performance.enable(Optional.empty()));
        List<Metric>metriclist = devTools.send(Performance.getMetrics());
       System.out.println("value" +metriclist);
        driver.get("https://unicorn.yody.io/admin");
        driver.quit();
       for (Metric m : metriclist){
            System.out.println(m.getName() + " = " + m.getValue() );
           System.out.printf(String.valueOf(m));
        }
//        for (Metric m : metriclist){
//            System.out.printf(String.valueOf(m));
//
//        }
}
    //@BeforeMethod
    @Test
    //Chạy test case này trước sau đó mới chạy test case khác
    void logIn(){
    Assert.assertEquals(2,2,"oẳng rồi anh em ơi");
    }
    @AfterMethod
    //Chạy test case này sau khi kết thúc một test case khác
    void logOut(){

    }
    //
   }
