package PageFactory;

import PageFactory.Block.BookCard;
import PageFactory.Page.MainPage;
import PageFactory.Page.SearchResult;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumJupiter.class)
@Epic("Каталог")
@Feature("Добавлеие книг в корзину.")
@Story("Как пользователь я хочу добавлять книги в корзину.")
public class LabirintTest {

    private final String url = "https://www.labirint.ru/";
    private  SearchResult searchResult;

    @DisplayName("Ищем книги на странице.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("ASmarun")
    @Test
    public void searchPOMTest(ChromeDriver driver) throws InterruptedException {
        generateJson();
        generateSql();

        searchResult = PageFactory.initElements(driver, SearchResult.class);

        MainPage mainPage = new MainPage(driver);

        mainPage.getWithConditions();
        mainPage.getHeader().search("java");

        searchResult.sortByType("высокий рейтинг");
        searchResult.closeChip("ожидаются");
        searchResult.closeChip("нет в продаже");

        List<BookCard> books = searchResult.getBooks();

        step("Добавить книги в корзину.", () -> {
            for (BookCard book : books) {
                book.addToCart();
            }
        });

        int booksCount = books.size();
        int goodsInBasket = searchResult.getHeader().awaitLoadCartCounter(booksCount).getCartCounter();

        step("Проверка соответствия количества добавленных товаров. О.Р. = " + booksCount + ".", () ->{
            assertEquals(booksCount, goodsInBasket);
        });
    }

    @Attachment(value = "reques-Body", type = "application/json")
    private String generateJson(){
        return "{\"name\": \"Artur\"}";
    }

    @Attachment(value = "sql-query", type = "text/plain")
    private String generateSql(){
        return "SELECT * FROM users WHERE id IN (1, 2 ,3)";
    }
}
