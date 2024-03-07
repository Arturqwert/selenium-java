package MyCucumber;

import io.cucumber.java.*;
import io.cucumber.java.ru.Дано;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchResultStepdefs {

    private WebDriver driver;
    private List<WebElement> cards;

    public SearchResultStepdefs(DriverManager driver) {
        this.driver = driver.getInstance();
    }

    @Дано("отображается список книг")
    public void shouldBeBooksList(){
        cards = driver.findElements(By.cssSelector(".product-card"));
        assertEquals(60, cards.size());
    }
}
