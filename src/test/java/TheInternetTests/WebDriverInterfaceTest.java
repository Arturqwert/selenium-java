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
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({PropsResolver.class, WatcherTakeScreenshotAfterFail.class, SetUpDriverResolver.class})
public class WebDriverInterfaceTest implements WebDriverProvider {

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

    @Test
    public void loginTest() {
        driver.get(baseUrl + "/login");

        driver.findElement(By.cssSelector("#username")).sendKeys("tomsmith");
        driver.findElement(By.cssSelector("#password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector(".fa-sign-in")).click();

        var res = driver.findElement(By.cssSelector("#flash")).getAttribute("class");

        assertTrue(res.contains("success"));
    }

    @Test
    public void longLoadingTest() {
        driver.get(baseUrl + "/dynamic_loading/2");

        driver.findElement(By.cssSelector("#start button")).click();

        driver.manage().timeouts().implicitlyWait(Duration.of(6, ChronoUnit.SECONDS));

        var text = driver.findElement(By.cssSelector("#finish")).getText();

        assertEquals(text, "Hello World!");
    }

    @Test
    public void shouldSwitchBetweenTabs() {
        driver.get(baseUrl + "/windows");

        driver.findElement(By.linkText("Click Here")).click();

        var getHandles = driver.getWindowHandles();
        String[] handles = new String[getHandles.size()];
        getHandles.toArray(handles);

        String firstTab = handles[0];
        String secondTab = handles[1];

        driver.switchTo().window(secondTab);

        var text = driver.findElement(By.cssSelector("h3")).getText();

        assertEquals(text, "New Window");
    }

    @Test
    public void shouldSwitchBetweenFrames() {
        driver.get(baseUrl + "/iframe");

        driver.switchTo().frame("mce_0_ifr");

        var textFromFrame = driver.findElement(By.cssSelector("#tinymce")).getText();
        assertEquals(textFromFrame, "Your content goes here.");

        driver.switchTo().defaultContent();

        var textFromPage = driver.findElement(By.cssSelector("h3")).getText();
        assertEquals(textFromPage, "An iFrame containing the TinyMCE WYSIWYG Editor");
    }
    @Test
    public void shouldAuthWithCookie() throws IOException {
        String userToken = props.getProperty("user.token");
        driver.get("https://github.com");

        driver.manage().addCookie(new Cookie("user_session", userToken));
        driver.navigate().refresh();

        var img = driver.findElement(By.cssSelector("span.Button-label img")).getAttribute("src");
        driver.manage().deleteCookieNamed("user_session");

        assertEquals(img, "https://avatars.githubusercontent.com/u/85057592?v=4");
    }

    @Test
    public void shouldReceiveStaleElementReferenceException() {
        driver.get(baseUrl + "/login");

        var res1 = driver.findElement(By.cssSelector(".radius"));

        driver.navigate().refresh();

        try {
            res1.click();
        }
        catch (StaleElementReferenceException exception)
        {
            assertTrue(exception.getMessage().contains("stale element not found"));
        }
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }
}