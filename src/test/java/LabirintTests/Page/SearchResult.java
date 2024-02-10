package LabirintTests.Page;

import LabirintTests.Block.BookCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchResult extends Page{
    public SearchResult(WebDriver driver) {
       super(driver);
    }

    public void sortByType(String sortType) throws InterruptedException {
        driver.findElement(By.cssSelector(".sorting-value")).click();
        driver.findElement(By.cssSelector("[data-event-content='" + sortType + "']")).click();
        loaderWait();
    }

    public List<BookCard> getBooks(){
        List<WebElement> cards = driver.findElements(By.cssSelector(".product-card"));

        List<BookCard> bookCards = new ArrayList<>();
        for (WebElement card : cards) {
            bookCards.add(new BookCard(card));
        }

        return bookCards;
    }

    public void closeChip(String name) throws InterruptedException {
        List<WebElement> chips = driver.findElements(By.cssSelector(".only_desc .filter-reset"));

        for (WebElement chip : chips) {
            if(chip.getText().equalsIgnoreCase(name)){
                chip.findElement(By.cssSelector(".header-sprite")).click();
                break;
            }
        }
        loaderWait();
    }
    public void loaderWait(){
        WebElement loader = driver.findElement(By.cssSelector(".loading-panel"));
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOf(loader));
    }
}
