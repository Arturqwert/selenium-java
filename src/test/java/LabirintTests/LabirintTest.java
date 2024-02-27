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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.containers.BrowserWebDriverContainer.*;
import static org.testcontainers.containers.VncRecordingContainer.VncRecordingFormat.MP4;

@ExtendWith(SeleniumJupiter.class)
@Testcontainers
public class LabirintTest {

    private final String url = "https://www.labirint.ru/";
    private WebDriver driver;

    @Container
    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer<>(
            "selenium/standalone-chrome:latest"
    )
            //.withExposedPorts(7900)
            .withRecordingMode(VncRecordingMode.RECORD_ALL, Path.of("vids").toFile(), MP4);

    @BeforeEach
    public void setUp() throws MalformedURLException {
        System.out.println("teste");

        driver = new RemoteWebDriver(chrome.getSeleniumAddress(), new ChromeOptions());
        //driver = new RemoteWebDriver(new URL("http://localhost:4444"), new ChromeOptions());
    }

//    @AfterEach
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

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
