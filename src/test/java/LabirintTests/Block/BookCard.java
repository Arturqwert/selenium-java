package LabirintTests.Block;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BookCard {
    private final WebElement bookCard;

    public BookCard(WebElement bookCard) {
        this.bookCard = bookCard;
    }

    public void addToCart(){
        bookCard.findElement(By.cssSelector(".btn-tocart")).click();
    }
}
