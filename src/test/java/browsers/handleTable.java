package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

public class handleTable {
    WebDriver driver;
    List<Double> dues;

    @BeforeClass
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/tables");
        driver.manage().window().maximize();
        dues = driver.
                findElements(By.xpath("//table[@id='table1']/tbody/tr/td[4]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList())
                .stream()
                .map(s -> s.replace("$", ""))
                .collect(Collectors.toList())
                .stream()
                .map(Double::valueOf)
                .collect(Collectors.toList());
    }

    @Test
    void verifyLargestDuePerSonInTable1() {
        Double maxValue = Collections.max(dues);// Tìm giá trị lớn nhất của mảng
        int maxDueIndex = dues.indexOf(maxValue);//Tìm vị trí của giá trị lớn nhất của mảng
        String maxDueFirstnamePerson = driver.findElement(By.xpath(String.format("//table[@id='table1']/tbody/tr[%d]/td[2]", maxDueIndex + 1))).getText();
        String maxDueLastnamePerson = driver.findElement(By.xpath(String.format("//table[@id='table1']/tbody/tr[%d]/td[1]", maxDueIndex + 1))).getText();

        Assert.assertEquals(String.format("%s %s", maxDueLastnamePerson, maxDueFirstnamePerson), "Doe Jason");
    }


    @Test
    void verifySmallestDuePersonsIntable1() {
        Double minDues = Collections.min(dues);//Lấy ra phần tử nhỏ nhất của mảng
        List<String> smallestDuePersons = new ArrayList<>();
        for (int i = 0; i < dues.size(); i++) {
            if (Objects.equals(dues.get(i), minDues)) {
                String minDueFirstNamePerson = driver.findElement(By.xpath(String.format("//table[@id='table1']/tbody/tr[%d]/td[2]", i + 1))).getText();
                String minDuelastNamePerson = driver.findElement(By.xpath(String.format("//table[@id='table1']/tbody/tr[%d]/td[1]", i + 1))).getText();
                smallestDuePersons.add(String.format("%s %s", minDuelastNamePerson, minDueFirstNamePerson));//Thêm phần tử vào mảng
            }
        }
        Assert.assertEquals(smallestDuePersons, Arrays.asList("Smith John", "Conway Tim"));// Tạo một mảng và add 2 phần tử vào
        Assert.assertEquals("smallestDuePersons","minDueFirstNamePerson");
    }

    @AfterClass
    void tearDown() {
        driver.quit();
    }

}
