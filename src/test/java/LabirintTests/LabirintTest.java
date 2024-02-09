package LabirintTests;

import LabirintTests.Block.BookCard;
import LabirintTests.Page.MainPage;
import LabirintTests.Page.SearchResult;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumJupiter.class)
public class LabirintTest {

    private final String url = "https://www.labirint.ru/";

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

        String test = String.valueOf(btns.size());

        String goodsFromBtns = Integer.toString(btns.size());
        String goodsInBasket = driver.findElement(By.cssSelector("span.basket-in-cart-a")).getText();

        assertEquals(goodsFromBtns, goodsFromBtns);

    }

    @Test
    public void searchPOMTest(ChromeDriver driver) throws InterruptedException {
        MainPage mainPage = new MainPage(driver);

        mainPage.get();
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
