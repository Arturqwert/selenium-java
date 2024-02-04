package TheInternetTests;

import TheInternetTests.Extensions.PropsResolver;
import TheInternetTests.Extensions.WatcherTakeScreenshotAfterFail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith({SeleniumJupiter.class, PropsResolver.class})
@ExtendWith(PropsResolver.class)
public class WebElementInterfaceTest {
    public static Properties props;
    public static String baseUrl;
    public WebDriver driver;

    @BeforeAll
    public static void setUpOneTime(Properties properties)
    {
       props = properties;
       baseUrl = props.getProperty("url");
    }

//    @BeforeEach
//    public void setUp(ChromeDriver driver)
//    {
//        this.driver = driver;
//    }



    @Test
    public void shouldFindElement_s()
    {
        driver.get(baseUrl);

        WebElement element = driver.findElement(By.cssSelector("h2"));
        List<WebElement> elements = driver.findElements(By.cssSelector("h2"));

        assertEquals(element, elements.get(0));
        assertEquals(1, elements.size());
    }

    @Test
    public void searchContextTest()
    {
        driver.get(baseUrl);

        WebElement element = driver.findElement(By.cssSelector("#content"));
        WebElement nestedElement = element.findElement(By.cssSelector("h2"));

        assertEquals("Available Examples", nestedElement.getText());
    }

    @Test
    public void shouldClearInput()
    {
        driver.get(baseUrl + "/key_presses");

        WebElement input = driver.findElement(By.cssSelector("#target"));
        input.sendKeys("send", "Keys", "Test");

        input.sendKeys(Keys.chord(Keys.SHIFT, Keys.ARROW_UP), Keys.BACK_SPACE);

        assertTrue(input.getAttribute("value").isBlank());
    }

    @Test
    public void shouldUploadFile()
    {
        driver.get(baseUrl + "/upload");

        driver.manage().timeouts().implicitlyWait(Duration.of(2, ChronoUnit.SECONDS));
        driver.findElement(By.cssSelector("#file-upload")).sendKeys("C:\\Users\\artur\\IdeaProjects\\webTesting\\.gitignore");
        driver.findElement(By.cssSelector("#file-submit")).click();

        String nameUploadedFile = driver.findElement(By.cssSelector("#uploaded-files")).getText();
        assertEquals(".gitignore", nameUploadedFile);
    }

    @ExtendWith(WatcherTakeScreenshotAfterFail.class)
    @Test
    public void shouldTakeScreenshot() throws IOException {
        driver = new ChromeDriver();
        driver.get(baseUrl + "/login");

        //File screenshot = driver.findElement(By.cssSelector("form")).getScreenshotAs(OutputType.FILE);

        //Files.move(screenshot.toPath(), Path.of("./scrn.png"));

        assertEquals(1, 2);
    }
}