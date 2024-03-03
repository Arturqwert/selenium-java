package LabirintTests;

import LabirintTests.Block.BookCard;
import LabirintTests.Page.MainPage;
import LabirintTests.Page.SearchResult;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.containers.BrowserWebDriverContainer.*;
import static org.testcontainers.containers.VncRecordingContainer.VncRecordingFormat.MP4;

@ExtendWith(SeleniumJupiter.class)
public class LabirintTest {

    private final String url = "https://www.labirint.ru/";
    private WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        FirefoxOptions  options = new FirefoxOptions ();
        options.setCapability("browserVersion", "122.0");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.setCapability("enableVNC", true);
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            /* How to add test badge */
            put("name", "Test badge...");

            /* How to set session timeout */
            put("sessionTimeout", "1m");

            /* How to set timezone */
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});

            /* How to add "trash" button */
            put("labels", new HashMap<String, Object>() {{
                put("auto", "true");
            }});

            /* How to enable video recording */
            put("enableVideo", true);
        }});

        String hubURL = System.getProperty("hub_name", "http://localhost:4444");

        driver = new RemoteWebDriver(new URL(hubURL + "/wd/hub"), options);

        //driver = new RemoteWebDriver(chrome.getSeleniumAddress(), new ChromeOptions());
        //driver = new RemoteWebDriver(new URL("http://localhost:4444"), new ChromeOptions());
        //driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void searchTest(ChromeDriver driver) throws InterruptedException {
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().addCookie(new Cookie("cookie_policy", "1"));


        driver.findElement(By.cssSelector("#search-field")).sendKeys("java", Keys.RETURN);
        driver.findElement(By.cssSelector(".sorting-value")).click();
        driver.findElement(By.cssSelector("[data-event-content='высокий рейтинг']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[@class='filter-reset__content' and text()[contains(., 'жидаются')]]/div[@class='filter-reset__icon header-sprite']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[@class='filter-reset__content' and text()[contains(., 'Нет в продаже')]]/div[@class='filter-reset__icon header-sprite']")).click();
        Thread.sleep(2000);

        List<WebElement> btns = driver.findElements(By.cssSelector(".btn-tocart"));

        for (WebElement btn : btns) {
            btn.click();
        }

        String goodsFromBtns = Integer.toString(btns.size());
        String goodsInBasket = driver.findElement(By.cssSelector("span.basket-in-cart-a")).getText();

        assertEquals(goodsFromBtns, goodsInBasket);

    }

    @Test
    public void searchPOMTest() throws InterruptedException {
        //throw new RuntimeException("hello world!");
        MainPage mainPage = new MainPage(driver);

        mainPage.getWithConditions();
        mainPage.getHeader().search("java");

        SearchResult searchResult = new SearchResult(driver);
        searchResult.sortByType("высокий рейтинг");
        searchResult.closeChip("ожидаются");
        searchResult.closeChip("нет в продаже");

        List<BookCard> books = searchResult.getBooks();

        for (BookCard book : books) {
            book.addToCart();
        }

        String booksCount = Integer.toString(books.size());
        String goodsInBasket = searchResult.getHeader().awaitLoadCartCounter(booksCount).getCartCounter();

        assertEquals(booksCount, goodsInBasket);
    }
}
