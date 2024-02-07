package TheInternetTests;

import TheInternetTests.Extensions.CustomEC;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static java.time.Duration.*;
import static java.time.temporal.ChronoUnit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumJupiter.class)
public class WaitersTest {
    static Properties properties;
    String baseUrl = properties.getProperty("url.uitap");

    @BeforeAll
    public static void setupOneTime() throws IOException {
        properties = new Properties();
        properties.load(new FileReader("src/main/resources/credentials.properties"));
    }


    @Test
    public void implicitlyWaitTest(ChromeDriver driver) {
        String textToBe = "Data loaded with AJAX get request.";
        driver.manage().timeouts().implicitlyWait(of(20, SECONDS));
        driver.get(baseUrl + "/ajax");

        driver.findElement(By.cssSelector("#ajaxButton")).click();
        String successText = driver.findElement(By.cssSelector(".bg-success")).getText();

        assertEquals(textToBe, successText);
    }

    @Test
    public void progressBarWithOwnWaiterTest(ChromeDriver driver) {
        driver.get(baseUrl + "/progressbar");

        driver.findElement(By.cssSelector("#startButton")).click();
        WebElement stopButton =  driver.findElement(By.cssSelector("#stopButton"));
        WebElement result =  driver.findElement(By.cssSelector("#result"));

        long timeOutInSeconds = 20;
        By progressLocator = By.cssSelector("#progressBar");
        String textToBe = "75%";

        ownWaiter(driver, timeOutInSeconds, progressLocator, textToBe);

        stopButton.click();
        String resultText = result.getText();

        assertTrue(resultText.contains("Result: 0"));
    }

    private static void ownWaiter(WebDriver driver, long timeOutinSeconds, By locator, String textToBe) {
        String elementText;
        long startTime = System.currentTimeMillis();
        timeOutinSeconds = timeOutinSeconds * 1000;

        while (System.currentTimeMillis() - timeOutinSeconds <= startTime) {
            elementText = driver.findElement(locator).getText();
            if(elementText.equals(textToBe)){
                break;
            }
        }

        throw new TimeoutException("Не удалось найти текст элемента за " + timeOutinSeconds/1000 + " секунд.");
    }

    @Test
    public void explicitlyWaitTest(ChromeDriver driver){
        driver.get(baseUrl + "/progressbar");

        driver.findElement(By.cssSelector("#startButton")).click();
        WebElement stopButton =  driver.findElement(By.cssSelector("#stopButton"));
        WebElement result =  driver.findElement(By.cssSelector("#result"));

        By progressLocator = By.cssSelector("#progressBar");
        String textToBe = "75%";

        WebDriverWait waiter = new WebDriverWait(driver, of(20, SECONDS));
        waiter.pollingEvery(of(100, MILLIS))
                .until(ExpectedConditions.textToBe(progressLocator, textToBe));

        stopButton.click();
        String resultText = result.getText();

        assertTrue(resultText.contains("Result: 0"));
    }

    @Test
    public void ownExpectedConditionTest(ChromeDriver driver){
        String filepath = Path.of("").toAbsolutePath() + "/src/main/resources/trafficlight.html";
        driver.get("file://" + filepath);

        String cssValueToBe = "rgba(163, 217, 135, 1)";
        By light = By.cssSelector("#light");

        WebDriverWait wait = new WebDriverWait(driver, of(2, SECONDS));
        wait.withMessage("не удалось найти свойство " + cssValueToBe)
                .until(CustomEC.cssPropertyToBe(light, "background-color", cssValueToBe)).click();

        String textIsAs = driver.findElement(light).getText();
        assertEquals("green", textIsAs);
    }
}
