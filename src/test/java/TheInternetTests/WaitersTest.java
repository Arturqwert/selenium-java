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
import static org.openqa.selenium.By.cssSelector;

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

        driver.findElement(cssSelector("#ajaxButton")).click();
        String successText = driver.findElement(cssSelector(".bg-success")).getText();

        assertEquals(textToBe, successText);
    }

    @Test
    public void progressBarWithOwnWaiterTest(ChromeDriver driver) {
        driver.get(baseUrl + "/progressbar");

        driver.findElement(cssSelector("#startButton")).click();
        WebElement stopButton =  driver.findElement(cssSelector("#stopButton"));
        WebElement result =  driver.findElement(cssSelector("#result"));

        long timeOutInSeconds = 20;
        By progressLocator = cssSelector("#progressBar");
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

        driver.findElement(cssSelector("#startButton")).click();
        WebElement stopButton =  driver.findElement(cssSelector("#stopButton"));
        WebElement result =  driver.findElement(cssSelector("#result"));

        By progressLocator = cssSelector("#progressBar");
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
        By light = cssSelector("#light");

        WebDriverWait wait = new WebDriverWait(driver, of(2, SECONDS));
        wait.withMessage("не удалось найти свойство " + cssValueToBe)
                .until(CustomEC.cssPropertyToBe(light, "background-color", cssValueToBe)).click();

        String textIsAs = driver.findElement(light).getText();
        assertEquals("green", textIsAs);
    }

    @Test
    public void conflictWaitersTest(ChromeDriver driver){
        driver.manage().timeouts().implicitlyWait(ofSeconds(15));
        //test duration ~34 sec, because the element is not found within 15 sec and the driver sends another request with timeout of 15 sec
        //After the first request timeout 15 of the 20 are spent in explicitly wait, so the driver sends another request.

        driver.get(baseUrl + "/ajax");

        driver.findElement(cssSelector("#ajaxButton")).click();
        By locator = cssSelector(".bg-success9");

        new WebDriverWait(driver, ofSeconds(20))
                .until(ExpectedConditions.textToBe(locator, "Data loaded with AJAX get request."));

        String successText = driver.findElement(locator).getText();

        assertEquals("Data loaded with AJAX get request.", successText);
    }
}
