package PageFactory.Page;

import PageFactory.Block.BookCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchResult extends Page {

    @FindBy(css = ".sorting-value")
    private WebElement sortingValue;
    @FindBy(css = ".product-card")
    private List<WebElement> cards;

    @FindBy(css = ".only_desc .filter-reset")
    private List<WebElement> chips;
    @FindBy(css = ".loading-panel")
    private WebElement loader;

    public SearchResult(WebDriver driver) {
       super(driver);
    }

    public void sortByType(String sortType) throws InterruptedException {
        sortingValue.click();
        driver.findElement(By.cssSelector("[data-event-content='" + sortType + "']")).click();
        loaderWait();
    }

    public List<BookCard> getBooks(){
        List<BookCard> bookCards = new ArrayList<>();
        for (WebElement card : cards) {
            bookCards.add(new BookCard(card));
        }

        return bookCards;
    }

    public void closeChip(String name) throws InterruptedException {
        for (WebElement chip : chips) {
            if(chip.getText().equalsIgnoreCase(name)){
                chip.findElement(By.cssSelector(".header-sprite")).click();
                break;
            }
        }
        loaderWait();
    }
    public void loaderWait(){
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.invisibilityOf(loader));
    }
}
