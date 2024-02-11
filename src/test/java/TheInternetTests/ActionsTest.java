package TheInternetTests;

import TheInternetTests.Extensions.PropsResolver;
import TheInternetTests.Extensions.SetUpDriverResolver;
import TheInternetTests.Extensions.WatcherTakeScreenshotAfterFail;
import TheInternetTests.Infrastructure.WebDriverProvider;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({PropsResolver.class, WatcherTakeScreenshotAfterFail.class, SetUpDriverResolver.class})
public class ActionsTest implements WebDriverProvider {

    public WebDriver driver;
    public static Properties props;
    public static String baseUrl;

    @BeforeAll
    public static void setUpOneTime(Properties properties)
    {
        props = properties;
        baseUrl = props.getProperty("url");
    }

    @BeforeEach
    public void setUp(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @Test
    public void sliderTest(){
        driver.get(baseUrl + "/horizontal_slider");

        WebElement slider = driver.findElement(By.cssSelector("[type='range']"));

        Actions action = new Actions(driver);
        action.clickAndHold(slider)
                .moveByOffset(22, 0)
                .release()
                .perform();

        String  value = driver.findElement(By.cssSelector("#range")).getText();

        assertEquals("3.5", value);
    }

    @Test
    public void dragAndDropTest(){
        driver.get(baseUrl + "/drag_and_drop");

        WebElement a = driver.findElement(By.cssSelector("#column-a"));
        WebElement b = driver.findElement(By.cssSelector("#column-b"));

        Actions action = new Actions(driver);
        action.dragAndDrop(a, b).perform();

        String textA = driver.findElement(By.cssSelector("#column-a")).getText();
        String textB = driver.findElement(By.cssSelector("#column-b")).getText();

        assertEquals("B", textA);
        assertEquals("A", textB);
    }

    @Test
    public void contextClick() {
        driver.get(baseUrl + "/context_menu");

        WebElement context = driver.findElement(By.cssSelector("#hot-spot"));

        Actions action = new Actions(driver);
        action.contextClick(context)
                .perform();

        Alert alert = driver.switchTo().alert();
        String textAlert = alert.getText();

        assertEquals("You selected a context menu", textAlert);
        alert.accept();
    }

    @Test
    public void canvasTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/draw-in-canvas.html");

        WebElement canvas = driver.findElement(By.cssSelector("#my-canvas"));

        Actions action = new Actions(driver);
        action.moveToElement(canvas)
                .moveByOffset(-300, 0)
                .clickAndHold(canvas)
                .moveByOffset(70, 50)
                .moveByOffset(150, -20)
                .release(canvas)
                .perform();
    }

    @Test
    public void removeAddTest(){
        driver.get("https://qna.habr.com/");

        String removeBanner = "document.querySelectorAll('.roxotAdContainer')[2].remove()";
        String removeLabel = "document.querySelectorAll('.dfp_label')[2].remove()";

        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".roxotAdContainer"),3));

        List<WebElement> banners = driver.findElements(By.cssSelector(".roxotAdContainer"));

        ((JavascriptExecutor)driver).executeScript(removeBanner);
        ((JavascriptExecutor)driver).executeScript(removeLabel);

        List<WebElement> bannersAfter = driver.findElements(By.cssSelector(".roxotAdContainer"));

        assertEquals(bannersAfter.size(), banners.size() - 1);
    }

}
