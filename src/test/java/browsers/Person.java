package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Data
public class Person {
    private String lastName;
    private String firstName;
    private String email;
    private BigDecimal due;
    private String website;

    public Person(String lastName, String firstName, String email, BigDecimal due, String website) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.due = due;
        this.website = website;
    }

    /**
 Constructors and Getters Setters
 **/

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getDue() {
        return due;
    }

    public void setDue(BigDecimal due) {
        this.due = due;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public static class TableTest {
        WebDriver driver;
        @Test
        void getPersonWhoHasMaxOfDue() {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            driver.get("https://the-internet.herokuapp.com/tables");
            List<WebElement> rows = driver.findElements(By.xpath("//table[@id='table1']/tbody/tr"));

            List<Person> persons = rows.stream()
                    .map(this::toPerson).collect(Collectors.toList());

            Person largestDuePerson = persons
                    .stream()
                    .max(Comparator.comparing(Person::getDue))
                    .orElseThrow(NoSuchElementException::new);

            Assert.assertEquals(String.format("%s %s",largestDuePerson.getFirstName(), largestDuePerson.getLastName()), "Doe Jason");

        }

        /**
         * element is a row in table
         *
         * @param element
         * @return
         */
        private Person toPerson(WebElement element) {
            String lastName = element.findElements(By.tagName("td")).get(0).getText();
            String firstname = element.findElements(By.tagName("td")).get(1).getText();
            String email = element.findElements(By.tagName("td")).get(2).getText();
            BigDecimal due = BigDecimal.valueOf(Double.parseDouble(element.findElements(By.tagName("td")).get(3).getText().trim().replace("$", "")));
            String website = element.findElements(By.tagName("td")).get(4).getText();
            return new Person(firstname, lastName, email,due, website);
        }
        @AfterClass(alwaysRun = true)
        void teaDown(){
        driver.quit();
        }
    }
}
