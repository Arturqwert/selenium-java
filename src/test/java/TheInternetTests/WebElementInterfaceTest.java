package TheInternetTests;

import TheInternetTests.Extensions.PropsResolver;
import TheInternetTests.Extensions.SetUpDriverResolver;
import TheInternetTests.Extensions.WatcherTakeScreenshotAfterFail;
import TheInternetTests.Infrastructure.WebDriverProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({PropsResolver.class, WatcherTakeScreenshotAfterFail.class, SetUpDriverResolver.class})
public class WebElementInterfaceTest implements WebDriverProvider {
    public static Properties props;
    public static String baseUrl;
    public WebDriver driver;

    @BeforeAll
    public static void setUpOneTime(Properties properties) {
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
    public void shouldFindElement_s() {
        driver.get(baseUrl);

        WebElement element = driver.findElement(By.cssSelector("h2"));
        List<WebElement> elements = driver.findElements(By.cssSelector("h2"));

        assertEquals(element, elements.get(0));
        assertEquals(1, elements.size());
    }

    @Test
    public void searchContextTest() {
        driver.get(baseUrl);

        WebElement element = driver.findElement(By.cssSelector("#content"));
        WebElement nestedElement = element.findElement(By.cssSelector("h2"));

        assertEquals("Available Examples", nestedElement.getText());
    }

    @Test
    public void shouldClearInput() {
        driver.get(baseUrl + "/key_presses");

        WebElement input = driver.findElement(By.cssSelector("#target"));
        input.sendKeys("send", "Keys", "Test");

        input.sendKeys(Keys.chord(Keys.SHIFT, Keys.ARROW_UP), Keys.BACK_SPACE);

        assertTrue(input.getAttribute("value").isBlank());
    }

    @Test
    public void shouldUploadFile() {
        driver.get(baseUrl + "/upload");

        driver.manage().timeouts().implicitlyWait(Duration.of(2, ChronoUnit.SECONDS));
        driver.findElement(By.cssSelector("#file-upload")).sendKeys("C:\\Users\\artur\\IdeaProjects\\webTesting\\.gitignore");
        driver.findElement(By.cssSelector("#file-submit")).click();

        String nameUploadedFile = driver.findElement(By.cssSelector("#uploaded-files")).getText();
        assertEquals(".gitignore", nameUploadedFile);
    }

    @Test
    public void shouldTakeScreenshot() throws IOException {
        driver.get(baseUrl + "/login");

        assertEquals(1, 2);
    }
}