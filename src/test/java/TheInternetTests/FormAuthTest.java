package TheInternetTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormAuthTest {

    @Test
    public void loginTest() {

        WebDriver browser = new ChromeDriver();

        browser.get("https://the-internet.herokuapp.com/login");

        browser.findElement(By.cssSelector("#username")).sendKeys("tomsmith");
        browser.findElement(By.cssSelector("#password")).sendKeys("SuperSecretPassword!");

        browser.findElement(By.cssSelector(".fa-sign-in")).click();

        var res = browser.findElement(By.cssSelector("#flash")).getAttribute("class");

        browser.close();

        assertTrue(res.contains("success"));
    }
}
