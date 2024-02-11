package PageFactory;

import PageFactory.Block.BookCard;
import PageFactory.Page.MainPage;
import PageFactory.Page.SearchResult;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumJupiter.class)
public class LabirintTest {

    private final String url = "https://www.labirint.ru/";
    private  SearchResult searchResult;

    @Test
    public void searchPOMTest(ChromeDriver driver) throws InterruptedException {
        searchResult = PageFactory.initElements(driver, SearchResult.class);

        MainPage mainPage = new MainPage(driver);

        mainPage.getWithConditions();
        mainPage.getHeader().search("java");

        searchResult.sortByType("высокий рейтинг");
        searchResult.closeChip("ожидаются");
        searchResult.closeChip("нет в продаже");

        List<BookCard> books = searchResult.getBooks();

        for (BookCard book : books) {
            book.addToCart();
        }

        int booksCount = books.size();
        int goodsInBasket = searchResult.getHeader().awaitLoadCartCounter(booksCount).getCartCounter();

        assertEquals(booksCount, goodsInBasket);
    }
}
